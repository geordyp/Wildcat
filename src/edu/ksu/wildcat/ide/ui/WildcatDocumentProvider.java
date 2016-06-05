package edu.ksu.wildcat.ide.ui;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;

import edu.ksu.wildcat.Activator;

public class WildcatDocumentProvider extends FileDocumentProvider {
	
	@Override
	protected IDocument createDocument(Object element) throws CoreException {
		IDocument document = super.createDocument(element);
		
		if (document instanceof IDocumentExtension3) {
			IDocumentExtension3 extension3 = (IDocumentExtension3) document;
			IDocumentPartitioner partitioner = new FastPartitioner(Activator.getDefault().getWildcatPartitionScanner(),
					WildcatPartitionScanner.PARTITION_TYPES);
			extension3.setDocumentPartitioner(Activator.WILDCAT_PARTITIONING,  partitioner);
			partitioner.connect(document);
		}
		
		return document;
	}
	
	@Override
	protected IDocument createEmptyDocument() {
		return new WildcatDocument();
	}
}
