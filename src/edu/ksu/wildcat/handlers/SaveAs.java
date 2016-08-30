package edu.ksu.wildcat.handlers;
import java.io.File;
import java.io.PrintWriter;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;


/**
 * Handler for the "Save As..." menu action which opens a save dialog
 *
 * @author geordypaul
 */
public class SaveAs extends AbstractHandler implements IHandler {

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

		File file = saveFile();
		if (file != null) {
			String[] filePath = file.toString().split("\\\\");
			String fileName = filePath[filePath.length - 1];
			String[] fileNameParts = fileName.split("\\.");
			String extension = fileNameParts[fileNameParts.length - 1];

			try{
				// Open the file in the window
				IEditorInput eiFile = new FileStoreEditorInput(EFS.getStore(file.toURI()));
				if (extension.equals("in")) {
					// Use rich editor for *.in files
					page.openEditor(eiFile, "edu.ksu.wildcat.ide.ui.WildcatEditor");
				}
				else {
					page.openEditor(eiFile, EditorsUI.DEFAULT_TEXT_EDITOR_ID);
				}
			} catch (CoreException e){
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * Show SaveFile dialog for the user to save the file
	 *
	 * @return - the saved file
	 */
	private File saveFile() {
		Shell s = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell();

		// Create save dialog to select file
		FileDialog dialog = new FileDialog(s, SWT.SAVE);

		// Get the name of the current file
		IEditorInput editorInput = page.getActiveEditor().getEditorInput();
		FileStoreEditorInput fileStore = (FileStoreEditorInput) editorInput;
		String[] filePath = fileStore.getURI().toString().split("/");
		String fileName = filePath[filePath.length - 1];

		dialog.setFileName(fileName);
		dialog.setFilterNames(new String[] { "Dakota Input File (*.in)" });
		dialog.setFilterExtensions(new String[]{"*.*"});
		String fileSelected = dialog.open();

		if (fileSelected != null && fileSelected.length() > 0) {
			IEditorPart editorPart = page.getActiveEditor();
			
			// The current path's format needs to match the selected path's format for comparison
			String currentFile = fileStore.getURI().toString();
			currentFile = currentFile.substring(6);
			currentFile = currentFile.replace('/', '\\');

			// Just save the editor if a new file is not created
			if (fileSelected.equals(currentFile)) {
				page.saveEditor(editorPart, true);
				
				// The handler is finished if all we needed to do was save the current file
				return null;
			}
			else {
				try {
					// Copy the content in the editor into the file selected
				    if (!(editorPart instanceof ITextEditor)) return null;
				    ITextEditor textEditor = (ITextEditor) editorPart;
				    IDocument doc = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
					PrintWriter newFile = new PrintWriter(fileSelected);
					newFile.println(doc.get());
					newFile.close();
					
					return new File(fileSelected);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
