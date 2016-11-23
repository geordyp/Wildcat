package edu.ksu.wildcat;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class KeywordNode {
	private String word;
	private ArrayList<String> aliases;
	private KeywordNode parent;
	private List<KeywordNode> children;

	public KeywordNode(String w, ArrayList<String> a, KeywordNode p) {
		word = w;
		aliases = a;
		parent = p;
		children = new ArrayList<KeywordNode>();
	}

	public void insert(String word, ArrayList<String> aliases) {
		children.add(new KeywordNode(word, aliases));
	}

	public KeywordNode findChild(String word) {
		Iterator<KeywordNode> childNodes = children.iterator();
		while (childNodes.hasNext()) {
			KeywordNode curr = childNodes.next();
			if (foundKeyword(curr, word))
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

	public KeywordNode getParent() {
		return parent;
	}

	public Iterator<KeywordNode> getChildren() {
		return children.iterator();
	}

	private boolean foundKeyword(KeywordNode node, String word) {
		if (node.word.equals(word)) {
			return true;
		}

		if (node.aliases != null) {
			Iterator<String> aliasList = node.aliases.iterator();
			while (aliasList.hasNext()) {
				if (aliasList.next().equals(word))
					return true;
			}
		}

		return false;
	}
}
