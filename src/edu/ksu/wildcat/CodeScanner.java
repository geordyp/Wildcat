package edu.ksu.wildcat;

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

/**
 * The scanner is used to get the next token by
 * evaluating its rule in sequence until one is successful
 * 
 * @author geordypaul
 */
public class CodeScanner extends RuleBasedScanner {
	
	private static String[] _mainKeywords = {"environment"};
	
	/**
	 * Constructor
	 * 
	 * @param provider - the color provider
	 */
	public CodeScanner(ColorProvider provider) {
		IToken keyword = new Token(
				new TextAttribute(provider.getColor(ColorProvider.MAIN_KEYWORDS)));
		IToken regularKeyword = new Token(
				new TextAttribute(provider.getColor(ColorProvider.REGULAR_KEYWORDS)));
		IToken comment = new Token(
				new TextAttribute(provider.getColor(ColorProvider.COMMENTS)));
		IToken string = new Token(
				new TextAttribute(provider.getColor(ColorProvider.STRING_VALUES)));
		IToken other = new Token(
				new TextAttribute(provider.getColor(ColorProvider.DEFAULT)));
		
		//ArrayList<IRule> rules = new ArrayList();
		ArrayList<IRule> rules = new ArrayList<IRule>();
		
		// Add rule for single line comments
		rules.add(new EndOfLineRule("#", comment));
		
		// Add rule for strings
		rules.add(new SingleLineRule("\"", "\"", string, '\\'));
		rules.add(new SingleLineRule("'", "'", string, '\\'));
		
		// Add generic whitespace rule
		rules.add(new WhitespaceRule(new WhitespaceDetector()));

		// Add word rule for keywords
		WordRule wordRule = new WordRule(new WordDetector(), other);
		for (int i = 0; i< _mainKeywords.length; i++)
			wordRule.addWord(_mainKeywords[i], keyword);
		rules.add(wordRule);
		
		IRule[] result = new IRule[rules.size()];
		rules.toArray(result);
		setRules(result);
	}

}
