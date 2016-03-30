package edu.pitt.dbmi.xmeso.partizer;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import edu.pitt.dbmi.xmeso.model.Model.EndOfDocument;
import edu.pitt.dbmi.xmeso.model.Model.EndOfSection;
import edu.pitt.dbmi.xmeso.model.Model.Part;
import edu.pitt.dbmi.xmeso.model.Model.PartHeaderGenerator;
import edu.pitt.dbmi.xmeso.model.Model.PartNumber;
import edu.pitt.dbmi.xmeso.model.Model.Section;
import edu.pitt.dbmi.xmeso.model.Model.XmesoNamedEntity;
import edu.pitt.dbmi.xmeso.model.Model.XmesoSize;
import edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm;

public class PartizerAnnotationEngine extends
		org.apache.uima.fit.component.JCasAnnotator_ImplBase {

	/**
	 * Given a set of PartHeaderGenerator Annotations covering a series of
	 * PartNumbers make a new PartHeader Annotation spanning the
	 * PartHeaderGenerator taking on the partNumber from its subsumed Annotation
	 * (also called PartNumber)
	 */
	private Comparator<Annotation> annotComparator = new Comparator<Annotation>() {
		@Override
		public int compare(Annotation o1, Annotation o2) {
			int retValue = o1.getBegin() - o2.getBegin();
			retValue = (retValue == 0) ? o1.getEnd() - o2.getEnd() : retValue;
			retValue = (retValue == 0) ? -1 : retValue;
			return retValue;
		}
	};

	private final TreeSet<Annotation> sPartSet = new TreeSet<Annotation>(
			annotComparator);

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
		System.out.println("Processing report " + reportNumber++);
		clearCaches();
		partizeFromExplicitlyLabeled(jCas);
		partizeFromInferred(jCas);
		ftrXchangeSectionToPart(jCas);
		ftrXchangePartToNamedEntity(jCas);
		cacheEndOfDocument(jCas);
		initalizeSizeCodePenalties(jCas);
		initializeDefaultTumorForm(jCas);
		gatherSectionLevels(jCas);
		gatherCodePenalties(jCas);
		// displayDimensions();
		fillTumorForms(jCas);
	}

	private void clearCaches() {
		sPartSet.clear();
		partNumbers.clear();
		sectionLevels.clear();
		codedPenalties.clear();
	}

	@SuppressWarnings("unused")
	private void displayDimensions() {
		System.out.println("Displaying dimensions");
		StringBuilder sb = new StringBuilder();
		sb.append("Part Numbers: ");
		for (Integer partNumber : partNumbers) {
			sb.append(partNumber + ", ");
		}
		System.out.println(sb.toString());
		sb = new StringBuilder();
		sb.append("Section Levels: ");
		for (Integer sectionLevel : sectionLevels) {
			sb.append(sectionLevel + ", ");
		}
		System.out.println(sb.toString());
		sb = new StringBuilder();
		sb.append("Coded Penalties:  ");
		for (Integer codedPenalty : codedPenalties) {
			sb.append(codedPenalty + ", ");
		}
		System.out.println(sb.toString());
	}

	private void partizeFromExplicitlyLabeled(JCas jCas) {
		sPartSet.clear();
		sPartSet.addAll(JCasUtil.select(jCas, PartHeaderGenerator.class));
		sPartSet.addAll(JCasUtil.select(jCas, EndOfSection.class));
		sPartSet.addAll(JCasUtil.select(jCas, EndOfDocument.class));
		Iterator<Annotation> sPartIterator = sPartSet.iterator();
		Iterator<Annotation> ePartIterator = sPartSet.iterator();
		if (ePartIterator.hasNext()) {
			ePartIterator.next();
			while (sPartIterator.hasNext() && ePartIterator.hasNext()) {
				Annotation sAnnot = sPartIterator.next();
				Annotation eAnnot = ePartIterator.next();
				if (sAnnot instanceof PartHeaderGenerator) {
					Collection<PartNumber> nes = JCasUtil.selectCovered(
							PartNumber.class, sAnnot);
					for (PartNumber ne : nes) {
						Part part = new Part(jCas);
						partNumbers.add(ne.getPartNumber());
						part.setPartNumber(ne.getPartNumber());
						int sPos = sAnnot.getEnd() + 1;
						int ePos = eAnnot.getBegin() - 1;
						if (ePos - sPos > 0) {
							part.setBegin(sPos);
							part.setEnd(ePos);
							part.addToIndexes(jCas);
						}
					}
				}
			}
		}
		if (partNumbers.isEmpty()) {
			partNumbers.add(1);
		}
	}

	private void partizeFromInferred(JCas jCas) {
		if (partNumbers.size() > 0) {
			for (Section section : JCasUtil.select(jCas, Section.class)) {
				if (JCasUtil.selectCovered(Part.class, section).size() == 0) {
					for (Integer partNumber : partNumbers) {
						Part part = new Part(jCas);
						part.setSectionLevel(section.getLevel());
						part.setSectionName(section.getName());
						part.setPartNumber(partNumber);
						part.setBegin(section.getBegin());
						part.setEnd(section.getEnd());
						part.addToIndexes(jCas);
						break;
					}
				}
			}
		}
	}

	private void ftrXchangeSectionToPart(JCas jCas) {
		for (Section section : JCasUtil.select(jCas, Section.class)) {
			for (Part part : JCasUtil.selectCovered(Part.class, section)) {
				part.setSectionLevel(section.getLevel());
				part.setSectionName(section.getName());
				part.addToIndexes(jCas);
			}
		}
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
		this.endOfDocument = JCasUtil.select(jCas, EndOfDocument.class)
				.iterator().next();
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

	@SuppressWarnings("unused")
	private String annotToText(Annotation annot) {
		StringBuilder sb = new StringBuilder();
		sb.append(annot.getClass().getSimpleName() + ": (");
		sb.append(annot.getBegin() + ",");
		sb.append(annot.getEnd() + ") => ");
		if (annot.getCoveredText().equals("\n")) {
			sb.append("EOLN");
		} else {
			sb.append(annot.getCoveredText());
		}
		return sb.toString();
	}

	@SuppressWarnings("unused")
	private String annotToTextWithNoCovering(Annotation annot) {
		StringBuilder sb = new StringBuilder();
		sb.append(annot.getClass().getSimpleName() + ": (");
		sb.append(annot.getBegin() + ",");
		sb.append(annot.getEnd() + ") => ");
		return sb.toString();
	}

}
