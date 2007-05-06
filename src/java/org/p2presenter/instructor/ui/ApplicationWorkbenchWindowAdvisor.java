package org.p2presenter.instructor.ui;

import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.p2presenter.instructor.model.SessionClosedEvent;
import org.p2presenter.instructor.model.SessionOpenedEvent;
import org.p2presenter.instructor.ui.event.Listener;


public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }
    
    public void preWindowOpen() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setInitialSize(new Point(800, 600));
        configurer.setShowCoolBar(false);
        configurer.setShowMenuBar(true);
        configurer.setShowStatusLine(true);
        configurer.setShowPerspectiveBar(true);
        
        configurer.setTitle("p2presenter Instructor");
    }
    
    @Override
    public void postWindowOpen() {
    	final IStatusLineManager statusLine = getWindowConfigurer().getActionBarConfigurer().getStatusLineManager();
    	statusLine.setMessage("Welcome to p2presenter Instructor");
    	
    	Activator plugin = Activator.getDefault();
    	plugin.getListenerRegsitry().register(SessionOpenedEvent.class, new Listener<SessionOpenedEvent>() {
			public void onEvent(SessionOpenedEvent event) {
				statusLine.setMessage("Connected");
			}
		});
		
    	plugin.getListenerRegsitry().register(SessionClosedEvent.class, new Listener<SessionClosedEvent>() {
			public void onEvent(SessionClosedEvent event) {
				getWindowConfigurer().getWindow().getShell().getDisplay().asyncExec(new Runnable() {
					public void run() {
						statusLine.setMessage("Not connected");
					}
				});
			}
		});
    }
}
