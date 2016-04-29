package edu.ksu.wildcat;

import java.awt.List;
import java.util.ArrayList;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;

public class MyCodeScanner extends RuleBasedScanner {
	
	private static String[] fgKeywords = {"environment", "method", "model", "variables", "interface", "responses"};
	
	public MyCodeScanner(MyColorProvider provider) {
		IToken keyword = new Token(
				new TextAttribute(provider.getColor(MyColorProvider.MAIN_KEYWORDS)));
		/*
		IToken regularKeyword = new Token(
				new TextAttribute(provider.getColor(MyColorProvider.REGULAR_KEYWORDS)));
		IToken comment = new Token(
				new TextAttribute(provider.getColor(MyColorProvider.COMMENTS)));
		IToken string = new Token(
				new TextAttribute(provider.getColor(MyColorProvider.STRING_VALUES)));
		*/
		IToken other = new Token(
				new TextAttribute(provider.getColor(MyColorProvider.DEFAULT)));
		
		//ArrayList<IRule> rules = new ArrayList();
		ArrayList rules = new ArrayList();
		
		// Add rule for single line comments
		//rules.add(new EndOfLineRule("//", comment));
		
		// Add rule for strings
		//rules.add(new SingleLineRule("\"", "\"", string, '\\'));
		//rules.add(new SingleLineRule("'", "'", string, '\\'));
		
		// Add generic whitespace rule
		rules.add(new WhitespaceRule(new MyWhitespaceDetector()));

		// Add word rule for keywords
		WordRule wordRule = new WordRule(new MyWordDetector(), other);
		for (int i = 0; i< fgKeywords.length; i++)
			wordRule.addWord(fgKeywords[i], keyword);
		rules.add(wordRule);
		
		IRule[] result = new IRule[rules.size()];
		rules.toArray(result);
		setRules(result);
	}

}
