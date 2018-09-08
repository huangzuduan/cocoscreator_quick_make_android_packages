package json;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
/**
 * 默认过滤字段
 * @author Rain
 *
 */
public class DSuperExclusionStrategy implements ExclusionStrategy {

	@Override
	public boolean shouldSkipClass(Class<?> arg0) {
		return false;
	}

	@Override
	public boolean shouldSkipField(FieldAttributes arg0) {
		String fieldName = arg0.getName();
		if (fieldName.equals("infoLifeCycle")) {
			return true;
		}
		if (fieldName.equals("infoLastVisit")) {
			return true;
		}
		if (fieldName.equals("infoWriteDbCycle")) {
			return true;
		}
		if (fieldName.equals("infoLastWriteDb")) {
			return true;
		}
		if (fieldName.equals("infoStatus")) {
			return true;
		}
		
		String[] arr = arg0.getDeclaringClass().getName().split("\\.");
		String className = arr[arr.length - 1];
		if (className.equals("ObjectId")) {
			return true;
		}
		
		return false;
	}

}
