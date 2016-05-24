package edu.pitt.dbmi.xmeso.qa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocsCollector;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.hibernate.Criteria;

import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2meta.I2b2MetaDataSourceManager;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2meta.NmvbMesothelioma;

public class CodeResolver {

	private I2b2MetaDataSourceManager metaDataMgr;

	private Directory directory;
	private Analyzer analyzer;
	private IndexWriterConfig writerConfig;
	private IndexWriter writer;

	private final List<String> queryTerms = new ArrayList<String>();

	public static void main(String[] args) {
		CodeResolver codeResolver = new CodeResolver();
		codeResolver.setMetaDataMgr(new I2b2MetaDataSourceManager());
		codeResolver.initialize();
		codeResolver.addQueryTerm("Histological Type");
		codeResolver.addQueryTerm("Biphasic Mesothelioma");
		String code = codeResolver.searchForCode();
		System.out.println("Found code of " + code);
		codeResolver.clear();
		
		codeResolver.addQueryTerm("Immunohistochemical Profile");
		codeResolver.addQueryTerm("CD10");
		code = codeResolver.searchForCode();
		System.out.println("Found code of " + code);
		codeResolver.clear();
		
		codeResolver.addQueryTerm("Immunohistochemical Profile");
		codeResolver.addQueryTerm("CD31");
		code = codeResolver.searchForCode();
		System.out.println("Found code of " + code);
		codeResolver.clear();
		
		codeResolver.addQueryTerm("Immunohistochemical Profile");
		codeResolver.addQueryTerm("AE1/3");
		code = codeResolver.searchForCode();
		System.out.println("Found code of " + code);
		codeResolver.clear();
	}

	public CodeResolver() {
		
	}
	
	@SuppressWarnings("deprecation")
	public void initialize() {
		try {
			setDirectory(new RAMDirectory());
			setAnalyzer(new SimpleAnalyzer());
			writerConfig = new IndexWriterConfig(analyzer);
			setWriter(new IndexWriter(directory, writerConfig));		
			Criteria searchCriteria = metaDataMgr.getSession().createCriteria(
					NmvbMesothelioma.class);
			@SuppressWarnings("unchecked")
			List<NmvbMesothelioma> recordList = searchCriteria.list();
			for (NmvbMesothelioma record : recordList) {
				if (StringUtils.isBlank(record.getCFullname())) {
					continue;
				}
				final Set<String> wordSet = new HashSet<String>();
				parseWordsInString(record.getCFullname(), wordSet);
				String code = record.getCBasecode();
				String content = StringUtils.join(wordSet, " ");
				if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(content)) {
					if (content.toLowerCase().contains("biphasic")) {
						System.out.println("Adding code: " + code + " content: " + content);
					}
					Document doc = new Document();
					doc.add(new Field("code", record.getCBasecode(), Field.Store.YES,
							Field.Index.NOT_ANALYZED));
					doc.add(new Field("content", content.toLowerCase(),
							Field.Store.NO, Field.Index.ANALYZED));
					writer.addDocument(doc);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addQueryTerm(String term) {
		queryTerms.add(term.toLowerCase());
	}
	
	private String constructQueryFromTerms() {
		final Set<String> wordSet = new HashSet<String>();
		for (String term : queryTerms) {
			parseWordsInString(term, wordSet);
		}

		String query = "";
		for (String word : wordSet) {
			if (query.length() > 0) {
				query += " AND ";
			}
			query += "content:" + word;
		}
		System.out.println("query: " + query);
		
		return query;
	}

	public String searchForCode()  {
		String result = null;
		try {
			result = trySearchForCode();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return result;

	}
	
	private String trySearchForCode() throws IOException, ParseException {
		String query = constructQueryFromTerms();
		
		boolean applyAllDeletes = true;
		DirectoryReader directoryReader = DirectoryReader.open(writer,
				applyAllDeletes);
		IndexSearcher is = new IndexSearcher(directoryReader);

		QueryParser qp = new QueryParser("content", analyzer);
		Query q = qp.parse(query);

		TopDocsCollector<?> collector = TopScoreDocCollector.create(1000000);
		is.search(q, collector);

		ScoreDoc[] scoreDocs = collector.topDocs().scoreDocs;
		String code = "";
		for (ScoreDoc scoreDoc : scoreDocs) {
			Document d = directoryReader.document(scoreDoc.doc);
			code = d.getField("code").stringValue();
			break;
		}
		return code;
	}

	@SuppressWarnings("unused")
	private String generateQueryTerms() {
		final Set<String> wordSet = new HashSet<String>();
		for (String term : queryTerms) {
			parseWordsInString(term, wordSet);
		}
		return StringUtils.join(wordSet, " ");
	}

	private void parseWordsInString(String srcString, Set<String> wordSet) {
		if (srcString != null && srcString.length() > 0) {
			Pattern pattern = Pattern.compile("\\w+");
			Matcher matcher = pattern.matcher(srcString);
			while (matcher.find()) {
				wordSet.add(matcher.group());
			}			
		}
	}
	
	public void clear() {
		queryTerms.clear();
		
	}

	public Directory getDirectory() {
		return directory;
	}

	public void setDirectory(Directory directory) {
		this.directory = directory;
	}

	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	public IndexWriter getWriter() {
		return writer;
	}

	public void setWriter(IndexWriter writer) {
		this.writer = writer;
	}

	public I2b2MetaDataSourceManager getMetaDataMgr() {
		return metaDataMgr;
	}

	public void setMetaDataMgr(I2b2MetaDataSourceManager metaDataMgr) {
		this.metaDataMgr = metaDataMgr;
	}

}
