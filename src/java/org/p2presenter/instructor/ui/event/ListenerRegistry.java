/* $Id$ */

package org.p2presenter.instructor.ui.event;

import java.util.HashMap;

/** Regsitry for all listeners in the application.
 * If a source will repeatedly generate events of a particular class,
 * it should use the <code>ClassListenerRegsitry</code> for that class
 * directly, to avoid looking up the registry for every event.
 * @author Ryan Berdeen
 *
 */
public class ListenerRegistry {
	private HashMap<Class, ClassListenerRegistry> listenerRegistry = new HashMap<Class, ClassListenerRegistry>();
	
	/** Registers a <code>Listener</code> with the appropriate <code>ClassListenerRegistry</code>.
	 * @param <T> the class of event
	 * @param eventClass the class of event
	 * @param listener the listener to register
	 */
	public <T extends Event> void register(Class<T> eventClass, Listener<T> listener) {
		getListeners(eventClass).register(listener);
	}
	
	/** Unregisters a <code>Listener</code> from the appropriate <code>ClassListenerRegistry</code>.
	 * @param <T> the class of event
	 * @param eventClass the class of event
	 * @param listener the listener to unregister
	 */
	public <T extends Event> void unregister(Class<T> eventClass, Listener<T> listener) {
		ClassListenerRegistry<T> listeners = getListenersInternal(eventClass);
		if (listeners != null) {
			listeners.unregister(listener);
		}
	}
	
	/** Returns the <code>ClassListenerRegistry</code> for the given event class.
	 */
	public <T extends Event> ClassListenerRegistry<T> getListeners(Class<T> eventClass) {
		synchronized (listenerRegistry) {
			ClassListenerRegistry<T> listeners = getListenersInternal(eventClass);
			if (listeners == null) {
				listeners = new ClassListenerRegistry<T>();
				listenerRegistry.put(eventClass, listeners);
			}
			return listeners;
		}
	}
	
	private <T extends Event> ClassListenerRegistry<T> getListenersInternal(Class<T> eventClass) {
		return listenerRegistry.get(eventClass);
	}
	
	/** Notifies the appropriate <code>ClassListenerRegistry</code> of the event.
	 */
	public boolean onEvent(Event event) {
		ClassListenerRegistry listeners = listenerRegistry.get(event.getClass());
		if (listeners != null) {
			return listeners.onEvent(event);
		}
		else {
			return true;
		}
	}
}
