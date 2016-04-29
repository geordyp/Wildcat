package edu.ksu.wildcat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class MyColorProvider {
	
	public static final RGB MAIN_KEYWORDS = new RGB(171, 73, 204);		// purple
	public static final RGB REGULAR_KEYWORDS = new RGB(49, 39, 242);	// blue
	public static final RGB COMMENTS = new RGB(219, 230, 28);			// yellow
	public static final RGB STRING_VALUES = new RGB(55, 219, 60);		// green
	public static final RGB DEFAULT = new RGB(0, 0, 0);					// black
	
	protected Map fColorTable = new HashMap(10);
	
	public void dispose() {
		Iterator e = fColorTable.values().iterator();
		while (e.hasNext())
			((Color) e.next()).dispose();
	}
	
	public Color getColor(RGB rgb) {
		Color color = (Color) fColorTable.get(rgb);
		if (color == null) {
			color = new Color(Display.getCurrent(), rgb);
			fColorTable.put(rgb, color);
		}
		return color;
	}

}
