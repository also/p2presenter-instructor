/* $Id$ */

package org.p2presenter.instructor.ui.event;

/** An event than can be vetoed.
 * When an event is vetoed, no further listeners are notified of the event.
 * @author Ryan Berdeen
 *
 */
public interface VetoableEvent extends Event {
	public void veto();
	
	public boolean isVetoed();
}
