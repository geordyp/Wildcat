package edu.ksu.wildcat.ide.ui;

import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;

import java.util.ArrayList;

import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;

public class WildcatPartitionScanner extends RuleBasedPartitionScanner {
	// string constants for different partition types
	public final static String SINGLELINE_COMMENT = "singleline_comment";
	public final static String STRING = "string";
	public final static String[] PARTITION_TYPES = new String[] {SINGLELINE_COMMENT, STRING};
	
	public WildcatPartitionScanner() {
		super();
		
		IToken singlelinecomment = new Token(SINGLELINE_COMMENT);
		IToken string = new Token(STRING);
		
		ArrayList<IRule> rules = new ArrayList<IRule>();
		
		rules.add(new EndOfLineRule("#", singlelinecomment));
		
		rules.add(new SingleLineRule("\"", "\"", string, '\\'));
		rules.add(new SingleLineRule("'", "'", string, '\\'));
		
		IPredicateRule[] result = new IPredicateRule[rules.size()];
		rules.toArray(result);
		setPredicateRules(result);
	}
}
