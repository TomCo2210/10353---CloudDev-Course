package demo;

public class KeyObjectPair {
	private String key;
	private Demo object;

	public KeyObjectPair() {
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Demo getObject() {
		return object;
	}

	public void setObject(Demo object) {
		this.object = object;
	}

	@Override
	public String toString() {
		return "KeyObjectPair [key=" + key + ", object=" + object + "]";
	}

}
