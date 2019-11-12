package edu.smith.cs.csc212.speller;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * This class contains experimentation code.
 * @author jfoley
 *
 */
public class CheckSpelling {
	/**
	 * Read all lines from the UNIX dictionary.
	 * @return a list of words!
	 */
	public static List<String> loadDictionary() {
		long start = System.nanoTime();
		// Read all lines from a file:
		// This file has one word per line.
		List<String> words = WordSplitter.readUTF8File("src/main/resources/words")
				.lines()
				.collect(Collectors.toList());
		long end = System.nanoTime();
		double time = (end - start) / 1e9;
		System.out.println("Loaded " + words.size() + " entries in " + time +" seconds.");
		return words;
	}
	
	/**
	 * This method looks for all the words in a dictionary.
	 * @param words - the "queries"
	 * @param dictionary - the data structure.
	 */
	public static void timeLookup(List<String> words, Collection<String> dictionary) {
		long startLookup = System.nanoTime();
		
		int found = 0;
		for (String w : words) {
			if (dictionary.contains(w)) {
				found++;
			}
		}
		
		long endLookup = System.nanoTime();
		double fractionFound = found / (double) words.size();
		double timeSpentPerItem = (endLookup - startLookup) / ((double) words.size());
		int nsPerItem = (int) timeSpentPerItem;
		System.out.println("  "+dictionary.getClass().getSimpleName()+": Lookup of items found="+fractionFound+" time="+nsPerItem+" ns/item");
	}
	
	/**
	 * This is **an** entry point of this assignment.
	 * @param args - unused command-line arguments.
	 */
	public static void main(String[] args) {
		// --- Load the dictionary.
		List<String> listOfWords = loadDictionary();
		
		// --- Create a bunch of data structures for testing:
		
		// TREE
		long startTree = System.nanoTime();
		TreeSet<String> treeOfWords = new TreeSet<>(listOfWords);
		long endTree = System.nanoTime();
		System.out.println("Tree takes " + (endTree-startTree)/1e9 + " s.");
		
		//looping
		TreeSet<String> tree = new TreeSet<>();
		long startTree2 = System.nanoTime();
		for (String t : listOfWords) {
			tree.add(t);
		} 
		long endTree2 = System.nanoTime();
		System.out.println("Tree (with loop) takes " + (endTree2-startTree2)/1e9 + " s.");
		
		// HASH SET 
		long startHash = System.nanoTime();
		HashSet<String> hashOfWords = new HashSet<>(listOfWords);
		long endHash = System.nanoTime();
		System.out.println("Hash Set takes " + (endHash-startHash)/1e9 + " s.");
		
		//looping
		HashSet<String> hash = new HashSet<>();
		long startHash2 = System.nanoTime();
		for (String h : listOfWords) {
			hash.add(h); 
		} 
		long endHash2 = System.nanoTime();
		System.out.println("Hash (with loop) takes " + (endHash2-startHash2)/1e9 + " s.");
		
		// SORTED STRING
		long startSort = System.nanoTime();
		SortedStringListSet bsl = new SortedStringListSet(listOfWords);
		long endSort = System.nanoTime();
		System.out.println("Sorted String List Set takes " + (endSort-startSort)/1e9 + " s.");
		
		// CHAR TRIE
		CharTrie trie = new CharTrie();
		long startChar = System.nanoTime();
		for (String w : listOfWords) {
			trie.insert(w);
		} 
		long endChar = System.nanoTime();
		System.out.println("Char Trie takes " + (endChar-startChar)/1e9 + " s.");
		
		// LLHASH
		LLHash hm100k = new LLHash(100000);
		long startLL = System.nanoTime();
		for (String w : listOfWords) {
			hm100k.add(w);
		}
		long endLL = System.nanoTime();
		System.out.println("LLHash takes " + (endLL-startLL)/1e9 + " s.");
		
		
		/**
		 * Timing
		 */
		// HashSet
//		long startLL = System.nanoTime();
//		//doSomething();
//		long endLL = System.nanoTime();
//		//System.out.println("LLHash takes " + (endLL-startLL) + " ns.");
		
		
		// --- Make sure that every word in the dictionary is in the dictionary:
		//     This feels rather silly, but we're outputting timing information!
		timeLookup(listOfWords, treeOfWords);
		timeLookup(listOfWords, hashOfWords);
		timeLookup(listOfWords, bsl);
		timeLookup(listOfWords, trie);
		timeLookup(listOfWords, hm100k);
		
		
		
	
		// --- print statistics about the data structures:
		System.out.println("Count-Nodes: "+trie.countNodes());
		System.out.println("Count-Items: "+hm100k.size());

		System.out.println("Count-Collisions[100k]: "+hm100k.countCollisions());
		System.out.println("Count-Used-Buckets[100k]: "+hm100k.countUsedBuckets());
		System.out.println("Load-Factor[100k]: "+hm100k.countUsedBuckets() / 100000.0);

		
		System.out.println("log_2 of listOfWords.size(): "+listOfWords.size());
		
		System.out.println("Done!");
	}
}
