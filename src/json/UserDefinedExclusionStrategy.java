package json;

import java.util.HashSet;

import com.google.gson.FieldAttributes;

/**
 * 自定义过滤字段
 * 
 * @author Rain
 * 
 */
public class UserDefinedExclusionStrategy extends DSuperExclusionStrategy {

	private HashSet<String> fieldAttributes;
	private boolean include = false;

	public UserDefinedExclusionStrategy(HashSet<String> fieldAttributes,
			boolean include) {
		super();
		this.fieldAttributes = fieldAttributes;
		this.include = include;
	}

	public boolean shouldSkipField(FieldAttributes arg0) {
		if (super.shouldSkipField(arg0)) {
			return true;
		} else {
			String fieldName = arg0.getName();
			String[] arr = arg0.getDeclaringClass().getName().split("\\.");
			String className = arr[arr.length - 1];

			if (!include) {
				if (fieldAttributes.contains(fieldName)) {
					// 排除的
					return true;
				}
				if (fieldAttributes.contains(className + "." + fieldName)) {
					// 排除的
					return true;
				}

				return false;
			} else {
				if (fieldAttributes.contains(fieldName)) {
					// 包含的
					return false;
				}
				if (fieldAttributes.contains(className + "." + fieldName)) {
					// 包含的
					return false;
				}

				return true;
			}
		}
	}

}
