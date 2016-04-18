package edu.pitt.dbmi.xmeso.ie;

import java.util.Iterator;
import java.util.TreeSet;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import edu.pitt.dbmi.xmeso.model.Model.EndOfDocument;
import edu.pitt.dbmi.xmeso.model.Model.Part;
import edu.pitt.dbmi.xmeso.model.Model.Section;
import edu.pitt.dbmi.xmeso.model.Model.XmesoNamedEntity;
import edu.pitt.dbmi.xmeso.model.Model.XmesoSize;
import edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm;

public class InformationExtractorAnnotationEngine extends
		org.apache.uima.fit.component.JCasAnnotator_ImplBase {

	private final TreeSet<Integer> partNumbers = new TreeSet<Integer>();
	private final TreeSet<Integer> sectionLevels = new TreeSet<Integer>();
	private final TreeSet<Integer> codedPenalties = new TreeSet<Integer>();

	private int currentPart = -1;
	private int currentSectionLevel = -1;
	private int currentCodedPenalty = -1;

	private EndOfDocument endOfDocument;

	private XmesoTumorForm defaultTumorForm;
	private XmesoTumorForm currentTumorForm;

	private int reportNumber = 0;

	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {
		System.out.println("InformationExtractor Processing report "
				+ reportNumber++);
		clearCaches();
		ftrXchangePartToNamedEntity(jCas);
		cacheEndOfDocument(jCas);
		initalizeSizeCodePenalties(jCas);
		initializeDefaultTumorForm(jCas);
		gatherSectionLevels(jCas);
		gatherPartLevels(jCas);
		gatherCodePenalties(jCas);
		fillTumorForms(jCas);
	}

	private void clearCaches() {
		partNumbers.clear();
		sectionLevels.clear();
		codedPenalties.clear();
	}

	private void ftrXchangePartToNamedEntity(JCas jCas) {
		for (Part part : JCasUtil.select(jCas, Part.class)) {
			for (XmesoNamedEntity namedEntity : JCasUtil.selectCovered(
					XmesoNamedEntity.class, part)) {
				namedEntity.setPartNumber(part.getPartNumber());
				namedEntity.setSectionName(part.getSectionName());
				namedEntity.setSectionLevel(part.getSectionLevel());
				namedEntity.addToIndexes(jCas);
			}
		}
	}

	private void cacheEndOfDocument(JCas jCas) {
		endOfDocument = JCasUtil.select(jCas, EndOfDocument.class).iterator()
				.next();
	}

	private void initalizeSizeCodePenalties(JCas jCas) {
		for (XmesoSize xmesoSize : JCasUtil.select(jCas, XmesoSize.class)) {
			xmesoSize.setCodedPenalty(1);
		}
	}

	private void initializeDefaultTumorForm(JCas jCas) {
		defaultTumorForm = new XmesoTumorForm(jCas);
		defaultTumorForm.setCurrentPart(-1);
		defaultTumorForm.setSurgicalProcedure("PROC:UNKNOWN");
		defaultTumorForm.setHistopathologicalType("HIST_TYPE:UNKNOWN");
		defaultTumorForm.setTumorSite("TUMOR_SITE:UNKNOWN");
		defaultTumorForm.setTumorConfiguration("TUMOR_CONFIG:UNKNOWN");
		defaultTumorForm.setTumorDifferentiation("ANA|TUMOR_DIFF:NA");
		defaultTumorForm.setSizeDimensionX(-1.0f);
		defaultTumorForm.setSizeDimensionY(-1.0f);
		defaultTumorForm.setSizeDimensionZ(-1.0f);
		defaultTumorForm.setSizeDimensionMax(-1.0f);
		defaultTumorForm.setBegin(endOfDocument.getBegin());
		defaultTumorForm.setEnd(endOfDocument.getEnd());
	}

	private void initializeCurrentTumorForm(JCas jCas) {
		currentTumorForm = new XmesoTumorForm(jCas);
		currentTumorForm.setCurrentPart(currentPart);
		currentTumorForm.setSurgicalProcedure(defaultTumorForm
				.getSurgicalProcedure());
		currentTumorForm.setHistopathologicalType(defaultTumorForm
				.getHistopathologicalType());
		currentTumorForm.setTumorSite(defaultTumorForm.getTumorSite());
		currentTumorForm.setTumorConfiguration(defaultTumorForm
				.getTumorConfiguration());
		currentTumorForm.setTumorDifferentiation(defaultTumorForm
				.getTumorDifferentiation());
		currentTumorForm.setBegin(defaultTumorForm.getBegin());
		currentTumorForm.setEnd(defaultTumorForm.getEnd());
	}

	private void gatherSectionLevels(JCas jCas) {
		for (Section section : JCasUtil.select(jCas, Section.class)) {
			int sectionLevel = section.getLevel();
			sectionLevels.add(sectionLevel);
		}
	}

	private void gatherPartLevels(JCas jCas) {
		for (Part part : JCasUtil.select(jCas, Part.class)) {
			int partNumber = part.getPartNumber();
			partNumbers.add(partNumber);
		}
	}

	private void gatherCodePenalties(JCas jCas) {
		for (XmesoNamedEntity namedEntity : JCasUtil.select(jCas,
				XmesoNamedEntity.class)) {
			int codedPenalty = namedEntity.getCodedPenalty();
			codedPenalties.add(codedPenalty);
		}
	}

	private void fillTumorForms(JCas jCas) {
		for (Iterator<Integer> partNumberIterator = partNumbers.iterator(); partNumberIterator
				.hasNext();) {
			currentPart = partNumberIterator.next();
			initializeCurrentTumorForm(jCas);
			for (Iterator<Integer> sectionLevelIterator = sectionLevels
					.iterator(); sectionLevelIterator.hasNext();) {
				currentSectionLevel = sectionLevelIterator.next();
				for (Iterator<Integer> codedPenaltyIterator = codedPenalties
						.iterator(); codedPenaltyIterator.hasNext();) {
					currentCodedPenalty = codedPenaltyIterator.next();
					fillRemainingFormSlots(jCas);
				}
			}
			jCas.addFsToIndexes(currentTumorForm);
		}
	}

	private void fillRemainingFormSlots(JCas jCas) {
		for (Section section : JCasUtil.select(jCas, Section.class)) {
			for (Part part : JCasUtil.selectCovered(Part.class, section)) {
				for (XmesoNamedEntity namedEntity : JCasUtil.selectCovered(
						XmesoNamedEntity.class, part)) {
					boolean isCurrent = true;
					isCurrent = isCurrent
							&& (section.getLevel() == currentSectionLevel);
					isCurrent = isCurrent
							&& (part.getPartNumber() == currentPart);
					isCurrent = isCurrent
							&& (namedEntity.getCodedPenalty() == currentCodedPenalty);
					isCurrent = isCurrent && !namedEntity.getIsNegated();
					if (isCurrent) {
						String simpleClassName = namedEntity.getClass()
								.getSimpleName();
						if ("XmesoSurgicalProcedure".equals(simpleClassName)) {
							if (defaultTumorForm.getSurgicalProcedure().equals(
									currentTumorForm.getSurgicalProcedure())) {
								currentTumorForm
										.setSurgicalProcedure(namedEntity
												.getSnomedCode());
							}
						} else if ("XmesoHistologicalType"
								.equals(simpleClassName)) {
							if (defaultTumorForm.getHistopathologicalType()
									.equals(currentTumorForm
											.getHistopathologicalType())) {
								currentTumorForm
										.setHistopathologicalType(namedEntity
												.getSnomedCode());
							}
						} else if ("XmesoTumorSite".equals(simpleClassName)) {
							if (defaultTumorForm.getTumorSite().equals(
									currentTumorForm.getTumorSite())) {
								currentTumorForm.setTumorSite(namedEntity
										.getSnomedCode());
							}
						} else if ("XmesoTumorConfiguration"
								.equals(simpleClassName)) {
							if (defaultTumorForm.getTumorConfiguration()
									.equals(currentTumorForm
											.getTumorConfiguration())) {
								currentTumorForm
										.setTumorConfiguration(namedEntity
												.getSnomedCode());
							}
						} else if ("XmesoTumorDifferentiation"
								.equals(simpleClassName)) {
							if (defaultTumorForm.getTumorDifferentiation()
									.equals(currentTumorForm
											.getTumorDifferentiation())) {
								currentTumorForm
										.setTumorDifferentiation(namedEntity
												.getSnomedCode());
							}
						} else if ("XmesoSize".equals(simpleClassName)) {
							XmesoSize currentSize = (XmesoSize) namedEntity;
							if (currentSize.getMaxDim() > currentTumorForm
									.getSizeDimensionMax()) {
								currentTumorForm.setSizeDimensionX(currentSize
										.getDimOne());
								currentTumorForm.setSizeDimensionY(currentSize
										.getDimTwo());
								currentTumorForm.setSizeDimensionZ(currentSize
										.getDimThree());
								currentTumorForm
										.setSizeDimensionMax(currentSize
												.getMaxDim());
							}
						}
					}
				}
			}
		}
	}

}
