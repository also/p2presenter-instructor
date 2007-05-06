/* $Id$ */

package org.p2presenter.instructor.model;

import java.util.List;

import org.ry1.json.JsonObject;

public class Person extends Entity<String> {
	private List<Course> coursesTaught;
	
	@Override
	void setAttributes(JsonObject json) {
		id = json.getString("username");
	}
	
	public List<Course> getCoursesTaught() {
		if (coursesTaught == null) {
			coursesTaught = dao.buildEntities(Course.class, dao.getJson("/entity/person/" + id + "/listCoursesTaught").getArray("coursesTaught"));
		}
		return coursesTaught;
	}
}
