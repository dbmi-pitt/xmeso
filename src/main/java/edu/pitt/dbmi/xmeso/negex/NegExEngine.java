package edu.pitt.dbmi.xmeso.negex;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class NegExEngine {

	private List<NegPhrase> negPhrases;

	private final int scopeBoundaryLength = 6;
	private final String PSEUDO_NEG_PHRASES = "PSEUDO-NEG-PHRASES";
	private final String PRE_NEG_PHRASES = "PRE-NEG-PHRASES";
	private final String STOP_WORDS = "STOP-WORDS";
	private final String POST_NEG_PHRASES = "POST-NEG-PHRASES";
	
	//private final String NEGEX_DICTIONARY_PATH = "resources/negex/NegExDictionary.txt";
	private final String NEGEX_DICTIONARY_PATH = "C:\\ws\\ws-xmeso\\xmeso\\resources\\negex\\NegExDictionary.txt";
	

	public NegExEngine() {
		negPhrases = new LinkedList<NegPhrase>();
		initializeTerminology(NEGEX_DICTIONARY_PATH);
		System.out.println("DICTIONARY SIZE : "+negPhrases.size());
	}

	public List<NegPhrase> findPossibleNegatedConcepts(String sentence) {
		if(sentence == null || sentence.isEmpty()) {
			return null;
		} else {
			String words[] = sentence.split("\\s+");
			List<NegPhrase> negationsIdentified = processNegations(words, negPhrases);
			List<NegPhrase> results = defineScope(negationsIdentified, words); 
			return results;
		}
	}

	public List<NegPhrase> defineScope(List<NegPhrase> negationConcepts, String[] words) {
		if(negationConcepts != null && !negationConcepts.isEmpty()) {
			List<NegPhrase> scopeBoundaries = new LinkedList<NegPhrase>();
			NegPhrase phrase = null;
			for(int i=0; i<negationConcepts.size(); i++) {
				phrase = negationConcepts.get(i);
				if(phrase != null) {
					if(phrase.getNegationType().trim().equals(PSEUDO_NEG_PHRASES)) {
						continue;
					} else if(phrase.getNegationType().trim().equals(POST_NEG_PHRASES)){
						String sequence = "";
						int start = 0;
						int end = phrase.getStartIndex();
						if((end - scopeBoundaryLength) >= 0){
							start = end - scopeBoundaryLength;
							for(int id = start; id < end; id++){
								sequence = sequence + words[id]+" ";
							}
						} else {
							for(int id = 0; id < end; id++){
								sequence = sequence + words[id]+" ";
							}
						}
						if(!sequence.isEmpty()) {
							NegPhrase negationScope = new NegPhrase();
							negationScope.setStartIndex(start);
							negationScope.setEndIndex(end);
							negationScope.setCoveredText(sequence);
							scopeBoundaries.add(negationScope);
						}
					} else if(phrase.getNegationType().trim().equals(PRE_NEG_PHRASES)) {
						if(((i + 1) >= 0) && ((i + 1) < negationConcepts.size())) {
							NegPhrase next = negationConcepts.get(i + 1);
							if(next != null ){
								int start = phrase.getEndIndex();
								int end = next.getStartIndex();
								String sequence = "";
								for(int id = start; id<end; id++){
									sequence = sequence + words[id]+" ";
								}
								if(!sequence.isEmpty()) {
									NegPhrase negationScope = new NegPhrase();
									negationScope.setStartIndex(start);
									negationScope.setEndIndex(end);
									negationScope.setCoveredText(sequence);
									scopeBoundaries.add(negationScope);
								}
							} 
						} else {
							int start = phrase.getEndIndex();
							String sequence = "";
							if((start+scopeBoundaryLength) < words.length) {
								int end = (start+scopeBoundaryLength); 
								for(int id = start; id<end; id++){
									sequence = sequence + words[id]+" ";
								}
								if(!sequence.isEmpty()) {
									NegPhrase negationScope = new NegPhrase();
									negationScope.setStartIndex(start);
									negationScope.setEndIndex(end);
									negationScope.setCoveredText(sequence);
									scopeBoundaries.add(negationScope);
								}
							} else {
								for(int id = start; id<words.length; id++){
									sequence = sequence + words[id]+" ";
								}
								if(!sequence.isEmpty()) {
									NegPhrase negationScope = new NegPhrase();
									negationScope.setStartIndex(start);
									negationScope.setEndIndex(words.length);
									negationScope.setCoveredText(sequence);
									scopeBoundaries.add(negationScope);
								}
							}
						}

					}

				}
			}
			return scopeBoundaries;
		}
		return null;
	}


	public List<NegPhrase> processNegations(String[] words, List<NegPhrase>negPhrases) {
		List<NegPhrase> result = new LinkedList<NegPhrase>();
		NegPhrase phrase = null;
		for(int sentenceIndex = 0; sentenceIndex < words.length; sentenceIndex++) {
			for(int negPhrasesIndex=0; negPhrasesIndex < negPhrases.size(); negPhrasesIndex++) {
				phrase = negPhrases.get(negPhrasesIndex);
				if(phrase != null) {
					String negationPhrase = phrase.getNegationPhrase();
					String tokens[] = negationPhrase.split("\\s+");
					HashMap<Integer, NegPhrase> commonbaseWords = new LinkedHashMap<Integer, NegPhrase>();
					commonbaseWords.put(negPhrasesIndex, phrase);
					String baseWord = "";
					int basePhraseIndex = negPhrasesIndex + 1;
					if(tokens != null) {
						baseWord = tokens[0];
						if( (baseWord == null) || (!words[sentenceIndex].equals(baseWord))){
							continue;
						}
						while(true) {
							if(basePhraseIndex < negPhrases.size()) {
								NegPhrase phrs = negPhrases.get(basePhraseIndex);
								if(phrs.getNegationType().equals(phrase.getNegationType())) {
									String [] wordTokens = phrs.getNegationPhrase().split("\\s+");
									if(wordTokens[0].trim().equals(baseWord)) {
										commonbaseWords.put(basePhraseIndex, phrs);
									} else {
										break;
									}
									basePhraseIndex++;
								} else {
									break;
								}
							} else {
								break;
							}
						}
						int longestMatchingSequenceKey = Integer.MIN_VALUE;
						int longestMatchingSequenceLength = Integer.MIN_VALUE;
						NegPhrase negation;
						String negationConceptPhrase = "";
						if(!commonbaseWords.isEmpty()){
							for(Integer key : commonbaseWords.keySet()) {
								String sequence = "";
								negation = commonbaseWords.get(key); 
								String [] tokenConcepts = negation.getNegationPhrase().split("\\s+");
								negationConceptPhrase = negation.getNegationPhrase().trim();
								if(tokenConcepts != null && tokenConcepts.length > 0) {
									for(int tokenIndex = 0; tokenIndex < tokenConcepts.length; tokenIndex++) {
										if((tokenIndex+sentenceIndex) < words.length) {
											sequence = sequence + words[tokenIndex+sentenceIndex]+ " ";
										} else {
											break;
										}
									}
									if(sequence.trim().equals(negationConceptPhrase)) {
										if(tokenConcepts.length > longestMatchingSequenceLength) {
											longestMatchingSequenceLength = tokenConcepts.length;
											longestMatchingSequenceKey = key;
										}
									} 
								}
							}
							if(longestMatchingSequenceKey != Integer.MIN_VALUE) {
								NegPhrase longestNegation = commonbaseWords.get(longestMatchingSequenceKey);
								longestNegation.setStartIndex(sentenceIndex);
								longestNegation.setEndIndex(sentenceIndex+longestMatchingSequenceLength);
								result.add(longestNegation);
								sentenceIndex = sentenceIndex + longestMatchingSequenceLength;
								sentenceIndex--; 
								break;
							} else {
								if(!commonbaseWords.isEmpty()) {
									negPhrasesIndex = negPhrasesIndex + commonbaseWords.size();
									negPhrasesIndex--; 
								}
							}
						}
					} 
				}
			}
		}
		return result;
	}


	private void initializeTerminology(String path) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new FileInputStream(path));
			System.out.println("PATH >> "+path);
			while(scanner.hasNextLine()) {
				String sentence = scanner.nextLine();
				String[] conceptSpace = sentence.split("\t");
				NegPhrase phrase = new NegPhrase();
				phrase.setNegationPhrase(conceptSpace[0]);
				phrase.setNegationType(conceptSpace[1]);
				negPhrases.add(phrase);
			}

		} catch(Exception e) {
			System.out.println("Exception occurred while parsing NegEx Dictionary!");

		} finally {
			if(scanner != null) {
				scanner.close();
			}
		}
	}

}
