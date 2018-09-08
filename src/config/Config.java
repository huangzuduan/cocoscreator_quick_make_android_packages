package config;

import config.XMLManger;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import json.JSONObject;
import org.xml.sax.SAXException;

public class Config {

	private static HashMap<String, String> configData;
	public static String GetConfig() {
		JSONObject json = new JSONObject();
		Set<String> keys = configData.keySet();
		Iterator<String> var3 = keys.iterator();
		while (var3.hasNext()) {
			String key = (String) var3.next();
			json.put(key, configData.get(key).toString());
		}
		return json.toString();
	}

	public static void init(String configFile)
			throws ParserConfigurationException, IOException, SAXException {
		String xmlFile = configFile;
		configData = XMLManger.readXml(xmlFile);
	}

	public static Object get(String key) {
		Object o = configData.get(key);
		return o == null ? null : o;
	}

	public static int getInt(String key) {
		return Integer.parseInt(configData.get(key).toString());
	}

	public static String getString(String key) {
		return configData.get(key).toString();
	}

	public static Float getFloat(String key) {
		return Float.valueOf(Float.parseFloat(configData.get(key).toString()));
	}

	public static Double getDouble(String key) {
		return Double.valueOf(Double
				.parseDouble(configData.get(key).toString()));
	}

	public static Boolean getBoolean(String key) {
		Object o = configData.get(key);
		return o == null ? Boolean.valueOf(false) : Boolean.valueOf(Boolean
				.parseBoolean(configData.get(key).toString()));
	}
}