package org.p2presenter.instructor.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.p2presenter.instructor.model.Slide;
import org.p2presenter.instructor.ui.active.LectureOpenedEvent;
import org.p2presenter.instructor.ui.event.Listener;
import org.p2presenter.instructor.ui.event.ListenerAdaptor;
import org.p2presenter.instructor.ui.event.ListenerRegistry;

public class LiveDisplay {
	private Display display;
	private Monitor monitor;
	private Shell shell;
	
	private SlideImageCache slideImageCache;
	private Canvas slideCanvas;
	
	private Listener<SlideChangedEvent> slideChangedListener;
	private Listener<LectureOpenedEvent> lectureOpenedListener;
	
	public LiveDisplay(Display display, Monitor monitor) {
		this.display = display;
		this.monitor = monitor;
		
		shell = new Shell(display, SWT.NO_TRIM);
    	shell.setBounds(monitor.getBounds());
    	shell.setLayout(new GridLayout(1, false));
    	shell.setBackground(display.getSystemColor(SWT.COLOR_BLACK));

        Color white = display.getSystemColor(SWT.COLOR_WHITE);
		Color black = display.getSystemColor(SWT.COLOR_BLACK);
		PaletteData palette = new PaletteData(new RGB[] {white.getRGB(),black.getRGB()});
		ImageData sourceData = new ImageData(16, 16, 1, palette);
		sourceData.transparentPixel = 0;
		Cursor cursor = new Cursor(display, sourceData, 0, 0);

		shell.setCursor(cursor);
		
		slideImageCache = new SlideImageCache(display);
		
		slideCanvas = new Canvas(shell, SWT.NO_BACKGROUND);
		slideCanvas.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
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
		
		ListenerRegistry listenerRegistry = Activator.getDefault().getListenerRegsitry();
		lectureOpenedListener = new Listener<LectureOpenedEvent>() {
			public void onEvent(LectureOpenedEvent event) {
				slideImageCache.clear();
				slideCanvas.redraw();
			}
		};
		listenerRegistry.register(LectureOpenedEvent.class, lectureOpenedListener);
		
		slideChangedListener = new ListenerAdaptor<SlideChangedEvent>(this) {
			@Override
			public void onExternalEvent(SlideChangedEvent event) {
				Slide slide = event.getSlide();
				
				if (slide != null) {
					slideImageCache.setCurrentSlide(slide);
					slideCanvas.redraw();
				}
			}
		};
		listenerRegistry.register(SlideChangedEvent.class, slideChangedListener);
	}
	
	public void setVisible(boolean visible) {
		shell.setVisible(visible);
	}
	
	// FIXME needs dispose
	
}
