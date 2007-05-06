/* $Id$ */

package org.p2presenter.instructor.model;

import org.p2presenter.instructor.ui.Activator;
import org.p2presenter.instructor.ui.active.LectureEndedEvent;
import org.ry1.json.JsonObject;

public class ActiveLecture extends Entity<Integer> {
	private int currentSlideIndex = -1;
	private boolean active = true;
	
	@Override
	void setAttributes(JsonObject json) {
		id = json.getInteger("id");
	}
	
	public void end() {
		dao.getJson("/entity/activeLecture/" + id + "/end");
		active = false;
		Activator.getDefault().getListenerRegsitry().onEvent(new LectureEndedEvent(this));
	}
	
	public void setCurrentSlideIndex(int index) {
		// don't update the lecture if we don't need to
		if (currentSlideIndex != index) {
			dao.get("/entity/activeLecture/" + id + "/setCurrentSlideIndex", String.valueOf(index));
			currentSlideIndex = index;
		}
	}
	
	public int getCurrentSlideIndex() {
		return currentSlideIndex;
	}
	
	public boolean isActive() {
		return active;
	}

}
