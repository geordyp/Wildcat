package edu.ksu.wildcat;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

public class MyWhitespaceDetector implements IWhitespaceDetector {

	@Override
	public boolean isWhitespace(char c) {

		if (c == ' ') return true;
		else return false;
	}
}
