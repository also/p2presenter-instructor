package org.p2presenter.instructor.ui;

import java.io.ByteArrayInputStream;
import java.util.HashMap;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.p2presenter.instructor.model.Slide;

public class SlideImageCache {
	protected Display display;
	
	private HashMap<Slide, Image> slideImages = new HashMap<Slide, Image>();
	private Image currentImage;
	
	public SlideImageCache(Display display) {
		this.display = display;
	}
	
	public void setCurrentSlide(Slide currentSlide) {
		currentImage = currentSlide != null ? getImage(currentSlide) : null;
	}
	
	public Image getCurrentImage() {
		return currentImage;
	}
	
	public Image getImage(Slide slide) {
		synchronized (slideImages) {
			Image slideImage = slideImages.get(slide);
			if (slideImage == null) {
				slideImage = createImage(slide);
				slideImages.put(slide, slideImage);
			}
			
			return slideImage;
		}
	}
	
	protected Image createImage(Slide slide) {
		return new Image(display, new ByteArrayInputStream(slide.getImageContent()));
	}
	
	public void clear() {
		currentImage = null;
		for (Image image : slideImages.values()) {
			image.dispose();
		}
	}
}
