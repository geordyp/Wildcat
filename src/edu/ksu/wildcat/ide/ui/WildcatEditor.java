package edu.ksu.wildcat.ide.ui;

import org.eclipse.ui.editors.text.TextEditor;

/**
 * Our custom Text Editor used for *.in files
 * which includes syntax highlighting
 * 
 * @author geordypaul
 */
public class WildcatEditor extends TextEditor {
	
	/**
	 * Initializes this editor
	 */
	protected void initializeEditor() {
		super.initializeEditor();
		setSourceViewerConfiguration(new WildcatSourceViewerConfiguration());
	}
}
