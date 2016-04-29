package edu.ksu.wildcat.handlers;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.util.Scanner;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.FileStoreEditorInput;

public class Run extends AbstractHandler implements IHandler {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		IEditorInput input = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getActiveEditor().getEditorInput();
		
		FileStoreEditorInput fileStore = (FileStoreEditorInput) input;
		URI thing = fileStore.getURI();
		String other = thing.toString();
		other = other.substring(6);
		
		try {
			File f = new File(other);
			
	        //try {
	            String line;
	            /*
	            Process p = null;
	            ProcessBuilder pb = new ProcessBuilder("java", "Addition");
	            pb.directory(new File("C:/Users/geordypaul/Documents/Research/wildcat/edu.ksu.wildcat/process_dir"));
	            p = pb.start();
	            */
	            Process p = null;
	            ProcessBuilder pb = new ProcessBuilder("dakota", other);
	            System.out.println("user.dir: " + System.getProperty("user.dir"));
	            System.out.println("old: " + pb.directory());
	            pb.directory(new File("C:/Users/geordypaul/Documents/Research/wildcat/edu.ksu.wildcat/process_dir"));
	            System.out.println("new: " + pb.directory());
	            p = pb.start();
	            //Process p = Runtime.getRuntime().exec("java Addition");
	            //Process p = Runtime.getRuntime().exec("dakota " + other);
	            //Process p = Runtime.getRuntime().exec("dakota dakota_input.in");
	            BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
	            BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
	            
	            StringBuilder consoleOutput = new StringBuilder();
	            
	            //PrintWriter pw = new PrintWriter("C:/Users/geordypaul/Desktop/output.txt");
	            PrintWriter pw = new PrintWriter("output.txt");
	            while ((line = bri.readLine()) != null) {
	                //System.out.println(line);
	            	consoleOutput.append(line + "\n");
	            	pw.println(line);
	            }
	            bri.close();
	            
	            pw.close();
	            PrintWriter pwError = new PrintWriter("error.txt");
	            while ((line = bre.readLine()) != null) {
	                pwError.println(line);
	            	//System.out.println(line);
	            }
	            pwError.close();
	            bre.close();
	            p.waitFor();
	            System.out.println("Process Finished");
	        //}
	        //catch (Exception e) {
	            //System.out.println(e.toString());
	            //e.printStackTrace();
	        //}
			
			
			
			//Scanner s = new Scanner(f);
			
			//StringBuilder sb = new StringBuilder();
			
			//while (s.hasNext()) {
				//sb.append(s.nextLine() + "\n");
			//}
			
	        /*
			IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
			MessageDialog.openInformation(
					window.getShell(), 
					"Hello World!",
					consoleOutput.toString());
			*/
	            
			File file = new File("output.txt");
			if( file!=null ){
				IWorkbenchPage page = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage();
				try{
					IFileStore otherFileStore = EFS.getStore( file.toURI() );
					IEditorInput otherInput = new FileStoreEditorInput(otherFileStore);
					page.openEditor(otherInput, EditorsUI.DEFAULT_TEXT_EDITOR_ID);
				} catch (CoreException e){
					e.printStackTrace();
				}
			}
			
			//s.close();
		}
		catch (Exception e) {
			
		}
		
		return null;
	}
}
