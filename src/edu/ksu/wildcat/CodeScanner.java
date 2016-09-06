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
	
	private static String[] _mainKeywords = {"environment", "method", "model", "variables", "interface", "responses"};
	private static String[] _regularKeywords = {"tabular_graphics_data", "vector_parameter_study", "final_point", "num_steps", "single", "continuous_design", "initial_point", "descriptors", "system", "analysis_driver", "parameters_file", "results_file", "file_save", "num_objective_functions", "no_gradients", "no_hessians"};
	
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

}
