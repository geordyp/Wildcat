package edu.ksu.wildcat;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class KeywordNode {
	private String word;
	private ArrayList<String> aliases;
	
	private List<KeywordNode> children;
	
	public KeywordNode(String w, ArrayList<String> a) {
		word = w;
		aliases = a;
		children = new ArrayList<KeywordNode>();
	}
	
	public void insert(String word, ArrayList<String> aliases) {
		children.add(new KeywordNode(word, aliases));
	}
	
	public KeywordNode find(String word) {
		Iterator<KeywordNode> childNodes = children.iterator();
		while (childNodes.hasNext()) {
			KeywordNode curr = childNodes.next();
			if (curr.word.equals(word))
				return curr;
		}	
		return null;
	}
	
	public String getWord() {
		return word;
	}
	
	public ArrayList<String> getAliases() {
		return aliases;
	}

	public Iterator<KeywordNode> getChildren() {
		return children.iterator();
	}
}
