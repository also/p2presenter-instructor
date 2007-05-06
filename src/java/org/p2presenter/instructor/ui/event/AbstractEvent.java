/* $Id$ */

package org.p2presenter.instructor.ui.event;

/** Superclass for events that stores the event source.
 * @author Ryan Berdeen
 *
 */
public class AbstractEvent implements Event {
	private Object source;
	
	public AbstractEvent(Object source) {
		this.source = source;
	}

	public Object getSource() {
		return source;
	}

}
