/* $Id$ */

package org.p2presenter.instructor.model;

import java.util.List;

import org.ry1.json.JsonObject;

public class Course extends Entity<Integer> {
	private String subject;
	private String number;
	private String title;
	private List<Lecture> lectures;
	
	@Override
	void setAttributes(JsonObject json) {
		id = json.getInteger("id");
		subject = json.getString("subject");
		number = json.getString("number");
		title = json.getString("title");
		lectures = dao.buildEntities(Lecture.class, json.getArray("lectures"));
	}

	public List<Lecture> getLectures() {
		if (lectures == null) {
			reload();
		}

		return lectures;
	}

	public String getNumber() {
		return number;
	}

	public String getSubject() {
		return subject;
	}

	public String getTitle() {
		return title;
	}
}
