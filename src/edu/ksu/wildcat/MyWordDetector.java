package edu.ksu.wildcat;

import org.eclipse.jface.text.rules.IWordDetector;

public class MyWordDetector implements IWordDetector {

	@Override
	public boolean isWordPart(char c) {

		// ASCII values for capital and lower case letters
		if (( ((int)c > 64) && ((int)c < 91) ) || 
				( ((int)c > 96) && ((int)c < 123) )) return true;
		return false;
	}

	@Override
	public boolean isWordStart(char c) {

		// ASCII values for capital and lower case letters
		if (( ((int)c > 64) && ((int)c < 91) ) || 
				( ((int)c > 96) && ((int)c < 123) )) return true;
		return false;
	}

}
