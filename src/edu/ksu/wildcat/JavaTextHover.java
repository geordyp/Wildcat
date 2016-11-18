package edu.ksu.wildcat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.swt.graphics.Point;

public class JavaTextHover implements ITextHover {
	private KeywordNode _keywordTree;
	
	public JavaTextHover(KeywordNode kt, ArrayList<String> mk) {
		super();
		_keywordTree = kt;
	}

	@Override
	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		if (hoverRegion != null) {
			try {
				// grab all the text up to the user's selection (includes the selection)
				String allText = textViewer.getDocument().get();
				int end = hoverRegion.getOffset();
				while (allText.charAt(end) == ' ') {
					end++;
				}
				while (allText.charAt(end) != ',' && allText.charAt(end) != ' ' && allText.charAt(end) != '\n') {
					end++;
				}
				String text = textViewer.getDocument().get(0, end);
				String[] lines = text.split("\n");
				Stack<KeywordNode> visitedNodes = new Stack<KeywordNode>();
				Stack<String> keywordPath = new Stack<String>();
				for (int i = (lines.length - 1); i >= 0; i--) {
					// disregard comments
					if (lines[i].charAt(0) != '#') {	
						String[] words = lines[i].split(",|\\s+");
						for (int j = (words.length - 1); j >= 0; j--) {
							// disregard empty strings, numbers, and quoted strings
							if (!words[j].equals("") && !isNumeric(words[j]) && !isQuotedString(words[j])) {
								keywordPath.push(words[j]);
								KeywordNode mainKeyword = _keywordTree.findChild(words[j]);
								if (mainKeyword != null) {
									visitedNodes.push(mainKeyword);
									break;
								}
							}
						}
					}
					
					if (!visitedNodes.isEmpty()) {
						// pop the main keyword off the stack
						keywordPath.pop();
						while (!keywordPath.isEmpty()) {
							String currKeyword = keywordPath.pop();
							KeywordNode currNode = visitedNodes.peek();
							KeywordNode child = currNode.findChild(currKeyword);
							if (child != null) {
								visitedNodes.push(child);
							}
							else {
								while (child == null) {
									visitedNodes.pop();
									currNode = visitedNodes.peek();
									child = currNode.findChild(currKeyword);
								}
								visitedNodes.push(child);
							}
						}
						// the selection node will be at the top of the stack
						KeywordNode selectedNode = visitedNodes.pop();
						return selectedNode.getWord();
					}
				}
			} catch (BadLocationException x) {
			}
		}
		return "empty";
	}
	
	@Override
	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
		Point selection = textViewer.getSelectedRange();
		if (selection.x <= offset && offset < selection.x + selection.y)
			return new Region(selection.x, selection.y);
		return new Region(offset, 0);
	}
	
	private boolean isNumeric(String str)  {  
		try  {
			double d = Double.parseDouble(str);
		}
		catch (NumberFormatException nfe) {
			return false;
		}  
		
		return true;  
	}
	
	private boolean isQuotedString(String str) {
		if (str.charAt(0) == '\'' || str.charAt(0) == '\"') {
			return true;
		}
		else {
			return false;
		}
	}
}