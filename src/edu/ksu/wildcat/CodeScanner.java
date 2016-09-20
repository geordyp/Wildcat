package edu.ksu.wildcat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

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
	
	private static String[] _mainKeywords;
	private static String[] _regularKeywords;
	private static Logger LOGGER;
	
	/**
	 * Constructor
	 * 
	 * @param provider - the color provider
	 */
	public CodeScanner(ColorProvider provider) {
		LOGGER = Logger.getLogger( CodeScanner.class.getName() );
		
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
		
		//ArrayList<IRule> rules = new ArrayList();
		ArrayList<IRule> rules = new ArrayList<IRule>();
		
		// Add rule for single line comments
		rules.add(new EndOfLineRule("#", comment));
		
		// Add rule for strings
		rules.add(new SingleLineRule("\"", "\"", string, '\\'));
		rules.add(new SingleLineRule("'", "'", string, '\\'));
		
		// Add generic whitespace rule
		rules.add(new WhitespaceRule(new WhitespaceDetector()));

		try {
			getKeywords();			
		}
		catch(Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		
		// Add word rule for main keywords
		WordRule wordRule = new WordRule(new WordDetector(), other);
		for (int i = 0; i < _mainKeywords.length; i++)
			wordRule.addWord(_mainKeywords[i], keyword);
		for (int i = 0; i < _regularKeywords.length; i++)
			wordRule.addWord(_regularKeywords[i], regularKeyword);
		rules.add(wordRule);
		
		IRule[] result = new IRule[rules.size()];
		rules.toArray(result);
		setRules(result);
	}
	
	private static void getKeywords() throws IOException {
		List<String> mainKeywords = new ArrayList<String>();
		List<String> regularKeywords = new ArrayList<String>();

		try {
			// TODO get this file path without hard coding
			Scanner s = new Scanner(new File("C:/Users/geordypaul/Documents/Research/Wildcat/edu.ksu.wildcat/utility/dakota.input.dictionary"));
			while (s.hasNext()) {
				// remove "KWD " from the line
				String line = s.nextLine().substring(4);
				String[] words = line.split("/");
				
				// main keyword
				if (words.length == 1) {
					mainKeywords.add(words[0]);
				}
				// regular keyword with aliases
				else if (words[words.length - 1].contains("ALIAS")) {
					String[] aliases = words[words.length - 1].split(" ALIAS ");
					for (int i = 0; i < aliases.length; i++) {
						regularKeywords.add(aliases[i]);
					}
				}
				// regular keyword without aliases
				else {
					regularKeywords.add(words[words.length - 1]);					
				}
			}
			
			_regularKeywords = regularKeywords.toArray(new String[1]);
			_mainKeywords = mainKeywords.toArray(new String[1]);
			s.close();
		}
		catch(Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}

}
