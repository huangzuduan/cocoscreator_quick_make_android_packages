package json;

import java.lang.reflect.Type;
import java.util.HashSet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class JSON {

	private JSON() {
	}

	private static Gson gson;

	/**
	 * 初始化gson
	 */
	private static void init() {
		if (gson != null) {
			return;
		}
		GsonBuilder builder = new GsonBuilder();
		builder.addSerializationExclusionStrategy(new DSuperExclusionStrategy());
		gson = builder.create();
	}

	/**
	 * json解码
	 * 
	 * @param json
	 * @param typeOfT
	 *            类型
	 * @return T
	 * @throws JsonSyntaxException
	 */
	public static <T> T decode(String json, Type typeOfT)
			throws JsonSyntaxException {
		init();
		return gson.fromJson(json, typeOfT);
	}

	/**
	 * 解JSON格式配置用，抛出JSON配置错误
	 * 
	 * @param json
	 * @param typeOfT
	 * @return T
	 * @throws Err
	 * @author Rain 2012-4-26
	 */
	public static <T> T decodeConfig(String json, Type typeOfT)  {
		init();
		try {
			return gson.fromJson(json, typeOfT);
		} catch (JsonSyntaxException e) {
			throw  e;
		}
	}

	/**
	 * json编码
	 * 
	 * @param src
	 * @return String
	 */
	public static String encode(Object src) {
		init();
		return gson.toJson(src);
	}

	/**
	 * json编码
	 * 
	 * @param src
	 * @param fieldAttributes
	 *            字段属性Map
	 * @param include
	 *            是否包含
	 * @return String
	 */
	public static String encode(Object src, HashSet<String> fieldAttributes,
			boolean include) {
		init();
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder
				.addSerializationExclusionStrategy(new UserDefinedExclusionStrategy(
						fieldAttributes, include));
		return gsonBuilder.create().toJson(src);
	}
	
	/**
	 * debug json错误
	 * 
	 * @param json
	 *            json字符串
	 * @param errorAt
	 *            错误的位置(报错的时候报的位置)
	 * @param range
	 *            显示的范围(越大越容易看出来)
	 */
	public static void debugJSONErr(String json, int errorAt, int range) {
		String str = "";
		for (int i = range; i >= 1; i--) {
			str += json.charAt(errorAt - i);
		}
		str += json.charAt(errorAt);
		for (int i = 1; i <= range; i++) {
			str += json.charAt(errorAt + i);
		}
		System.out.println(str);
		System.exit(0);
	}
}
