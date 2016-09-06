package edu.ksu.wildcat;

import org.eclipse.jface.text.rules.IWordDetector;

/**
 * Defines the interface by which WordRule determines whether a given
 * character is valid as part of a word in the current context.
 *
 * @author geordypaul
 */
public class WordDetector implements IWordDetector {

	/**
	 * Returns whether the specified character is valid
	 * as a subsequent character in a word
	 * 
	 * @param c - the character to be checked
	 * @return true if the character is a valid word part
	 */
	@Override
	public boolean isWordPart(char c) {

		// ASCII values for capital and lower case letters
		if ( (((int)c > 64) && ((int)c < 91)) || 
				(((int)c > 96) && ((int)c < 123)) ) return true;
		return false;
	}

	/**
	 * Returns whether the specified character is valid
	 * as the first character in a word
	 * 
	 * @param c - the character to be checked
	 * @return true if the character is a valid first character in a word
	 */
	@Override
	public boolean isWordStart(char c) {

		// ASCII values for capital and lower case letters
		if ( (((int)c > 64) && ((int)c < 91)) || 
				(((int)c > 96) && ((int)c < 123)) ) return true;
		return false;
	}

}
