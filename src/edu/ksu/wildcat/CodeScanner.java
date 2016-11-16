package edu.ksu.wildcat;

import java.util.ArrayList;
import java.util.Iterator;

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
	
	private static ArrayList<String> _mainKeywords;
	private static ArrayList<String> _regularKeywords;
	
	/**
	 * Constructor
	 * 
	 * @param provider - the color provider
	 */
	public CodeScanner(ColorProvider provider, ArrayList<String> mk, ArrayList<String> rk) {
		
		IToken keyword = new Token(
				new TextAttribute(provider.getColor(ColorProvider.MAIN_KEYWORDS)));
		IToken regularKeyword = new Token(
				new TextAttribute(provider.getColor(ColorProvider.REGULAR_KEYWORDS)));
		IToken comment = new Token(
				new TextAttribute(provider.getColor(ColorProvider.COMMENTS)));
		IToken string = new Token(
				new TextAttribute(provider.getColor(ColorProvider.STRINGS)));
		IToken other = new Token(
				new TextAttribute(provider.getColor(ColorProvider.DEFAULT)));
		
		_mainKeywords = mk;
		_regularKeywords = rk;
		
		//ArrayList<IRule> rules = new ArrayList();
		ArrayList<IRule> rules = new ArrayList<IRule>();
		
		// Add rule for single line comments
		rules.add(new EndOfLineRule("#", comment));
		
		// Add rule for strings
		rules.add(new SingleLineRule("\"", "\"", string, '\\'));
		rules.add(new SingleLineRule("'", "'", string, '\\'));
		
		// Add generic whitespace rule
		rules.add(new WhitespaceRule(new WhitespaceDetector()));

		// Add word rule for main keywords
		WordRule wordRule = new WordRule(new WordDetector(), other);
		
		Iterator<String> mainKeywords = _mainKeywords.iterator();
		while (mainKeywords.hasNext())
			wordRule.addWord(mainKeywords.next(), keyword);
		
		Iterator<String> regularKeywords = _regularKeywords.iterator();
		while (regularKeywords.hasNext())
			wordRule.addWord(regularKeywords.next(), regularKeyword);
		
		rules.add(wordRule);
		
		IRule[] result = new IRule[rules.size()];
		rules.toArray(result);
		setRules(result);
	}
}
