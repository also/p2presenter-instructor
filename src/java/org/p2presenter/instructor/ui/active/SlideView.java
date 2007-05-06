/* $Id$ */

package org.p2presenter.instructor.ui.active;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.part.ViewPart;
import org.p2presenter.instructor.model.Slide;
import org.p2presenter.instructor.ui.Activator;
import org.p2presenter.instructor.ui.SlideChangedEvent;
import org.p2presenter.instructor.ui.SlideImageCache;
import org.p2presenter.instructor.ui.event.Listener;
import org.p2presenter.instructor.ui.event.ListenerAdaptor;
import org.p2presenter.instructor.ui.event.ListenerRegistry;

public class SlideView extends ViewPart {
	public static final String ID = "org.p2presenter.instructor.ui.active.slideview";
	
	private Activator plugin = Activator.getDefault();
	
	private Label slideLabel;
	
	private Composite top;
	
	private Slide slide;
	
	private Canvas slideCanvas;
	
	private Listener<SlideChangedEvent> slideChangedListener;
	private Listener<LectureOpenedEvent> lectureOpenedListener;
	
	private ISelectionListener postSelectionListener;
	
	private SlideImageCache slideImageCache;
	
	public void createPartControl(final Composite parent) {
		slideImageCache = new SlideImageCache(parent.getDisplay());
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.numColumns = 1;
		parent.setLayout(layout);
		
		top = new Composite(parent, SWT.NONE);
		top.setLayout(layout);
		slideCanvas = new Canvas(top, SWT.NO_BACKGROUND);
		slideCanvas.setLayoutData(new GridData(GridData.FILL_BOTH));
		slideCanvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Rectangle c = slideCanvas.getBounds();
				Image slideImage = slideImageCache.getCurrentImage();
				if (slideImage != null) {
					Rectangle s = slideImage.getBounds();
					e.gc.drawImage(slideImage, 0, 0, s.width, s.height, 0, 0, c.width, c.height);
				}
				else {
					e.gc.fillRectangle(c);
				}
			}
		});
		top.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		slideLabel = new Label(parent, SWT.NONE);
		slideLabel.setText("Select a slide...");
		slideLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		ListenerRegistry listenerRegistry = plugin.getListenerRegsitry();
		lectureOpenedListener = new Listener<LectureOpenedEvent>() {
			public void onEvent(LectureOpenedEvent event) {
				slideImageCache.clear();
				slideCanvas.redraw();
				slideLabel.setText("Select a slide...");
			}
		};
		listenerRegistry.register(LectureOpenedEvent.class, lectureOpenedListener);
		
		slideChangedListener = new ListenerAdaptor<SlideChangedEvent>(this) {
			@Override
			public void onExternalEvent(SlideChangedEvent event) {
				slideChanged(event);
			}
		};
		listenerRegistry.register(SlideChangedEvent.class, slideChangedListener);
		
	}

	public void setFocus() {
		// TODO Auto-generated method stub

	}
	
	private void slideChanged(SlideChangedEvent event) {
		slide = event.getSlide();
		
		if (slide != null) {
			slideLabel.setText(slide.getTitle() != null ? slide.getTitle() : "Untitled Slide");
			
			slideImageCache.setCurrentSlide(slide);
			slideCanvas.redraw();
		}
	}
	
	@Override
	public void dispose() {
		if (slideChangedListener != null) {
			plugin.getListenerRegsitry().unregister(SlideChangedEvent.class, slideChangedListener);
		}
		if (postSelectionListener != null) {
			getSite().getPage().removePostSelectionListener(postSelectionListener);
		}
		slideImageCache.clear();
		
		super.dispose();
	}

}
