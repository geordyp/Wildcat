package edu.ksu.wildcat.handlers.menu.file;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

/**
 * Handler for the "Save" menu action which saves the current file
 *
 * @author geordypaul
 */
public class Save extends AbstractHandler implements IHandler {

	private IWorkbenchPage page;

	/**
	 * Executes with the map of parameter values by name
	 *
	 * @param event - contains all the info about the current state of the app
	 * @return - the result of the execution
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart editorPart = page.getActiveEditor();
		page.saveEditor(editorPart, false);

		return null;
	}
}
