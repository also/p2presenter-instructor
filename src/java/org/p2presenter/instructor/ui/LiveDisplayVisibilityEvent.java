package org.p2presenter.instructor.ui;

import org.p2presenter.instructor.ui.event.AbstractEvent;

public class LiveDisplayVisibilityEvent extends AbstractEvent {
	public LiveDisplayVisibilityEvent(LiveDisplay source) {
		super(source);
	}
	
	public LiveDisplay getLiveDisplay() {
		return (LiveDisplay) getSource();
	}

}
