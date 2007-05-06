/* $Id$ */

package org.p2presenter.instructor.ui.active;

import org.eclipse.jface.action.Action;
import org.p2presenter.instructor.model.ActiveLecture;
import org.p2presenter.instructor.ui.Activator;
import org.p2presenter.instructor.ui.event.Listener;
import org.p2presenter.instructor.ui.event.ListenerRegistry;

public class EndLectureAction extends Action {
	public static final String ID = "org.p2presenter.instructor.ui.active.endLectureAction";
	
	private Activator plugin;
	
	private ActiveLecture activeLecture;
	
	public EndLectureAction(String text) {
		super(text);
		setId(ID);
		setEnabled(false);
		plugin = Activator.getDefault();
		
		ListenerRegistry listenerRegistry = plugin.getListenerRegsitry();
		
		/* enable when a lecture is opened */
		listenerRegistry.register(LectureStartedEvent.class, new Listener<LectureStartedEvent>() {
			public void onEvent(LectureStartedEvent event) {
				setEnabled(true);
				activeLecture = event.getActiveLecture();
			}
		});
		
		/* disable when a lecture is closed */
		listenerRegistry.register(LectureEndedEvent.class, new Listener<LectureEndedEvent>() {
			public void onEvent(LectureEndedEvent event) {
				setEnabled(false);
			}
		});
	}
	
	@Override
	public void run() {
		activeLecture.end();
	}

}
