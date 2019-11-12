package edu.smith.cs.csc212.speller;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.Random;

/**
 * Do your work for the Fake Dataset part here.
 * 
 * @author jfoley
 *
 */
public class FakeDatasetExperiment { 
	/**
	 * Maybe this will be a nice helper method. How do you "ruin" a correctly
	 * spelled word?
	 * 
	 * @param realWord - a word from the dictionary, perhaps chosen at random.
	 * @return an incorrectly-spelled word. Maybe you deleted a letter or added one?
	 */
	public static String makeFakeWord(String realWord) {
		throw new RuntimeException("TODO");
	}

	/**
	 * Create a list of words that contains some dictionary words in proportion to
	 * some non-dictionary words.
	 * 
	 * @param yesWords    - the words in the dictionary.
	 * @param numSamples  - the number of total words to select.
	 * @param fractionYes - the fraction of words that are in the dictionary -- with
	 *                    0.5, half would be spelled correctly and half would be
	 *                    incorrect.
	 * @return a new list where size is numSamples.
	 */
	public static List<String> createMixedDataset(List<String> yesWords, int numSamples, double fractionYes) {
		// Hint to the ArrayList that it will need to grow to numSamples size:
		List<String> output = new ArrayList<>(numSamples);
		//select numSamples * fractionYes words from yesWords; create the rest as
		// no words.
		
		//construct a dataset that has Strings that are both in and out of the dictionary 
		
		Random rand = new Random();
		int words = (int) (numSamples * fractionYes); //number of words we want
		for (int i = 0; i<words; i++) {
			int index = rand.nextInt(yesWords.size());
			output.add(yesWords.get(index));
		}

		//get a non-word
//		double fractionNo = 1 - fractionYes;
//		int nowords = (int) (numSamples * fractionNo);
//		for (int i = 0; i <nowords; i++) {
//			int index_no = rand.nextInt(s.size());
//			output.add(s.get(index));
//		}
//
//		
		// PSEUDO CODE
			//randomly pick a word from yesWords
			//put back into output
			
			//for the rest: add to output --> add not real words
			//print yesWords

			
		
		//for full credit: devise a method to inject some percentage of hits and misses. 
		//percentage: 
			//hits/total --> 
			//misses/total --> 
			//total = yesWords?
		
		return output;
	}
	
	/**
	 * Random Word Generator: randomly combine characters together
	 * This code was in collaboration with Chris, one of the CSC 212 TA's
	 */
	public static String generateRandomWord() {
		Random rand = new Random();
		int randlength = rand.nextInt(10);
		randlength++;
		String s = "";
		for (int i = 0; i < randlength; i++) {
			//pick a random character
			int randChar = rand.nextInt(26);
			randChar = randChar + 97;
			char newChar = (char) randChar;
			//append newChar to string
			s = s + newChar;
			System.out.println("new no words: " + s);
		}
		return s;
	}


	/**
	 * This is **an** entry point of this assignment.
	 * 
	 * @param args - unused command-line arguments.
	 */
	public static void main(String[] args) {
		// --- Load the dictionary.
		List<String> listOfWords = CheckSpelling.loadDictionary();

		// --- Create a bunch of data structures for testing:
		TreeSet<String> treeOfWords = new TreeSet<>(listOfWords);
		HashSet<String> hashOfWords = new HashSet<>(listOfWords);
		SortedStringListSet bsl = new SortedStringListSet(listOfWords);
		CharTrie trie = new CharTrie();
		for (String w : listOfWords) {
			trie.insert(w);
		}
		LLHash hm100k = new LLHash(100000);
		for (String w : listOfWords) {
			hm100k.add(w);
		}
		
		// --- OK, so that was a biased experiment (the answer to every question was yes!).
		// Let's try 10% yesses. 
		for (int i = 0; i < 10; i++) {
			// --- Create a dataset of mixed hits and misses with p=i/10.0
			List<String> hitsAndMisses = createMixedDataset(listOfWords, 10_000, i / 10.0);

			System.out.println("i="+i);
			// --- Time the data structures.
			// timeLookup calculates per-item query time 
			CheckSpelling.timeLookup(hitsAndMisses, treeOfWords);
			CheckSpelling.timeLookup(hitsAndMisses, hashOfWords);
			CheckSpelling.timeLookup(hitsAndMisses, bsl);
			CheckSpelling.timeLookup(hitsAndMisses, trie);
			CheckSpelling.timeLookup(hitsAndMisses, hm100k);
		}
	}
}
