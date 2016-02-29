package edu.pitt.dbmi.xmeso.ctakes.ae;

import java.util.ArrayList;
import java.util.List;

import org.apache.ctakes.typesystem.type.textspan.LookupWindowAnnotation;
import org.apache.log4j.Logger;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.analysis_engine.ResultSpecification;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.resource.ResourceInitializationException;

import edu.pitt.dbmi.nlp.noble.coder.NobleCoder;
import edu.pitt.dbmi.nlp.noble.coder.model.Mention;
import edu.pitt.dbmi.nlp.noble.terminology.Annotation;
import edu.pitt.dbmi.nlp.noble.terminology.Concept;
import edu.pitt.dbmi.nlp.noble.terminology.SemanticType;
import edu.pitt.dbmi.nlp.noble.terminology.Source;
import edu.pitt.dbmi.nlp.noble.terminology.Term;
import edu.pitt.dbmi.nlp.noble.terminology.TerminologyException;
import edu.pitt.dbmi.nlp.noble.terminology.impl.NobleCoderTerminology;
import edu.pitt.dbmi.nlp.noble.uima.types.Sentence;
import edu.pitt.dbmi.nlp.noble.util.StringUtils;

public class NobleCoderUimaAnnotator extends JCasAnnotator_ImplBase {

	private static final Logger logger = Logger
			.getLogger(NobleCoderUimaAnnotator.class);

	private NobleCoderTerminology terminology = new NobleCoderTerminology();
	private NobleCoder coder = new NobleCoder();

	private FormDrivenNamedEntityRecognizer ner = null;

	public NobleCoderUimaAnnotator() {
		logger.debug("Constructing a NobleCoderUimaAnnotator.");
	}

	/**
	 * Initialize the annotator, which includes compilation of regular
	 * expressions, fetching configuration parameters from XML descriptor file,
	 * and loading of the dictionary file.
	 */
	public void initialize(UimaContext uimaContext)
			throws ResourceInitializationException {

		ner = new FormDrivenNamedEntityRecognizer();

		terminology.setContiguousMode(false);
		terminology.setDefaultSearchMethod(NobleCoderTerminology.BEST_MATCH);
//		terminology.setIgnoreAcronyms(true);
		terminology.setIgnoreCommonWords(true);
		terminology.setIgnoreSmallWords(true);
		terminology.setIgnoreUsedWords(true);
		terminology.setOrderedMode(false);
		terminology.setOverlapMode(true);
		terminology.setPartialMode(false);
		terminology.setScoreConcepts(false);
		terminology.setSelectBestCandidate(true);
		terminology.setSubsumptionMode(false);
		terminology.setMaximumWordGap(3);
		ner.setTerminology(terminology);
		ner.execute();
		
		try {
			for (Concept concept : terminology.getConcepts()) {
				System.out.println(concept);
			}
		} catch (TerminologyException e) {
			e.printStackTrace();
		}

		coder.setHandleNegation(false);
		coder.setHandleAcronymExpansion(false);
		coder.setTerminology(terminology);
	}

	@SuppressWarnings("unused")
	private void parameterizeSemanticTypes(UimaContext uimaContext) {
		String colonSeparatedTuis = (String) uimaContext
				.getConfigParameterValue("tui.filter");
		String colonSeparatedStys = (String) uimaContext
				.getConfigParameterValue("sty.filter");
		if (!StringUtils.isEmpty(colonSeparatedTuis)
				&& !StringUtils.isEmpty(colonSeparatedStys)) {
			final String[] tuis = colonSeparatedTuis.split(":");
			final String[] stys = colonSeparatedStys.split(":");
			final SemanticType[] semTypeFilters = new SemanticType[tuis.length];
			for (int tdx = 0; tdx < tuis.length; tdx++) {
				semTypeFilters[tdx] = SemanticType.getSemanticType(stys[tdx],
						tuis[tdx]);
			}
			terminology.setSemanticTypeFilter(semTypeFilters);
		} else {
			terminology.setSemanticTypeFilter("");
		}
	}

	@SuppressWarnings("unused")
	private void parameterizeSources(UimaContext uimaContext) {
		String colonSeparatedSources = (String) uimaContext
				.getConfigParameterValue("source.filter");
		if (!StringUtils.isEmpty(colonSeparatedSources)) {
			final String[] sabs = colonSeparatedSources.split(":");
			final Source[] sourceFilters = new Source[sabs.length];
			for (int tdx = 0; tdx < sabs.length; tdx++) {
				sourceFilters[tdx] = new Source(sabs[tdx]);
			}
			terminology.setSourceFilter(sourceFilters);
		}
	}

	@SuppressWarnings("unused")
	private void testNobleCoder(String exampleSentence)
			throws TerminologyException {
		List<Mention> mentions = coder.process(exampleSentence);
		StringBuilder sb = new StringBuilder();
		for (Mention mention : mentions) {
			Concept conceptForMention = mention.getConcept();
			sb.append(conceptForMention.getCode() + ", "
					+ conceptForMention.getName());
			List<Annotation> annotations = mention.getAnnotations();
			for (Annotation annotation : annotations) {
				sb.append(" (" + annotation.getStartPosition() + ", "
						+ annotation.getEndPosition() + ") ");
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}

	/**
	 * Perform the actual analysis. Iterate over the document content looking
	 * for any matching words or phrases in the loaded dictionary and post an
	 * annotation for each match found.
	 * 
	 * @param tcas
	 *            the current CAS to process.
	 * @param aResultSpec
	 *            a specification of the result annotation that should be
	 *            created by this annotator
	 * 
	 * @see org.apache.uima.analysis_engine.annotator.TextAnnotator#process(CAS,ResultSpecification)
	 */
	@SuppressWarnings("rawtypes")
	public void process(JCas jCas) throws AnalysisEngineProcessException {
		try {
			// CAS tcas = jCas.getCas();
//			FSIndex<?> dbIndex = jCas
//					.getAnnotationIndex(LookupWindowAnnotation.type);
			FSIndex<?> dbIndex = jCas
					.getAnnotationIndex(Sentence.type);
			FSIterator spanIterator = dbIndex.iterator();
			while (spanIterator.hasNext()) {
				AnnotationFS lookupWindowAnnotation = (AnnotationFS) spanIterator
						.next();
				String lookupWindowContent = lookupWindowAnnotation
						.getCoveredText();
				System.out.println("NC startPos of "
						+ lookupWindowAnnotation.getBegin() + "\n"
						+ lookupWindowContent);
				List<Mention> mentions = coder.process(lookupWindowContent);
				if (mentions.isEmpty()) {
					System.out.println("No Concepts found");
				} else {
					System.out.println("Found mentions");
					for (Mention m : mentions) {
						Concept srcConcept = m.getConcept();
						int[] annotationRange = deriveAnnotationRange(m
								.getAnnotations());

						edu.pitt.dbmi.nlp.noble.uima.types.Concept tgtConcept = new edu.pitt.dbmi.nlp.noble.uima.types.Concept(
								jCas);

						tgtConcept.setBegin(lookupWindowAnnotation.getBegin()
								+ annotationRange[0]);
						tgtConcept.setEnd(lookupWindowAnnotation.getBegin()
								+ annotationRange[1]);
						tgtConcept.setCui(srcConcept.getCode());
						tgtConcept.setCn(srcConcept.getName());
						tgtConcept.setDefinition(srcConcept.getDefinition());
						Term preferredTerm = srcConcept.getPreferredTerm();
						if (preferredTerm != null) {
							tgtConcept.setPreferredTerm(srcConcept
									.getPreferredTerm().getText());
						}
						populateSemanticTypes(jCas, srcConcept, tgtConcept);
						populateSynonyms(jCas, srcConcept, tgtConcept);
						tgtConcept.addToIndexes(jCas);
					}

				}
			}
		} catch (Exception e) {
			throw new AnalysisEngineProcessException(e);
		}
	}

	@Override
	public void collectionProcessComplete() {
		if (terminology != null) {
			// System.out.println("DISPOSING of NC Terminology...");
			terminology.dispose();
			terminology = null;
			// System.out.println("FINISHED of NC Terminology...");
		}
	}

	private void populateSemanticTypes(JCas jCas, Concept c,
			edu.pitt.dbmi.nlp.noble.uima.types.Concept tgtConcept) {
		SemanticType[] semTypes = c.getSemanticTypes();
		ArrayList<edu.pitt.dbmi.nlp.noble.uima.types.SemanticType> semTypeArray = new ArrayList<edu.pitt.dbmi.nlp.noble.uima.types.SemanticType>();
		for (SemanticType sType : semTypes) {
			edu.pitt.dbmi.nlp.noble.uima.types.SemanticType tgtSemType = new edu.pitt.dbmi.nlp.noble.uima.types.SemanticType(
					jCas);
			tgtSemType.setBegin(tgtConcept.getBegin());
			tgtSemType.setEnd(tgtConcept.getEnd());
			tgtSemType.setTui(sType.getCode());
			tgtSemType.setSty(sType.getName());
			semTypeArray.add(tgtSemType);
		}
		FSArray sTypeArray = new FSArray(jCas, semTypeArray.size());
		FeatureStructure[] semTypeFtrStructArray = new FeatureStructure[semTypeArray
				.size()];
		semTypeArray.toArray(semTypeFtrStructArray);
		sTypeArray.copyFromArray(semTypeFtrStructArray, 0, 0,
				semTypeFtrStructArray.length);
		tgtConcept.setTuis(sTypeArray);
	}

	private void populateSynonyms(JCas jCas, Concept c,
			edu.pitt.dbmi.nlp.noble.uima.types.Concept tgtConcept) {
		StringArray synonymStringArray = new StringArray(jCas,
				c.getSynonyms().length);
		synonymStringArray.copyFromArray(c.getSynonyms(), 0, 0,
				c.getSynonyms().length);
		tgtConcept.setSynonyms(synonymStringArray);
	}

	private int[] deriveAnnotationRange(List<Annotation> annotations) {
		int maxIntValue = Integer.MIN_VALUE;
		int minIntValue = Integer.MAX_VALUE;
		for (Annotation annot : annotations) {
			if (annot.getStartPosition() < minIntValue) {
				minIntValue = annot.getStartPosition();
			}
			if (annot.getEndPosition() > maxIntValue) {
				maxIntValue = annot.getEndPosition();
			}
		}
		final int[] result = new int[2];
		result[0] = minIntValue;
		result[1] = maxIntValue;
		return result;
	}

}
