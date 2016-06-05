package edu.ksu.wildcat.handlers;
import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.ide.FileStoreEditorInput;

import edu.ksu.wildcat.ide.ui.WildcatEditor;

public class FileOpen extends AbstractHandler implements IHandler {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		
		File file = openFile();
		if( file!=null ){
			IWorkbenchPage page = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();
			try{
				IFileStore fileStore = EFS.getStore( file.toURI() );
				IEditorInput input = new FileStoreEditorInput(fileStore);
				//page.openEditor(input, EditorsUI.DEFAULT_TEXT_EDITOR_ID);
				page.openEditor(input, "edu.ksu.wildcat.ide.ui.WildcatEditor");
			} catch (CoreException e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/** Show OpenFile dialog to select a file */
	private File openFile(){
		Shell shell = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell();
		// opens dialog to select file
		FileDialog dialog = new FileDialog(shell, SWT.OPEN);
		dialog.setFilterExtensions(new String[]{"*.*"});
		dialog.setFilterNames(new String[]{"All files"});
		String fileSelected = dialog.open();
		if( fileSelected!=null && fileSelected.length() > 0 ){
			// Perform action, opens the file
			System.out.println("Selected file: " + fileSelected);
			return new File(fileSelected);
		}
		return null;
	}
}
