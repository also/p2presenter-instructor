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
import org.p2presenter.instructor.ui.event.ClassListenerRegistry;
import org.p2presenter.instructor.ui.event.Listener;
import org.p2presenter.instructor.ui.event.ListenerAdaptor;
import org.p2presenter.instructor.ui.event.ListenerRegistry;

public class LiveDisplay {
	private Display display;
	private Monitor monitor;
	private Shell shell;
	
	private boolean fullscreen;
	
	private Cursor invisibleCursor;
	
	private SlideImageCache slideImageCache;
	private Canvas slideCanvas;
	
	private Listener<SlideChangedEvent> slideChangedListener;
	private Listener<LectureOpenedEvent> lectureOpenedListener;
	
	private ClassListenerRegistry<LiveDisplayVisibilityEvent> visibilityListeners;
	
	private PaintListener canvasPaintListener;
	
	public LiveDisplay(Display display, Monitor monitor, boolean fullscreen) {
		this.display = display;

        Color white = display.getSystemColor(SWT.COLOR_WHITE);
		Color black = display.getSystemColor(SWT.COLOR_BLACK);
		PaletteData palette = new PaletteData(new RGB[] {white.getRGB(),black.getRGB()});
		ImageData sourceData = new ImageData(16, 16, 1, palette);
		sourceData.transparentPixel = 0;
		invisibleCursor = new Cursor(display, sourceData, 0, 0);
		
		canvasPaintListener = new PaintListener() {
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
		};
		
		showOn(monitor, fullscreen);
		
		slideImageCache = new SlideImageCache(display);
		
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
		
		visibilityListeners = listenerRegistry.getListeners(LiveDisplayVisibilityEvent.class);
	}
	
	public void showOn(Monitor monitor, boolean fullscreen) {
		if (this.monitor != null && fullscreen == this.fullscreen) {
			if (monitor.equals(this.monitor)) {
				return;
			}
			else {
				moveTo(monitor);
				this.monitor = monitor;
			}
		}
		
		/* changing to/from fullscreen */
		
		this.monitor = monitor;
		this.fullscreen = fullscreen;
		
		if (shell != null) {
			shell.dispose();
		}
		
		if (fullscreen) {
			shell = new Shell(display, SWT.NO_TRIM);
			shell.setCursor(invisibleCursor);
		}
		else {
			shell = new Shell(display, SWT.TITLE | SWT.MIN | SWT.RESIZE);
			shell.setText("Live Display");
			// TODO listen for shell closing
		}
		moveTo(monitor);
		
    	shell.setLayout(new GridLayout(1, false));
    	shell.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
		
		slideCanvas = new Canvas(shell, SWT.NO_BACKGROUND);
		slideCanvas.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
		slideCanvas.setLayoutData(new GridData(GridData.FILL_BOTH));
		slideCanvas.addPaintListener(canvasPaintListener);
	}
	
	public void moveTo(Monitor monitor) {
		if (fullscreen) {
	    	shell.setBounds(monitor.getBounds());
		}
		else {
			Rectangle b = monitor.getClientArea();
			shell.setBounds((int) (b.width * .25), (int) (b.height * .25), (int) (b.width * .5), (int) (b.height * .5));
		}
	}
	
	public boolean isVisible() {
		return shell.isVisible();
	}
	
	public void setVisible(boolean visible) {
		shell.setVisible(visible);
		visibilityListeners.onEvent(new LiveDisplayVisibilityEvent(this));
	}
	
	public void dispose() {
		shell.dispose();
		invisibleCursor.dispose();
	}
	
}
