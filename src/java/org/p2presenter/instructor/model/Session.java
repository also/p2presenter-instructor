/* $Id$ */

package org.p2presenter.instructor.model;

import java.io.IOException;
import java.net.Socket;

import org.eclipse.jface.dialogs.MessageDialog;
import org.p2presenter.instructor.ui.Activator;
import org.p2presenter.instructor.ui.SlideChangedEvent;
import org.p2presenter.instructor.ui.active.BeforeLectureOpenedEvent;
import org.p2presenter.instructor.ui.active.LectureEndedEvent;
import org.p2presenter.instructor.ui.active.LectureOpenedEvent;
import org.p2presenter.instructor.ui.active.LectureStartedEvent;
import org.p2presenter.instructor.ui.event.ClassListenerRegistry;
import org.p2presenter.instructor.ui.event.Listener;
import org.p2presenter.instructor.ui.event.ListenerRegistry;
import org.p2presenter.messaging.Connection;
import org.p2presenter.messaging.ConnectionListener;
import org.p2presenter.messaging.LocalConnection;

import edu.uoregon.cs.p2presenter.authentication.AuthenticationUtils;

public class Session {
	private Activator plugin;
	private ClassListenerRegistry<SessionClosedEvent> sessionClosedListeners;
	private ClassListenerRegistry<LectureStartedEvent> lectureStartedListeners;
	private Dao dao;
	private LocalConnection connection;
	
	private Lecture openLecture;
	
	private ActiveLecture activeLecture;
	
	private String username;
	
	public Session(Activator plugin, String host, String username, String password) throws IOException {
		this.plugin = plugin;
		this.username = username;
		connection = new LocalConnection(new Socket(host, 9000));
		connection.start();
		AuthenticationUtils.login(connection, username, password);
		
		ListenerRegistry listenerRegistry = plugin.getListenerRegsitry();
		sessionClosedListeners = listenerRegistry.getListeners(SessionClosedEvent.class);
		lectureStartedListeners = listenerRegistry.getListeners(LectureStartedEvent.class);
		
		connection.addConnectionListener(new ConnectionListener() {
			public void connectionClosed(Connection connection) {
				sessionClosedListeners.onEvent(new SessionClosedEvent(Session.this));
			}
		});
		
		/* */
		listenerRegistry.register(SlideChangedEvent.class, new Listener<SlideChangedEvent>() {
			public void onEvent(SlideChangedEvent event) {
				if (activeLecture != null && event.getSlide() != null) {
					activeLecture.setCurrentSlideIndex(event.getSlide().getIndex());
				}
			}
		});
		
		/* prompt the user to cancel an active lecture before a lecture is opened */
		listenerRegistry.register(BeforeLectureOpenedEvent.class, new Listener<BeforeLectureOpenedEvent>() {
			public void onEvent(BeforeLectureOpenedEvent event) {
				// TODO get shell
				if (activeLecture != null && !MessageDialog.openConfirm(null, "Open Lecture", "Opening a new lecture will end the current lecture.\n\nAre you sure you want to continue?")) {
					event.veto();
				}
			}
		});
		
		/* end the lecture session when a new lecture is opened */
		listenerRegistry.register(LectureOpenedEvent.class, new Listener<LectureOpenedEvent>() {
			public void onEvent(LectureOpenedEvent event) {
				openLecture = event.getLecture();
				if (activeLecture != null) {
					activeLecture.end();
				}
			}
		});
		
		/* clear the active lecture when it ends */
		listenerRegistry.register(LectureEndedEvent.class, new Listener<LectureEndedEvent>() {
			public void onEvent(LectureEndedEvent event) {
				activeLecture = null;
			}
		});
		
		plugin.getListenerRegsitry().onEvent(new SessionOpenedEvent(this));
	}
	
	public String getSiteName() {
		// TODO should be configured on the server
		return "p2presenter";
	}
	
	public String getUsername() {
		return username;
	}
	
	public Person getPerson() {
		return getDao().getEntity(Person.class, getUsername());
	}
	
	public synchronized Dao getDao() {
		if (dao == null) {
			dao = new Dao(this);
		}
		
		return dao;
	}
	
	public LocalConnection getConnection() {
		return connection;
	}
	
	public ActiveLecture beginLecture() {
		activeLecture = dao.buildEntity(ActiveLecture.class, dao.getJson("/entity/lecture/" + openLecture.getId() + "/begin"));
		lectureStartedListeners.onEvent(new LectureStartedEvent(this, activeLecture));
		
		return activeLecture;
	}
	
	public Lecture getOpenLecture() {
		return openLecture;
	}
	
	public ActiveLecture getActiveLecture() {
		return activeLecture;
	}
	
	public void close() {
		// FIXME
	}
}
