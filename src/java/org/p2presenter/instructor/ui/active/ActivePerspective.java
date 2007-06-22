package org.p2presenter.instructor.ui.active;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.p2presenter.instructor.ui.builder.BuilderPerspective;


public class ActivePerspective implements IPerspectiveFactory {
	public static final String ID = "org.p2presenter.instructor.ui.active";
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);

		layout.addView(SlideListView.ID, IPageLayout.LEFT, 0.15f, editorArea);

		layout.addView(SlideView.ID, IPageLayout.TOP, 0.85f, editorArea);
		layout.addView(ParticipantListView.ID, IPageLayout.RIGHT, 0.82f, SlideView.ID);
		layout.addView(InteractivityRecordingView.ID, IPageLayout.BOTTOM, 0.85f, SlideView.ID);
		
		layout.addPerspectiveShortcut(BuilderPerspective.ID);
		
		layout.addShowViewShortcut(SlideListView.ID);
		layout.addShowViewShortcut(SlideView.ID);
		layout.addShowViewShortcut(ParticipantListView.ID);
		layout.addShowViewShortcut(InteractivityRecordingView.ID);
		layout.addActionSet("org.p2presenter.instructor.ui.liveDisplayActionSet");
	}
}
