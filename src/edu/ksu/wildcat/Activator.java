package edu.ksu.wildcat;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import edu.ksu.wildcat.ide.ui.WildcatPartitionScanner;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "edu.ksu.wildcat"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	public final static String WILDCAT_PARTITIONING = "___Wildcat__partitioning_____";
	
	private static WildcatPartitionScanner fPartitionScanner;
	
	private static MyCodeScanner fCodeScanner;
	
	private static MyColorProvider fColorProvider;
	
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
	
	public static WildcatPartitionScanner getWildcatPartitionScanner() {
		if (fPartitionScanner == null)
			fPartitionScanner = new WildcatPartitionScanner();
		return fPartitionScanner;
	}
	
	public static MyCodeScanner getMyCodeScanner() {
		if (fCodeScanner == null) {
			if (fColorProvider == null) {
				fColorProvider = new MyColorProvider();
				fCodeScanner = new MyCodeScanner(fColorProvider);
			}
			else
				fCodeScanner = new MyCodeScanner(fColorProvider);
		}
		return fCodeScanner;
	}
	
	public static MyColorProvider getMyColorProvider() {
		if (fColorProvider == null)
			fColorProvider = new MyColorProvider();
		return fColorProvider;
	}
	
	
}
