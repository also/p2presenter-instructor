/* $Id$ */

package org.p2presenter.instructor.ui.builder;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.p2presenter.instructor.ui.Activator;
import org.p2presenter.instructor.ui.builder.TextRangeNode.TextRangeOverlapException;

public class CreateSnippetFromSelectionAction implements IWorkbenchWindowActionDelegate {
	public static final String ID = "org.p2presenter.instructor.ui.builder.createSnippetFromSelectionAction";
	
	private IWorkbenchWindow window;

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	public void run(IAction action) {
		try {
			Activator.getDefault().getPseudoEditor().defineTextRange();
			
		}
		catch (TextRangeOverlapException ex) {
			// TODO improve the error message
			MessageDialog.openError(window.getShell(), "Can't Create Snippet", "The selected text overlaps an existing selection");
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		
	}
}
