package edu.ksu.wildcat;

import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

/**
 * Public class for configuring the workbench
 * 
 * @author geordypaul
 */
public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

	private static final String PERSPECTIVE_ID = "edu.ksu.wildcat.perspective";

	/**
	 * Creates a new workbench window advisor for configuring a new workbench window
	 * via the given workbench window configurer
	 * 
	 * @param configurer - the workbench window configurer
	 * @return a new workbench window advisor
	 */
    public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        return new ApplicationWorkbenchWindowAdvisor(configurer);
    }

    /**
     * Returns the id of the perspective to use for the initial workbench window
     * 
     * @return the id of the perspective for the initial window
     */
	public String getInitialWindowPerspectiveId() {
		return PERSPECTIVE_ID;
	}
}
