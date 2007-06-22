/* $Id$ */

package org.p2presenter.instructor.ui.builder;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.p2presenter.instructor.ui.active.ActivePerspective;


public class BuilderPerspective implements IPerspectiveFactory {
	public static final String ID = "org.p2presenter.instructor.ui.builder";
	
	public void createInitialLayout(IPageLayout layout) {
		// TODO Auto-generated method stub
		layout.addPerspectiveShortcut(ActivePerspective.ID);
		layout.addActionSet("org.p2presenter.instructor.ui.builderActionSet");
	}

}
