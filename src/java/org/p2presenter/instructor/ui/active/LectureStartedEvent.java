/* $Id$ */

package org.p2presenter.instructor.ui.active;

import org.p2presenter.instructor.model.ActiveLecture;
import org.p2presenter.instructor.ui.event.AbstractEvent;

public class LectureStartedEvent extends AbstractEvent {
	private ActiveLecture activeLecture;
	
	public LectureStartedEvent(Object source, ActiveLecture activeLecture) {
		super(source);
		this.activeLecture = activeLecture;
	}
	
	public ActiveLecture getActiveLecture() {
		return activeLecture;
	}

}
