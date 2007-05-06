/* $Id$ */

package org.p2presenter.instructor.ui.active;

import org.p2presenter.instructor.ui.event.AbstractVetoableEvent;

public class BeforeLectureOpenedEvent extends AbstractVetoableEvent {
	public BeforeLectureOpenedEvent(Object source) {
		super(source);
	}
}
