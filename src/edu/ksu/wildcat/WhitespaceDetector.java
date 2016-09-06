package edu.ksu.wildcat;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

/**
 * Defines the interface by which WhitespaceRule determines whether a given
 * character is to be considered whitespace in the current context.
 *
 * @author geordypaul
 */
public class WhitespaceDetector implements IWhitespaceDetector {

	/**
	 * Returns whether the specified character is whitespace
	 * 
	 * @param c - the character to be checked
	 * @return true if the character is a whitespace char
	 */
	@Override
	public boolean isWhitespace(char c) {
		if (c == ' ' || c == '\t' || c == '\r' || c == '\n') return true;
		else return false;
	}
}
