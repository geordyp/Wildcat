package edu.ksu.wildcat.handlers;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.ide.FileStoreEditorInput;

/**
 * Handler for the run menu action which takes the given
 * input file and runs it through Dakota
 * 
 * @author geordypaul
 */
public class Run extends AbstractHandler implements IHandler {
	
	/**
	 * Executes with the map of parameter values by name
	 * 
	 * @param event - contains all the info about the current state of the app
	 * @return - the result of the execution
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		// Get the path of the current file
		IEditorInput editorInput = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getActiveEditor().getEditorInput();
		FileStoreEditorInput fileStore = (FileStoreEditorInput) editorInput;
		URI path = fileStore.getURI();
		String inputFilePath = path.toString();
		
		// Remove 'file:/' from the path
		inputFilePath = inputFilePath.substring(6);
		
		try {
            Process p = null;
            
            // Set the commands for the process
            ProcessBuilder pb = new ProcessBuilder("dakota", inputFilePath);
            
            // Set the directory for the process
            // TODO Get the directory path from the user
            pb.directory(new File("C:/Users/geordypaul/Documents/Research/wildcat/edu.ksu.wildcat/process_dir"));
            p = pb.start();
            
            // The output from the console
            BufferedReader outputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));
            
            // The error from the console
            BufferedReader errorStream = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            	            	            
            // Write the output stream to an output file
            // TODO write to a better directory
            PrintWriter pwOutput = new PrintWriter("output.txt");
            toPrintWriter(pwOutput, outputStream);
            
            // Write the error stream to an error file
            // TODO write to a better directory
            PrintWriter pwError = new PrintWriter("error.txt");
            toPrintWriter(pwError, errorStream);

            p.waitFor();
			
	        // Get the output file created by the PrintWriter
			File outputFile = new File("output.txt");
			if (outputFile != null) {
				
				// Get the workbench window
				IWorkbenchPage page = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage();
				try {
					// Display the output file
					// TODO display it as read-only
					IEditorInput eiOutput = new FileStoreEditorInput(EFS.getStore(outputFile.toURI()));
					page.openEditor(eiOutput, EditorsUI.DEFAULT_TEXT_EDITOR_ID);
				} catch (CoreException e){
					e.printStackTrace();
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Takes the given BufferedReader and writes its contents
	 * to the given PrintWriter
	 * 
	 * @param pw
	 * @param stream
	 */
	private static void toPrintWriter(PrintWriter pw, BufferedReader stream) {
        try {
    		String line;
            while ((line = stream.readLine()) != null) {
            	pw.println(line);
            }
            stream.close();
            pw.close();	
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
	}
}
