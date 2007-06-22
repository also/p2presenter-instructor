package org.p2presenter.instructor.ui.builder;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;

public class ShowPseudoDebuggerActionDelegate implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	public void run(IAction action) {
		IWorkbenchPage page = window.getActivePage();
		PseudoEditorInput input = new PseudoEditorInput();
		try {
			page.openEditor(input, PseudoDebuggerEditor.ID);
		}
		catch (PartInitException ex) {
			// TODO
			ex.printStackTrace();
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
