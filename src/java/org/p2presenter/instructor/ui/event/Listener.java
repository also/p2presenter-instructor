/* $Id$ */

package org.p2presenter.instructor.ui.event;

/** Listener interface for events of a particular class.
 * @author Ryan Berdeen
 *
 * @param <T> the type of event.
 */
public interface Listener<T extends Event> {
	/** Called to notify the listener of an event.
	 * @param event
	 */
	public void onEvent(T event);
}
