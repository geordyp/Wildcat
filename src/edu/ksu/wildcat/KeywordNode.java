package edu.ksu.wildcat;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import java.lang.StringBuilder;

/**
 * The tree of keywords
 *
 * @author geordypaul
 */
public class KeywordNode {

	private String keyword;
	private String description;
	private String parameter;
	private ArrayList<String> aliases;
	private KeywordNode parent;
	private List<KeywordNode> children;

	/**
	 * The constructor
	 *
	 * @param keyword
	 * @param description
	 * @param parameter
	 * @param aliases
	 * @param parent
	 */
	public KeywordNode(String k, String d, String pm, ArrayList<String> a,
										 KeywordNode p) {

		keyword = k;
		description = d;
		parameter = pm;
		aliases = a;
		parent = p;
		children = new ArrayList<KeywordNode>();
	}


	/**
	 * Add a child node to this node
	 *
	 * @param keyword
	 * @param parameter
	 * @param description
	 * @param aliases
	 * @param parent
	 */
	public void insert(String keyword, String parameter, String description,
										 ArrayList<String> aliases, KeywordNode parent) {

		children.add(new KeywordNode(keyword, parameter, description,
																 aliases, parent));
	}

	/**
	 * Finds the child with the given keyword, or returns null
	 *
	 * @param keyword
	 * @return the node if found, otherwise null
	 */
	public KeywordNode findChild(String keyword) {
		Iterator<KeywordNode> childNodes = children.iterator();
		while (childNodes.hasNext()) {
			KeywordNode curr = childNodes.next();
			if (foundKeyword(curr, keyword))
				return curr;
		}
		return null;
	}

	/**
	 * Compares a keyword to the keyword and aliases of a given node
	 *
	 * @param node
	 * @param keyword
	 * @return the true if the keyword was found, otherwise false
	 */
	private boolean foundKeyword(KeywordNode node, String keyword) {
		// compare keywords
		if (node.keyword.equals(keyword)) {
			return true;
		}

		// compare aliases
		if (node.aliases != null) {
			Iterator<String> aliasList = node.aliases.iterator();
			while (aliasList.hasNext()) {
				if (aliasList.next().equals(keyword)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Compiles all of this nodes data into a string for JavaTextHover
	 *
	 * @return all the information about this node
	 */
	public String getHoverText() {
		// OUTPUT
		// parents with descriptions
		//
		// keyword with description
		// parameters
		//
		// children with descriptions

		StringBuilder hoverText = new StringBuilder();

		// get parents
		Stack<KeywordNode> parents = new Stack<KeywordNode>();
		KeywordNode parentNode = parent;
		while (!parentNode.keyword.equals("root")) {
			parents.push(parentNode);
			parentNode = parentNode.getParent();
		}

		// add parent info
		String tabs = "";
		while (!parents.isEmpty()) {
			KeywordNode node = parents.pop();
			hoverText.append(tabs + node.keyword + ": " + node.description + "\n");
			tabs += "\t";
		}

		if (!getParent().getKeyword().equals("root")) {
			hoverText.append("\n");
		}
		
		// add keyword info
		if (parameter != ""){
			hoverText.append(tabs + keyword + " (" + parameter + ")" + ": " + description + "\n");
		}
		else {
			hoverText.append(tabs + keyword + ": " + description + "\n");
		}

		// add children info
		hoverText.append("\n" + children.size() + " subelements:\n");
		Iterator<KeywordNode> childNodes = children.iterator();
		int count = 1;
		while (childNodes.hasNext()) {
			KeywordNode child = childNodes.next();
			hoverText.append("\t" + count + ") " + child.getKeyword() + ": " +
											 child.getDescription() + "\n");
			count++;
		}

		return hoverText.toString();
	}

	/**
	 * gets this node's keyword
	 *
	 * @return keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * gets this node's parameter
	 *
	 * @return parameter
	 */
	public String getParameter() {
		return parameter;
	}

	/**
	 * gets this node's description
	 *
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * gets this node's aliases
	 *
	 * @return aliases
	 */
	public ArrayList<String> getAliases() {
		return aliases;
	}

	/**
	 * gets this node's parent
	 *
	 * @return parent
	 */
	public KeywordNode getParent() {
		return parent;
	}

	/**
	 * gets this node's children
	 *
	 * @return children as an iterator object
	 */
	public Iterator<KeywordNode> getChildren() {
		return children.iterator();
	}
}
