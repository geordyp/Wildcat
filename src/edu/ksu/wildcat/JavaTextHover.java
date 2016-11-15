package edu.ksu.wildcat;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.swt.graphics.Point;

public class JavaTextHover implements ITextHover {

	@Override
	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		if (hoverRegion != null) {
			try {
				if (hoverRegion.getLength() > -1) {
					String selection = textViewer.getDocument().get(hoverRegion.getOffset(), hoverRegion.getLength());
					//System.out.println(textViewer.getDocument().get());
					//System.out.println(hoverRegion.getOffset());
					//System.out.println(hoverRegion.getLength());
					return selection;
				}
			} catch (BadLocationException x) {
			}
		}
		return "";
	}
	
	@Override
	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
		Point selection= textViewer.getSelectedRange();
		if (selection.x <= offset && offset < selection.x + selection.y)
			return new Region(selection.x, selection.y);
		return new Region(offset, 0);
	}
}