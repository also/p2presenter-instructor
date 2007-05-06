/* $Id$ */

package org.p2presenter.instructor.ui.active;

import org.eclipse.jface.action.Action;
import org.p2presenter.instructor.ui.Activator;
import org.p2presenter.instructor.ui.event.Listener;
import org.p2presenter.instructor.ui.event.ListenerRegistry;

public class BeginLectureAction extends Action {
	public static final String ID = "org.p2presenter.instructor.ui.active.beginLectureAction";
	
	private Activator plugin;
	
	public BeginLectureAction(String text) {
		super(text);
		setId(ID);
		setEnabled(false);
		plugin = Activator.getDefault();
		
		ListenerRegistry listenerRegistry = plugin.getListenerRegsitry();
		
		listenerRegistry.register(LectureOpenedEvent.class, new Listener<LectureOpenedEvent>() {
			public void onEvent(LectureOpenedEvent event) {
				setEnabled(true);
			}
		});
		
		/* disable when a lecture starts */
		listenerRegistry.register(LectureStartedEvent.class, new Listener<LectureStartedEvent>() {
			public void onEvent(LectureStartedEvent event) {
				setEnabled(false);
			}
		});
		
		/* enable when a lecture ends */
		listenerRegistry.register(LectureEndedEvent.class, new Listener<LectureEndedEvent>() {
			public void onEvent(LectureEndedEvent event) {
				setEnabled(true);
			}
		});
		
		// TODO disable when a lecture is closed
	}
	
	@Override
	public void run() {
		plugin.getActiveSession().beginLecture();
	}

}
