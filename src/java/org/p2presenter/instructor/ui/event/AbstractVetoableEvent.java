/* $Id$ */

package org.p2presenter.instructor.ui.event;

public class AbstractVetoableEvent extends AbstractEvent implements VetoableEvent {
	private boolean vetoed = false;
	
	public AbstractVetoableEvent(Object source) {
		super(source);
	}

	public boolean isVetoed() {
		return vetoed;
	}

	public void veto() {
		vetoed = true;
	}

}
