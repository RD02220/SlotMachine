package com.slotmachine.backend;

import java.util.ArrayList;
import java.util.Random;

/**
 * Contains values, references, and methods for slot generation.
 * 
 * @author Chad Golden
 */
public final class ReelGenerator {
	
	private static String upperAlpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	@SuppressWarnings("unused")
	// TODO
	private static String lowerAlpha = "abcdefghijklmnopqrstuvwxyz";

	/**
	 * No instantiation for this class.
	 */
	private ReelGenerator() {
	}
	
	/**
	 * 
	 * @param alphabet
	 * @return
	 */
	public static Reel generateRandomReel(String alphabet) {
		// Store passed alphabet in a list structure.
		ArrayList<Character> letterList = new ArrayList<Character>();
		for (int i = 0; i < alphabet.length(); i++) {
			letterList.add(alphabet.charAt(i));
		}
		
		// String to build.
		StringBuilder symbolString = new StringBuilder();
		
		// Generate random index of list element.
		Random rng = new Random();
		
		// Append random character removed from list to StringBuilder.
		while (letterList.size() > 0) {
			symbolString.append(letterList.remove(rng.nextInt(letterList.size())));
		}
		
		return new Reel(symbolString.toString());
	}
	
	/**
	 * 
	 * @param sizeOfAlphabet
	 * @return
	 */
	public static Reel generateRandomReel(int sizeOfAlphabet) {
		// Store sequential alphabet of specified number of characters in list.
		ArrayList<Character> letterList = new ArrayList<Character>();
		for (int i = 0; i < sizeOfAlphabet; i++) {
			letterList.add(upperAlpha.charAt(i));
		}
		
		// String to build.
		StringBuilder symbolString = new StringBuilder();
		
		// Generate random index of list element.
		Random rng = new Random();
		
		while (letterList.size() > 0) {
			symbolString.append(letterList.remove(rng.nextInt(letterList.size())));
		}
		
		return new Reel(symbolString.toString());
	}
	
	
	
}
