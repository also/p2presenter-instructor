/* $Id$ */

package org.p2presenter.instructor.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ry1.json.JsonObject;
import org.ry1.json.PropertyList;

public class Lecture extends Entity<Integer> {
	public static final PropertyList DEFAULT_PROPERTIES;
	static {
		DEFAULT_PROPERTIES = new PropertyList()
			.includeValues("id", "title");
	}
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
	
	public void setTitle(String title) {
		this.title = title;
	}

	public void addSlideImage(File slideFile) throws IOException {
		byte[] slideBytes = new byte[(int) slideFile.length()];
		FileInputStream in = new FileInputStream(slideFile);
		in.read(slideBytes);
		
		dao.getString(getUri() + "/addSlideImage", slideBytes);
	}
}
