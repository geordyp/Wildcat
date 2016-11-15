package edu.ksu.wildcat;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

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
	
	private static JavaTextHover _javaTextHover;
	
	public static KeywordNode _keywordTree;
	
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
	 * Get the list of keywords
	 * 
	 * @return _keywordTree - the tree of keywords
	 */
	private static KeywordNode getKeywordTree() {
		if (_keywordTree == null) {
			try {
				Scanner s = new Scanner(new File("C:/Users/geordypaul/Documents/Research/Wildcat/edu.ksu.wildcat/utility/dakota.input.dictionary"));
				
				_keywordTree = new KeywordNode("root", null);
				KeywordNode currNode;
				KeywordNode parentNode;
				
				while (s.hasNext()) {
					// example: KWD environment/tabular_data ALIAS tabular_graphics_data
					
					// remove KWD
					String line = s.nextLine().substring(4);
					// example: environment/tabular_data ALIAS tabular_graphics_data
					
					// grab individual keywords
					String[] keywords = line.split("/");
					// example: [environment, tabular_data ALIAS tabular_graphics_data]
			        
			        parentNode = _keywordTree;
			        String keyword;
			        ArrayList<String> aliases;
			        for (int i = 0; i < keywords.length; i++) {
			        	keyword = keywords[i];
			        	aliases = null;
			        	
			        	// example: tabular_data ALIAS tabular_graphics_data
			        	if (keyword.contains("ALIAS")) {
			        		aliases = new ArrayList<String>();
			        		String[] words = keywords[i].split(" ALIAS ");
			        		
			        		// start at 1 because the actual keyword is at 0
			        		for (int j = 1; j < words.length; j++)
			        			aliases.add(words[j]);

			        		keyword = words[0];
			        	}
			        	currNode = parentNode.find(keyword);
			            if (currNode == null)
			            	parentNode.insert(keyword, aliases);
			            else
			            	parentNode = currNode;
			        }
				}
				
				s.close();
			}
			catch (Exception e) {
				//TODO
			}
		}	
			
		return _keywordTree;
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
			
			_codeScanner = new CodeScanner(_colorProvider, getKeywordTree());
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
	
	/**
	 * Get this plug-in's text hover
	 * 
	 * @return ITextHover
	 */
	public static ITextHover getMyTextHover() {
		if (_javaTextHover == null)
			_javaTextHover = new JavaTextHover();
		return _javaTextHover;
	}
}
