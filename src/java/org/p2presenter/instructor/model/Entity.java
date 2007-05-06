/* $Id$ */

package org.p2presenter.instructor.model;

import java.io.Serializable;

import org.ry1.json.JsonObject;
import org.ry1.json.PropertyList;

public abstract class Entity<I extends Serializable> {
	protected Dao dao;
	
	protected I id;
	
	void setDao(Dao dao) {
		this.dao = dao;
	}
	
	public JsonObject toJson(PropertyList propertyList) {
		return new JsonObject(this, propertyList);
	}
	
	public I getId() {
		return id;
	}
	
	public void reload() {
		dao.reloadEntity(this);
	}
	
	public String getUri() {
		return "/entity/" + getClass().getSimpleName().toLowerCase() + '/' + id;
	}
	
	abstract void setAttributes(JsonObject json);
}
