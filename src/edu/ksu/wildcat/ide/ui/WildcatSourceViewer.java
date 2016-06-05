package edu.ksu.wildcat.ide.ui;

import org.eclipse.swt.widgets.Composite;

import org.eclipse.jface.text.source.IOverviewRuler;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.SourceViewer;

public class WildcatSourceViewer extends SourceViewer {

	public WildcatSourceViewer(Composite parent, IVerticalRuler ruler, IOverviewRuler overviewRuler,
			boolean overviewRulerVisible, int styles) {
		super(parent, ruler, overviewRuler, overviewRulerVisible, styles);
	}

}
