package org.p2presenter.instructor.ui;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchWindow;
import org.p2presenter.instructor.model.SessionClosedEvent;
import org.p2presenter.instructor.model.SessionOpenedEvent;
import org.p2presenter.instructor.ui.event.Listener;


public class ConnectAction extends Action {
	public static final String ID = "org.uoregon.cs.presenter.instructor.ui.actions.connect";
	
	public ConnectAction(String text, IWorkbenchWindow window) {
		super(text);
		setId(ID);
		
		Activator.getDefault().getListenerRegsitry().register(SessionOpenedEvent.class, new Listener<SessionOpenedEvent>() {
			public void onEvent(SessionOpenedEvent event) {
				setEnabled(false);
			}
		});
		
		Activator.getDefault().getListenerRegsitry().register(SessionClosedEvent.class, new Listener<SessionClosedEvent>() {
			public void onEvent(SessionClosedEvent event) {
				setEnabled(true);
			}
		});
	}
	
	public void run() {
		Activator.getDefault().getActiveSession();
	}
}
