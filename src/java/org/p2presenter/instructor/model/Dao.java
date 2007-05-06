/* $Id$ */

package org.p2presenter.instructor.model;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.p2presenter.messaging.LocalConnection;
import org.p2presenter.messaging.message.IncomingResponseMessage;
import org.p2presenter.messaging.message.OutgoingRequestMessage;
import org.ry1.json.JsonArray;
import org.ry1.json.JsonObject;

public class Dao {
	private Session session;
	
	public Dao(Session session) {
		this.session = session;
	}
	
	public LocalConnection getConnection() {
		return session.getConnection();
	}
	
	JsonObject getJson(String uri) {
		return JsonObject.valueOf(get(uri, null));
	}
	
	public String get(String uri, String content) {
		OutgoingRequestMessage request = new OutgoingRequestMessage(getConnection(), uri);
		request.setContent(content);
		try {
			IncomingResponseMessage response = getConnection().sendRequestAndAwaitResponse(request);
			if (response.getStatus() == 200) {
				return response.getContentAsString();
			}
			else {
				throw new RuntimeException("URI: " + uri + ", Status: " + response.getStatus() + ", Message: " + response.getContentAsString());
			}
		}
		catch (InterruptedException ex) {
			return null;
		}
		catch (IOException ex) {
			// TODO exception type
			throw new RuntimeException(ex);
		}
	}
	
	public <T extends Entity> T getEntity(Class<T> entityClass, Serializable entityId) {
		JsonObject json = getJson("/entity/" + entityClass.getSimpleName().toLowerCase() + '/' + entityId + "/get");
		
		return json != null ?  buildEntity(entityClass, json) : null;
	}
	
	public void reloadEntity(Entity entity) {
		JsonObject json = getJson("/entity/" + entity.getClass().getSimpleName().toLowerCase() + '/' + entity.getId() + "/get");
		if (json == null) {
			// TODO exception type
			throw new RuntimeException("Entity unavailable");
		}
		else {
			entity.setAttributes(json);
		}
	}
	
	public <T extends Entity> T buildEntity(Class<T> entityClass, JsonObject json) {
		if (json == null) {
			return null;
		}
		try {
			Constructor<T> constructor = entityClass.getConstructor((Class[]) null);
			T result = constructor.newInstance((Object[]) null);
			result.setDao(this);
			result.setAttributes(json);
			return result;
		}
		catch (NoSuchMethodException ex) {
			// TODO exception type
			throw new RuntimeException(ex);
		}
		catch (InstantiationException ex) {
			// TODO exception type
			throw new RuntimeException(ex);
		}
		catch (InvocationTargetException ex) {
			// TODO exception type
			throw new RuntimeException(ex);
		}
		catch (IllegalAccessException ex) {
			// TODO exception type
			throw new RuntimeException(ex);
		}
	}
	
	public <T extends Entity> List<T> buildEntities(Class<T> entityClass, JsonArray jsonArray) {
		ArrayList<T> result = null;
		if (jsonArray != null) {
			result = new ArrayList<T>(jsonArray.getList().size());
			for (Object entityObject : jsonArray.getList()) {
				result.add(buildEntity(entityClass, (JsonObject) entityObject));
			}
		}
		
		return result;
	}
}
