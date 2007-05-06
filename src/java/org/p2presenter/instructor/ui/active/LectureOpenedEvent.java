/* $Id$ */

package org.p2presenter.instructor.ui.active;

import org.p2presenter.instructor.model.Lecture;
import org.p2presenter.instructor.ui.event.AbstractEvent;

public class LectureOpenedEvent extends AbstractEvent {
	private Lecture lecture;
	
	public LectureOpenedEvent(Object source, Lecture lecture) {
		super(source);
		this.lecture = lecture;
	}
	
	public Lecture getLecture() {
		return lecture;
	}
	
}
