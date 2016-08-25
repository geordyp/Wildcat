package edu.ksu.wildcat.handlers;
import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.FileStoreEditorInput;


/**
 * Handler for the 'Open File' menu action which opens a file dialog
 * for the user to select the file they want to open
 * 
 * @author geordypaul
 */
public class FileOpen extends AbstractHandler implements IHandler {
	
	/**
	 * Executes with the map of parameter values by name
	 * 
	 * @param event - contains all the info about the current state of the app
	 * @return - the result of the execution
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		// Get the file the user wants to open
		File file = openFile();
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
	
	/**
	 * Show OpenFile dialog for the user to select a file
	 */
	private static File openFile() {
		Shell s = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell();
		
		// Opens dialog to select file
		FileDialog dialog = new FileDialog(s, SWT.OPEN);
		dialog.setFilterExtensions(new String[]{"*.*"});
		dialog.setFilterNames(new String[]{"All files"});
		String fileSelected = dialog.open();
		
		if (fileSelected != null && fileSelected.length() > 0) {
			return new File(fileSelected);
		}
		return null;
	}
}
