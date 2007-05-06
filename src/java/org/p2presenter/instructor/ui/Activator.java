package org.p2presenter.instructor.ui;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.p2presenter.instructor.model.Session;
import org.p2presenter.instructor.model.SessionClosedEvent;
import org.p2presenter.instructor.ui.active.InteractivityRecordingView;
import org.p2presenter.instructor.ui.event.Listener;
import org.p2presenter.instructor.ui.event.ListenerRegistry;


/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.p2presenter.instructor.ui";

	// The shared instance
	private static Activator plugin;
	
	private ListenerRegistry listenerRegsitry;
	
	private InteractivityRecordingView interactivityRecordingView;
	
	private Session activeSession;
	
	/**
	 * The constructor
	 */
	public Activator() {
		plugin = this;
		listenerRegsitry = new ListenerRegistry();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	public ListenerRegistry getListenerRegsitry() {
		return listenerRegsitry;
	}
	
	public void setInteractivityRecordingView(InteractivityRecordingView interactivityRecordingView) {
		this.interactivityRecordingView = interactivityRecordingView;
	}
	
	public InteractivityRecordingView getInteractivityRecordingView() {
		return interactivityRecordingView;
	}
	
	public synchronized Session getActiveSession() {
		if (activeSession == null) {
			InputDialog hostInput = new InputDialog(null, "Connect to server", "Server host:", "localhost", null);
			if (hostInput.open() == InputDialog.CANCEL) {
				return null;
			}
			String host = hostInput.getValue();
			
			// TODO prompt for username, password
			try {
				activeSession = new Session(this, host, "ryan", "rb041801");
			}
			catch (Exception ex) {
				// TODO
				ex.printStackTrace();
				MessageDialog.openError(null, null, "Could not connect:\n" + ex.getMessage());
			}
			
			listenerRegsitry.register(SessionClosedEvent.class, new Listener<SessionClosedEvent>() {
				public void onEvent(SessionClosedEvent event) {
					sessionClosed();
				}
			});
		}
		
		return activeSession;
	}
	
	public boolean isSessionOpen() {
		return activeSession != null;
	}
	
	private void sessionClosed() {
		this.activeSession = null;
	}
}
