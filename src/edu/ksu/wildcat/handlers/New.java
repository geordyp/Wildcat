package edu.ksu.wildcat.handlers;
import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.FileStoreEditorInput;


/**
 * Handler for the "New" menu action which creates a new file
 * 
 * @author geordypaul
 */
public class New extends AbstractHandler implements IHandler {
	
	/**
	 * Executes with the map of parameter values by name
	 * 
	 * @param event - contains all the info about the current state of the app
	 * @return - the result of the execution
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		// Get the new file the user wants to create
		File file = new File("untitled");
		if (file != null) {
			// Get the workbench window
			IWorkbenchPage page = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();
			try{
				// Open the file in the window
				IEditorInput eiFile = new FileStoreEditorInput(EFS.getStore(file.toURI()));
				page.openEditor(eiFile, "edu.ksu.wildcat.ide.ui.WildcatEditor");
			} catch (CoreException e){
				e.printStackTrace();
			}
		}
		
		return null;
	}
}