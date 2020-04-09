package storage;

import java.util.Map;

public class ObjectWithKey {
	private String key;
	private Map<String, Object> object;
	
	public ObjectWithKey() {
	}

	public ObjectWithKey(String key, Map<String, Object> object) {
		super();
		this.key = key;
		this.object = object;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Map<String, Object> getObject() {
		return object;
	}

	public void setObject(Map<String, Object> object) {
		this.object = object;
	}
	
	
}
