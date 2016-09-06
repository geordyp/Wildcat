package edu.ksu.wildcat;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

//import edu.ksu.wildcat.ide.ui.WildcatPartitionScanner;

/**
 * The activator class controls the plug-in life cycle
 *
 * @author geordypaul
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "edu.ksu.wildcat"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;	
	
	private static CodeScanner _codeScanner;
	
	private static ColorProvider _colorProvider;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	/**
	 * Get this plug-in's code scanner
	 * 
	 * @return CodeScanner - RuleBasedScanner
	 */
	public static CodeScanner getMyCodeScanner() {
		if (_codeScanner == null) {
			if (_colorProvider == null)
				_colorProvider = new ColorProvider();
			
			_codeScanner = new CodeScanner(_colorProvider);
		}
		return _codeScanner;
	}
	
	/**
	 * Get this plug-in's color provider
	 * 
	 * @return ColorProvider - holds the colors for syntax highlighting
	 */
	public static ColorProvider getColorProvider() {
		if (_colorProvider == null)
			_colorProvider = new ColorProvider();
		return _colorProvider;
	}
}
