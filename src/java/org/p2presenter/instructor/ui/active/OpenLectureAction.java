/* $Id */

package org.p2presenter.instructor.ui.active;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.p2presenter.instructor.model.Lecture;
import org.p2presenter.instructor.ui.Activator;
import org.p2presenter.instructor.ui.wizard.OpenLectureWizard;

public class OpenLectureAction extends Action {
	public static final String ID = "org.p2presenter.instructor.ui.active.openLectureAction";
	
	private Activator plugin;
	private IWorkbenchWindow window;
	
	public OpenLectureAction(String text, IWorkbenchWindow window) {
		super(text);
		setId(ID);
		this.window = window;
		plugin = Activator.getDefault();
	}
	
	@Override
	public void run() {
		if (!plugin.getListenerRegsitry().onEvent(new BeforeLectureOpenedEvent(this))) {
			return;
		}
		OpenLectureWizard openLectureWizard = new OpenLectureWizard();
		WizardDialog dialog = new WizardDialog(window.getShell(), openLectureWizard);
		if (dialog.open() == WizardDialog.CANCEL) {
			return;
		}
		
		Lecture lecture = openLectureWizard.getLecture();
		plugin.getListenerRegsitry().onEvent(new LectureOpenedEvent(this, lecture));
	}
}
