/* $Id$ */

package org.p2presenter.instructor.ui.builder;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

public class EditPseudocodeAction extends Action implements IWorkbenchAction {
	public static final String ID = "org.p2presenter.instructor.ui.builder.editpseudocode";
	
	private IWorkbenchWindow window;
	
	public EditPseudocodeAction(IWorkbenchWindow window) {
		super("Edit Pseudocode");
		this.window = window;
		setId(ID);
	}
	
	@Override
	public void run() {
		IWorkbenchPage page = window.getActivePage();
		PseudoEditorInput input = new PseudoEditorInput();
		try {
			page.openEditor(input, PseudoEditor.ID);
			page.showView(PseudoSnippetsView.ID);
		}
		catch (PartInitException ex) {
			// TODO
			ex.printStackTrace();
		}
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
