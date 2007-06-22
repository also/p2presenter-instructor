/* $Id$ */

package org.p2presenter.instructor.ui.builder;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public class NewLectureActionDelegate implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	
	public void dispose() {
		// TODO Auto-generated method stub
		
		// TODO dispose the wizard?
		
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	public void run(IAction action) {
		NewLectureWizard newLectureWizard = new NewLectureWizard();
		WizardDialog dialog = new WizardDialog(window.getShell(), newLectureWizard);
		if (dialog.open() == WizardDialog.CANCEL) {
			return;
		}
		
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		
	}

}
