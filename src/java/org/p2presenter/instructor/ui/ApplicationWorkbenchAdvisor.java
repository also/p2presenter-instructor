package org.p2presenter.instructor.ui;

import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.p2presenter.instructor.ui.active.ActivePerspective;


public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {
	public void initialize(IWorkbenchConfigurer configurer) {
		configurer.setSaveAndRestore(false);
	}
	
    public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        return new ApplicationWorkbenchWindowAdvisor(configurer);
    }

	public String getInitialWindowPerspectiveId() {
		return ActivePerspective.ID;
	}
}
