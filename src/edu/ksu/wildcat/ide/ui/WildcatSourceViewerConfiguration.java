 package edu.ksu.wildcat.ide.ui;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

import edu.ksu.wildcat.Activator;

/**
 * This class bundles the configuration space of a source viewer
 * 
 * @author geordypaul
 */
public class WildcatSourceViewerConfiguration extends SourceViewerConfiguration {
	
	/**
	 * Returns the presentation reconciler ready to be
	 * used with the given source viewer
	 * 
	 * @param sourceViewer - the source viewer
	 * @return - the presentation reconciler
	 */
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();
		
		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(Activator.getMyCodeScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr,  IDocument.DEFAULT_CONTENT_TYPE);
		
		return reconciler;
	}
	
	public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType) {
		return Activator.getMyTextHover();
	}
}
