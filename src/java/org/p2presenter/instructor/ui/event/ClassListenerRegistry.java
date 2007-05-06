/* $Id$ */

package org.p2presenter.instructor.ui.event;

import java.util.HashSet;

/** Registy for event listeners of a particular class of event.
 * @author Ryan Berdeen
 *
 * @param <T> the class of event
 */
public class ClassListenerRegistry<T extends Event> {
	private HashSet<Listener<T>> listeners = new HashSet<Listener<T>>();
	
	/** Registers an event listener.
	 * @param listener the listener to register
	 */
	public void register(Listener<T> listener) {
		listeners.add(listener);
	}

	/** Unregisters an event listener.
	 * @param listener the listener to unregister
	 */
	public void unregister(Listener<T> listener) {
		listeners.remove(listener);
	}
	
	/** Notifies all event listeners of the event.
	 * If the event is vetoable, returns false as soon as the event is vetoed.
	 */
	public boolean onEvent(T event) {
		if (event instanceof VetoableEvent) {
			VetoableEvent vetoable = (VetoableEvent) event;
			for (Listener<T> listener : listeners) {
				listener.onEvent(event);
				if (vetoable.isVetoed()) {
					return false;
				}
			}
		}
		else {
			for (Listener<T> listener : listeners) {
				listener.onEvent(event);
			}
		}
		
		return true;
	}
}
