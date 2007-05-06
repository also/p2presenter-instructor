/* $Id$ */

package org.p2presenter.instructor.model;

import org.ry1.json.JsonObject;

public class InteractivityDefinition extends Entity<Integer> {

	@Override
	void setAttributes(JsonObject json) {
		id = json.getInteger("id");

	}

}
