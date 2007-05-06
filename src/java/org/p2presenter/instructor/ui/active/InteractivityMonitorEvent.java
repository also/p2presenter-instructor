package org.p2presenter.instructor.ui.active;

import org.p2presenter.instructor.ui.event.AbstractEvent;

import edu.uoregon.cs.p2presenter.interactivity.monitor.InteractivityEvent;

public class InteractivityMonitorEvent extends AbstractEvent {
	private InteractivityEvent<?> interactivityEvent;
	
	public InteractivityMonitorEvent(Object source, InteractivityEvent<?> interactivityEvent) {
		super(source);
		this.interactivityEvent = interactivityEvent;
	}
	
	public InteractivityEvent<?> getInteractivityEvent() {
		return interactivityEvent;
	}

}
