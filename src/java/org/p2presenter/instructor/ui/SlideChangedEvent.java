/* $Id$ */

package org.p2presenter.instructor.ui;

import org.p2presenter.instructor.model.Slide;
import org.p2presenter.instructor.ui.event.Event;


/** Event fired when the slide to display has changed.
 * @author Ryan Berdeen
 *
 */
public class SlideChangedEvent implements Event {
	private Object source;
	private Slide slide;
	
	public SlideChangedEvent(Object source, Slide slide) {
		this.source = source;
		this.slide = slide;
	}

	public Object getSource() {
		return source;
	}
	
	public Slide getSlide() {
		return slide;
	}

}
