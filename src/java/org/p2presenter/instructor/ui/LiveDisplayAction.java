/* $Id$ */

package org.p2presenter.instructor.ui;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.p2presenter.instructor.ui.event.Listener;

public class LiveDisplayAction extends Action implements IWorkbenchAction {
	public static final String ID = "org.p2presenter.instructor.ui.liveDisplayAction";
	
	public LiveDisplayAction(String text) {
		super(text, AS_CHECK_BOX);
		setId(ID);
		
		Activator.getDefault().getListenerRegsitry().register(LiveDisplayVisibilityEvent.class, new Listener<LiveDisplayVisibilityEvent>() {
			public void onEvent(LiveDisplayVisibilityEvent event) {
				setChecked(event.getLiveDisplay().isVisible());
			}
		});
	}
	
	@Override
	public void run() {
		LiveDisplay liveDisplay = Activator.getDefault().getLiveDisplay();
		if (liveDisplay != null) {
			liveDisplay.setVisible(!liveDisplay.isVisible());
		}
		else {
			setChecked(false);
		}
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
