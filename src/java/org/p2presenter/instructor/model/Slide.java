/* $Id$ */

package org.p2presenter.instructor.model;

import org.ry1.json.JsonObject;

public class Slide extends Entity<Integer> {
	private int index;
	private String title;
	private String body;
	private InteractivityDefinition interactivityDefinition;
	
	private byte[] imageContent;

	@Override
	void setAttributes(JsonObject json) {
		id = json.getInteger("id");
		index = json.getInteger("index");
		title = json.getString("title");
		body = json.getString("body");
		interactivityDefinition = dao.buildEntity(InteractivityDefinition. class, json.getJson("interactivityDefinition"));
	}
	
	public int getIndex() {
		return index;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getBody() {
		return body;
	}
	
	public InteractivityDefinition getInteractivityDefinition() {
		return interactivityDefinition;
	}
	
	public byte[] getImageContent() {
		if (imageContent == null) {
			// TODO don't always keep in memory
			imageContent = dao.getBytes(getUri() + "/image", null);
		}
		return imageContent;
	}
}
