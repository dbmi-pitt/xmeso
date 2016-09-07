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

public class PartCreatorAnnotationEngine extends
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

	private final TreeSet<Annotation> sPartSet = new TreeSet<Annotation>(annotComparator);

	private final TreeSet<Integer> partNumbers = new TreeSet<Integer>();
	private final TreeSet<Integer> sectionLevels = new TreeSet<Integer>();
	private final TreeSet<Integer> codedPenalties = new TreeSet<Integer>();

	//private int reportNumber = 0;

	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {
//		System.out.println("Processing report " + reportNumber++);
		clearCaches();
		partizeFromExplicitlyLabeled(jCas);
		partizeFromInferred(jCas);
		ftrXchangeSectionToPart(jCas);
	}

	private void clearCaches() {
		sPartSet.clear();
		partNumbers.clear();
		sectionLevels.clear();
		codedPenalties.clear();
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
					Collection<PartNumber> partNumAnnots = JCasUtil.selectCovered(PartNumber.class, sAnnot);
					for (PartNumber partNumAnnot : partNumAnnots) {
						Part part = new Part(jCas);
						partNumbers.add(partNumAnnot.getPartNumber());
						part.setPartNumber(partNumAnnot.getPartNumber());
						int sPos = sAnnot.getEnd();
						int ePos = eAnnot.getBegin();
						if (eAnnot.getCoveredText().startsWith("\n")) {
							ePos = eAnnot.getBegin() - 1;
						}
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
}

