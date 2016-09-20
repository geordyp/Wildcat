package edu.ksu.wildcat;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

/**
 * Public base class for configuring the action bars of a workbench window
 * 
 * @author geordypaul
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	/**
	 * Constructor
	 * 
	 * @param configurer - the action bar configurer
	 */
    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }

    /**
     * Instantiates the actions used in the fill methods
     * 
     * @param window - the window containing the action bars
     */
    protected void makeActions(IWorkbenchWindow window) {
    }

    /**
     * Fills the menu bar with the main menus for the window
     * 
     * @param menuBar - the menu manager for the menu bar
     */
    protected void fillMenuBar(IMenuManager menuBar) {
    }
    
}
