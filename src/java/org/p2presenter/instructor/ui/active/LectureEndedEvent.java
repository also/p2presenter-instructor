/* $Id$ */

package org.p2presenter.instructor.ui.active;

import org.p2presenter.instructor.model.ActiveLecture;
import org.p2presenter.instructor.ui.event.AbstractEvent;

public class LectureEndedEvent extends AbstractEvent {
	public LectureEndedEvent(ActiveLecture activeLecture) {
		super(activeLecture);
	}
	
	public ActiveLecture getActiveLecture() {
		return (ActiveLecture) getSource();
	}
}
