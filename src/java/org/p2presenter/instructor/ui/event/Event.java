/* $Id$ */

package org.p2presenter.instructor.ui.event;

/** Interface for all application events.
 * @author Ryan Berdeen
 *
 */
public interface Event {
	/** Returns the object that generated the event.
	 */
	public Object getSource();
}
