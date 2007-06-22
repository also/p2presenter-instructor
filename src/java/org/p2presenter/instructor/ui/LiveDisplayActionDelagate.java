/* $Id$ */

package org.p2presenter.instructor.ui;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.p2presenter.instructor.ui.event.Listener;

public class LiveDisplayActionDelagate implements IWorkbenchWindowActionDelegate {
	public static final String ID = "org.p2presenter.instructor.ui.liveDisplayAction";
	
	private IWorkbenchWindow window;
	
	private IAction action;
	
	public LiveDisplayActionDelagate() {
		Activator.getDefault().getListenerRegsitry().register(LiveDisplayVisibilityEvent.class, new Listener<LiveDisplayVisibilityEvent>() {
			public void onEvent(LiveDisplayVisibilityEvent event) {
				action.setChecked(event.getLiveDisplay().isVisible());
			}
		});
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	public void run(IAction action) {
		LiveDisplay liveDisplay = Activator.getDefault().getLiveDisplay();
		if (liveDisplay != null) {
			liveDisplay.setVisible(!liveDisplay.isVisible());
			window.getShell().forceActive();
		}
		else {
			action.setChecked(false);
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		this.action = action;
	}
}
