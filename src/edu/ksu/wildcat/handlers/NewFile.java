package edu.ksu.wildcat.handlers;

import java.io.File;
import java.io.PrintWriter;

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
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.ide.FileStoreEditorInput;

/**
 * Handler for the "New" menu action which creates a new file
 *
 * @author geordypaul
 */
public class NewFile extends AbstractHandler implements IHandler {

	private IWorkbenchPage page;

	/**
	 * Executes with the map of parameter values by name
	 *
	 * @param event
	 *            - contains all the info about the current state of the app
	 * @return - the result of the execution
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

		File file = createFile();
		if (file != null) {
			String[] filePath = file.toString().split("\\\\");
			String fileName = filePath[filePath.length - 1];
			String[] fileNameParts = fileName.split("\\.");
			String extension = fileNameParts[fileNameParts.length - 1];

			try {
				// Open the file in the window
				IEditorInput eiFile = new FileStoreEditorInput(EFS.getStore(file.toURI()));
				if (extension.equals("in")) {
					// Use rich editor for *.in files
					page.openEditor(eiFile, "edu.ksu.wildcat.ide.ui.WildcatEditor");
				} else {
					page.openEditor(eiFile, EditorsUI.DEFAULT_TEXT_EDITOR_ID);
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * Show SaveFile dialog for the user to create a file
	 *
	 * @return - the created file
	 */
	private File createFile() {
		Shell s = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();

		// Create save dialog to select file
		FileDialog dialog = new FileDialog(s, SWT.SAVE);

		dialog.setFileName("untitled");
		dialog.setText("New File");
		dialog.setFilterNames(new String[] { "Dakota Input File (*.in)" });
		dialog.setFilterExtensions(new String[] { "." });
		String fileSelected = dialog.open();

		if (fileSelected != null && fileSelected.length() > 0) {
			try {
				PrintWriter newFile = new PrintWriter(fileSelected);
				newFile.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return new File(fileSelected);
		}
		return null;
	}
}
