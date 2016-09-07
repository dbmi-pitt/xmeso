package edu.pitt.dbmi.xmeso.sentence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.Tokenizer;
import com.aliasi.tokenizer.TokenizerFactory;

public class SentenceDetectorUtils {
	
	private static TokenizerFactory tokenizerInstance = null;
	
	public static final String WHITE_SPACES = "WHITE_SPACES";
	public static final String TOKENS = "TOKENS";
	
	public static void initializeTokenizer() {
		if(tokenizerInstance == null) {
			tokenizerInstance = IndoEuropeanTokenizerFactory.INSTANCE;
		} 
	}
	
   public static Map<String, String[]> tokenizeText(String text) {
		
		List<String> tokenList = new ArrayList<String>();
		List<String> whiteList = new ArrayList<String>();
		
		initializeTokenizer();
		Tokenizer tokenizer = tokenizerInstance.tokenizer(text.toCharArray(),0,text.length());
		tokenizer.tokenize(tokenList,whiteList);
		
		String[] tokens = new String[tokenList.size()];
		String[] whiteSpaces = new String[whiteList.size()];
		
		tokenList.toArray(tokens);
		whiteList.toArray(whiteSpaces);
		
		Map<String,String[]> tokensMap = new HashMap<String, String[]>();
		tokensMap.put(TOKENS, tokens);
		tokensMap.put(WHITE_SPACES, whiteSpaces);
		
		return tokensMap;
	}
   
   public static List<String> breakDownToSentences(SentenceDetector boundaryDetector, String[] tokens, String[] whiteSpaces) {
		
		int [] boundaryIndices = boundaryDetector.boundaryIndices(tokens, whiteSpaces);
		List<String> sentences = new LinkedList<String>();
		
		int sentStartTok = 0;
		int sentEndTok = 0;
		for (int i = 0; i < boundaryIndices.length; ++i) {
		    sentEndTok = boundaryIndices[i];
		    List<String> words = new LinkedList<String>();
		    for (int j=sentStartTok; j <= sentEndTok; j++) {
		    	words.add(tokens[j]+whiteSpaces[j+1]);
		    }
		    String sentence = "";
		    for(String store: words) {
		    	sentence = sentence + store;
		    }
		    sentences.add(sentence);
		    sentStartTok = sentEndTok+1;
		}
		return sentences;
	}
   
   

}
