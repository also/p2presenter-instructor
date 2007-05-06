/* $Id$ */

package org.p2presenter.instructor.model;

import java.util.ArrayList;
import java.util.List;

import org.ry1.json.JsonObject;

public class Lecture extends Entity<Integer> {
	private Person creator;
	private String title;

	private List<Slide> slides = new ArrayList<Slide>();

	@Override
	void setAttributes(JsonObject json) {
		id = json.getInteger("id");
		title = json.getString("title");
		slides = dao.buildEntities(Slide.class, json.getArray("slides"));
	}

	public Person getCreator() {
		return creator;
	}

	public List<Slide> getSlides() {
		if (slides == null) {
			reload();
		}
		return slides;
	}

	public String getTitle() {
		return title;
	}

}
