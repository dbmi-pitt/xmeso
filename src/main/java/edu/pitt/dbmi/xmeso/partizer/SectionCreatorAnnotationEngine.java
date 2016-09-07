package edu.pitt.dbmi.xmeso.partizer;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import edu.pitt.dbmi.xmeso.model.Model.EndOfDocument;
import edu.pitt.dbmi.xmeso.model.Model.EndOfSection;
import edu.pitt.dbmi.xmeso.model.Model.Section;
import edu.pitt.dbmi.xmeso.model.Model.SectionHeader;

public class SectionCreatorAnnotationEngine extends JCasAnnotator_ImplBase {

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

	private final TreeSet<Annotation> sortedDelimiters = new TreeSet<Annotation>(annotComparator);


	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {
		sortedDelimiters.clear();
		sortedDelimiters.addAll(JCasUtil.select(jCas, SectionHeader.class));
		sortedDelimiters.addAll(JCasUtil.select(jCas, EndOfSection.class));
		sortedDelimiters.addAll(JCasUtil.select(jCas, EndOfDocument.class));
		Iterator<Annotation> iterOne = sortedDelimiters.iterator();
		Iterator<Annotation> iterTwo = sortedDelimiters.iterator();
		iterTwo.next();
		while (iterOne.hasNext() && iterTwo.hasNext()) {
			Annotation annotOne = iterOne.next();
			Annotation annotTwo = iterTwo.next();
			if (annotOne instanceof SectionHeader && ((annotTwo instanceof EndOfSection) || (annotTwo instanceof EndOfDocument))) {			
				int sPos = annotOne.getEnd();
				int ePos = annotTwo.getEnd();
				if (ePos - sPos > 0) {
					Section section = new Section(jCas);
					section.setBegin(sPos);
					section.setEnd(ePos);
					SectionHeader sectionHeader = (SectionHeader) annotOne;
					section.setName(sectionHeader.getName());
					section.setLevel(sectionHeader.getLevel());
					jCas.addFsToIndexes(section);
				}
			}
		}
	}
}
