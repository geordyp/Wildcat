package edu.ksu.wildcat.ide.ui;

import org.eclipse.swt.widgets.Composite;

import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.ui.editors.text.TextEditor;

public class WildcatEditor extends TextEditor {
	
	public WildcatEditor() {
		super();
		setDocumentProvider(new WildcatDocumentProvider());
	}
	
	protected void initializeEditor() {
		super.initializeEditor();
		setSourceViewerConfiguration(new WildcatSourceViewerConfiguration());
	}
	
	protected ISourceViewer createSourceViewer(Composite parent, IVerticalRuler ruler, int styles) {
		ISourceViewer viewer = new WildcatSourceViewer(parent, ruler, getOverviewRuler(),
				isOverviewRulerVisible(), styles);
		
		// ensure decoration support has been created and configured
		getSourceViewerDecorationSupport(viewer);
		
		return viewer;
	}
}
