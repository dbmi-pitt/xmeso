package edu.pitt.dbmi.xmeso.decomposition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.aliasi.sentences.HeuristicSentenceModel;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.Tokenizer;
import com.aliasi.tokenizer.TokenizerFactory;

public class SentenceDetectorConfigurator {
	
	private TokenizerFactory tokenizerFactory = IndoEuropeanTokenizerFactory.INSTANCE;
	
	private final Set<String> possibleSentenceStops = new HashSet<String>();
	private final Set<String> impossiblePenultimates = new HashSet<String>();
	private final Set<String> impossibleSentenceStarts = new HashSet<String>();

	private HeuristicSentenceModel detector;

	public SentenceDetectorConfigurator() {
		this(true, true);
	}
	
	public SentenceDetectorConfigurator(boolean forceFinalStop, boolean balanceParentheses) {
		initializePossibleStops();
		initializeImpossiblePenultimates();
		initializeImpossibleSentenceStarts();
		detector = new HeuristicSentenceModel(
				possibleSentenceStops,
				impossiblePenultimates,
				impossibleSentenceStarts,
				forceFinalStop,
				balanceParentheses);
	}

	public void initializePossibleStops() {
		possibleSentenceStops.add(".");
		possibleSentenceStops.add(":");
		possibleSentenceStops.add("-");
    }

	public void initializeImpossiblePenultimates() {
		impossiblePenultimates.add("Mme");
		impossiblePenultimates.add("Dr");
		impossiblePenultimates.add("Mr");
		impossiblePenultimates.add("Mrs");
	}

	public void initializeImpossibleSentenceStarts() {
		impossibleSentenceStarts.add(")");
		impossibleSentenceStarts.add("]");
		impossibleSentenceStarts.add("}");
		impossibleSentenceStarts.add(">");
		impossibleSentenceStarts.add("<");
		impossibleSentenceStarts.add(".");
		impossibleSentenceStarts.add("!");
		impossibleSentenceStarts.add("?");
		impossibleSentenceStarts.add(":");
		impossibleSentenceStarts.add(";");
		impossibleSentenceStarts.add("-");
		impossibleSentenceStarts.add("--");
		impossibleSentenceStarts.add("---");
		impossibleSentenceStarts.add("%");
	}

	public List<String> createSentencesForPart(String partContent) {
		final List<String> sentences = new ArrayList<String>();
		final List<String> tokenList = new ArrayList<String>();
		final List<String> spaceList = new ArrayList<String>();
		Tokenizer tokenizer = tokenizerFactory.tokenizer(partContent.toCharArray(), 0, partContent.length());
		tokenizer.tokenize(tokenList,spaceList);
		String[] tokenArray = new String[tokenList.size()];
		String[] spaceArray = new String[spaceList.size()];
	    tokenList.toArray(tokenArray);
		spaceList.toArray(spaceArray);
		
		int[] sentenceEndIndices = detector.boundaryIndices(tokenArray, spaceArray);

		int sentStartTok = 0;
		int sentEndTok = 0;
		for (int i = 0; i < sentenceEndIndices.length; i++) {
		    sentEndTok = sentenceEndIndices[i];
		    List<String> words = new LinkedList<String>();
		    for (int j=sentStartTok; j <= sentEndTok; j++) {
		    	words.add(tokenArray[j]+spaceArray[j+1]);
		    }
		    String sentence = StringUtils.join(words,"");
		    sentences.add(sentence);
		    sentStartTok = sentEndTok+1;
		}
		return sentences;
	}

}
