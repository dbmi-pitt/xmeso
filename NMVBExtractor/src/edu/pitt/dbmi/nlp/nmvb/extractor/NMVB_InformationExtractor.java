package edu.pitt.dbmi.nlp.nmvb.extractor;


//import NMVB_InformationExtractor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingUtilities;

import edu.pitt.dbmi.nlp.noble.coder.model.Mention;
import edu.pitt.dbmi.nlp.noble.coder.model.Section;
import edu.pitt.dbmi.nlp.noble.coder.model.Spannable;
import edu.pitt.dbmi.nlp.noble.coder.model.Text;
import edu.pitt.dbmi.nlp.noble.coder.processor.DocumentProcessor;
import edu.pitt.dbmi.nlp.noble.coder.processor.PartProcessor;
import edu.pitt.dbmi.nlp.noble.extract.InformationExtractor;
import edu.pitt.dbmi.nlp.noble.extract.model.ItemInstance;
import edu.pitt.dbmi.nlp.noble.extract.model.Template;
import edu.pitt.dbmi.nlp.noble.extract.model.TemplateDocument;
import edu.pitt.dbmi.nlp.noble.extract.model.TemplateItem;
import edu.pitt.dbmi.nlp.noble.terminology.Concept;
import edu.pitt.dbmi.nlp.noble.terminology.Terminology;
import edu.pitt.dbmi.nlp.noble.terminology.TerminologyException;
import edu.pitt.dbmi.nlp.noble.tools.TextTools;
import edu.pitt.dbmi.nlp.noble.util.CSVExporter;
import edu.pitt.dbmi.nlp.noble.util.HTMLExporter;

public class NMVB_InformationExtractor extends InformationExtractor {
	
	/**
	 * get list of parts in a given section
	 * @param section
	 * @return
	 *
	public static List<Spannable> getParts(Section section){
		Pattern pt = Pattern.compile("PARTS?\\s+\\d+(\\s+AND\\s+\\d+)?:",Pattern.MULTILINE|Pattern.DOTALL);
		Matcher mt = pt.matcher(section.getBody());
		List<Spannable> parts = new ArrayList<Spannable>();
		
		int offset = 0;
		Text part = null;
		while(mt.find()){
			if(part != null){
				part.setText(section.getBody().substring(part.getOffset(),mt.start()));
			}
			// init new text section
			offset = mt.end();
			part = new Text();
			part.setOffset(offset);
			parts.add(part);
		}
		if(part != null){
			part.setText(section.getBody().substring(part.getOffset()));
		}
		// reset offsets
		for(Spannable p: parts){
			((Text)p).setOffset(p.getStartPosition()+section.getBodyOffset());
			//System.out.println("--\n"+p.getText()+"\n--");
		}
		
		return parts;
	}
	*/
	private static boolean hasMesothelioma(Spannable part){
		Pattern pt = Pattern.compile("MESOTHELIOMA",Pattern.CASE_INSENSITIVE);
		return pt.matcher(part.getText()).find();
	}
	
	
	/**
	 * in FINAL DIAGNOSIS supress only 
	 * @param doc
	 * @throws TerminologyException 
	 */
	private static void filterParts(TemplateDocument doc) throws TerminologyException{
		PartProcessor pp = new PartProcessor();
		
		Template temp = null;
		for(Template t: doc.getTemplates()){
			temp = t;
			break;
		}
		TemplateItem feature = getFeature(temp,"NMVB:Tumor_Site");
		List<ItemInstance> torem = new ArrayList<ItemInstance>();
		List<ItemInstance> list = doc.getItemInstances(feature);
		
		// now lets do part de-composition
		Section final_dx = null;
		for(Section s: doc.getSections()){
			if(s.getTitle().equals("FINAL DIAGNOSIS:")){
				final_dx = s;
				break;
			}
		}
		// break final Dx section
		if(final_dx != null){
			List<Section> parts = pp.process(final_dx).getSections();
			for(Section part: parts){
				if(!hasMesothelioma(part)){
					for(ItemInstance ii: list){
						if(part.contains(ii.getMention())){
							torem.add(ii);
						}
					}
				}
			}
		}
		// remove extra instances
		if(!torem.isEmpty()){
			doc.getItemInstances(temp).removeAll(torem);
		}
		
	}
	
	
	private static TemplateItem getFeature(Template t, String code) throws TerminologyException{
		TemplateItem feature = new TemplateItem();
		feature.setConcept(t.getTerminology().lookupConcept(code));
		feature.setTemplate(t);
		return feature;
	}
	

	/**
	 * find greatest tumor dimension
	 * @param doc
	 * @throws TerminologyException 
	 */
	private void mergeTumorSizes(TemplateDocument doc) throws TerminologyException {
		Template temp = null;
		for(Template t: doc.getTemplates()){
			temp = t;
			break;
		}
		TemplateItem feature = getFeature(temp,"NMVB:Tumor_Dimension");
		List<ItemInstance> list = doc.getItemInstances(feature);
		ItemInstance greatest = null;
		for(ItemInstance i: list){
			if(greatest == null || isLargerSize(i.getAnswer(), greatest.getAnswer()))
				greatest = i;
		}
		if(greatest != null){
			doc.getItemInstances(temp).removeAll(list);
			doc.getItemInstances(temp).add(greatest);
		}
			
	}
	
	/**
	 * find greatest tumor dimension
	 * @param doc
	 * @throws TerminologyException 
	 */
	private void filterImmuno(TemplateDocument doc) throws TerminologyException {
		Template temp = null;
		for(Template t: doc.getTemplates()){
			temp = t;
			break;
		}
		TemplateItem immuno = getFeature(temp,"NMVB:Immunohistochemical_Profile");
		TemplateItem histo = getFeature(temp,"NMVB:Histochemical_Special_Stain_Profile");
		for(TemplateItem feature: new TemplateItem [] {immuno,histo}){
			Set<Concept> negatedConcepts = new HashSet<Concept>();
			List<ItemInstance> torem = new ArrayList<ItemInstance>();
			List<ItemInstance> list = doc.getItemInstances(feature);
			// if this histo is explicitly negated OR it was negated earlier
			for(ItemInstance i: list){
				if(i.getMention().isNegated() || negatedConcepts.contains(i.getConcept())){
					negatedConcepts.add(i.getConcept());
					torem.add(i);
				}
			}
			// remove extra instances
			if(!torem.isEmpty()){
				doc.getItemInstances(temp).removeAll(torem);
			}
		}		
	}
	
	
	private boolean isLargerSize(String s1, String s2){
		Pattern pt = Pattern.compile("\\d+(\\.\\d+)?");
		Matcher m1 = pt.matcher(s1);
		Matcher m2 = pt.matcher(s2);
		double d1 = 1.0;
		double d2 = 1.0;
		while(m1.find())
			d1 *= Double.parseDouble(m1.group());
		while(m2.find())
			d2 *= Double.parseDouble(m2.group());
		return d1 > d2;
	}
	
	
	public TemplateDocument process(TemplateDocument doc) throws TerminologyException {
		// do information extraction
		super.process(doc);
		
		// filter out parts where it is not mentioning mesothelioma
		filterParts(doc);
		
		// get greatest dimension
		mergeTumorSizes(doc);
		
		// filter immuno
		filterImmuno(doc);
		
		return doc;
	}

	/**
	 * This method will create a new directory for data files in the NMVB project.
	 * @return String representing the name of the new directory containing the preprocessed files.
	 */
	public static String createPreprocessDirectory(String dataDir) throws IOException {
		String retString = "";
		File fDataDir = new File(dataDir);
		File fNewDataDir = null;
		if (fDataDir.exists() && fDataDir.isDirectory() && fDataDir.canWrite()) {
			fNewDataDir = new File(dataDir,"preprocess");
			if (fNewDataDir.exists()) {
				System.out.println("The directory " + fNewDataDir.getAbsolutePath() + " already exists.  Removing existing files.");
				for(File file: fNewDataDir.listFiles()) file.delete();
			} else {
				if (fNewDataDir.mkdir()) {
				  fNewDataDir.createNewFile();
				} else {
				  throw new IOException("Failed to create directory " + fNewDataDir.getName());
				}
			}
			retString = fNewDataDir.getPath();
		}
		
		return retString;
	}
	
	public static void preprocess(String origDataPath, String preprocessPath) throws IOException {
		File origDataDir = new File(origDataPath);
		File preprocessDir = new File(preprocessPath);
		if (!origDataDir.exists() || !origDataDir.isDirectory() || !origDataDir.canRead()) {
			throw new IOException("Cannot access directory " + origDataPath);
		} 
		if (!preprocessDir.exists() || !preprocessDir.isDirectory() || !preprocessDir.canWrite()) {
			throw new IOException("Cannot access directory " + preprocessPath);
		} 
		File[] origFileList = origDataDir.listFiles();
		if (origFileList != null) {
			for (File datafile : origFileList) {
				if (datafile.isHidden()) {
					continue;
				}
				String section = extractSection(datafile);
				Writer writer = null;
				try {
					File newDataFile = new File(preprocessPath, datafile.getName());
				    writer = new BufferedWriter(new OutputStreamWriter(
				            new FileOutputStream(newDataFile), "utf-8"));
				      writer.write(section);
				} catch (IOException ex) {
					throw ex;
				} finally {
					try {
						writer.close();
					} catch (Exception e) {
						System.out.println("Error trying to close " + preprocessPath + File.pathSeparator + datafile.getName());
						e.printStackTrace(System.out);
					}
				}
				
			}
		} else {
			throw new IOException("No files found in directory " + origDataPath);
		}
		
	}
	
	public static String extractSection(File inputFile) throws IOException {
		String retString = "";
		FileInputStream fis = new FileInputStream(inputFile);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		StringBuffer sBuf = new StringBuffer();
		String strLine;
		boolean readData = false;
		while ((strLine = br.readLine()) != null) {
			//note: these are UPMC settings...
			if (strLine.startsWith("FINAL DIAGNOSIS:")) {
				readData = true;
			}
			//done processing
			if (strLine.startsWith("COMMENT:")) {
				readData = false;
				break;
			}
			if (readData) {
				//strLine.replace(":", "");
				sBuf.append(strLine + "\n");
			}			
		}
		try {
			br.close();
		} catch (Exception e) {
			System.out.println("Error trying to close " + inputFile.getAbsolutePath());
			e.printStackTrace(System.out);
		}

		retString = sBuf.toString();
		return retString;
	}

	public static void main(String[] args)  throws Exception {
		InformationExtractor nc = new NMVB_InformationExtractor();
		if(args.length == 0){
			nc.showDialog();
		}else if(args.length == 3){
			String newDirPath = "";
			try {
				newDirPath = NMVB_InformationExtractor.createPreprocessDirectory(args[1]);
				NMVB_InformationExtractor.preprocess(args[1], newDirPath);
				
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
			nc.process(Arrays.asList(nc.importTemplates(args[0])),newDirPath,args[2]);
		}else{
			System.err.println("Usage: java InformationExtractor [template] [input directory] [output directory]");
			System.err.println("Note:  If you don't specify parameters, GUI will pop-up");
		}
	}

}
