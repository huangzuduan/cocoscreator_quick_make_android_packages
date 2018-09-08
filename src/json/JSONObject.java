package json;

import java.util.LinkedHashMap;


import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;


public class JSONObject extends LinkedHashMap<String, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JSONObject() {
		super();
	}

	public JSONObject(String json) throws JsonSyntaxException {
		super();
		LinkedHashMap<String, Object> j = JSON.decode(json,
				new TypeToken<LinkedHashMap<String, Object>>() {
				}.getType());
		if (j != null) {
			this.putAll(j);
		}
	}

	/**
	 * 返回JSONObject对象，抛出配置错误
	 * 
	 * @param json
	 * @return JSONObject
	 * @throws Err
	 * @author Rain 2012-4-26
	 */
	public static JSONObject fromConfig(String json) throws Exception {
		try {
			return new JSONObject(json);
		} catch (JsonSyntaxException e) {
			throw e;
		}
	}

	public JSONObject put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public int getInt(String key) {
		return (int) getDouble(key);
	}

	public long getLong(String key) {
		return (long) getDouble(key);
	}

	public double getDouble(String key) {
		Object v = super.get(key);
		if (v == null || v.toString().equals("")) {
			return 0;
		}
		try {
			return Double.parseDouble(v.toString());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public float getFloat(String key) {
		return (float) getDouble(key);
	}

	public String getString(String key) {
		if (this.containsKey(key)) {
			return super.get(key).toString();
		}
		return "";
	}

	public boolean getBoolean(String key) {
		if (this.containsKey(key)) {
			return Boolean.valueOf(super.get(key).toString());
		}
		return false;
	}

	public JSONObject getJSONObject(String key) {
		return (JSONObject) super.get(key);
	}

	public String toString() {
		return JSON.encode(this);
	}
}
