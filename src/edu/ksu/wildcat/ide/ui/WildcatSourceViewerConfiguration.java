package edu.ksu.wildcat.ide.ui;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

import edu.ksu.wildcat.Activator;

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
}
