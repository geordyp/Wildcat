package edu.ksu.wildcat.ide.ui;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

import edu.ksu.wildcat.Activator;
import edu.ksu.wildcat.MyColorProvider;

public class WildcatSourceViewerConfiguration extends SourceViewerConfiguration {
	
	@Override
	public String getConfiguredDocumentPartitioning(ISourceViewer sourceViewer) {
		return Activator.WILDCAT_PARTITIONING;
	}
	
	@Override
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] {IDocument.DEFAULT_CONTENT_TYPE,
				WildcatPartitionScanner.SINGLELINE_COMMENT,
				WildcatPartitionScanner.STRING};
	}
	
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {

		MyColorProvider provider = Activator.getMyColorProvider();
		PresentationReconciler reconciler = new PresentationReconciler();
		
		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(Activator.getMyCodeScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr,  IDocument.DEFAULT_CONTENT_TYPE);
		
		return reconciler;
	}
}
