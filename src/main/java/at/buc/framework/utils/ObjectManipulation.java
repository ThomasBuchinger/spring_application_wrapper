package at.buc.framework.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ObjectManipulation {

	public static Object updateValues(Object original, Object update, Class<?> c) {
		Field[] fields = c.getFields();
		try {
			for (Field field : fields) {
				Object val = field.get(c.cast(update));
				if (val != null) {
					field.set(original, val);
				}
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return original;
	}

	public static Field[] getAllFields(Class c) {
		List<Field> fields = new ArrayList<Field>();
		fields.addAll(Arrays.asList(c.getDeclaredFields()));
		if (c.getSuperclass() != null) {
			fields.addAll(Arrays.asList(getAllFields(c.getSuperclass())));
		}
		return fields.toArray(new Field[]{});
	}

	public static Map<String, Object> asMap(Object obj, Class<?> c) {
		Field[] fields = getAllFields(c);

		HashMap<String, Object> ret = new HashMap<>();
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				ret.put(field.getName(), field.get(obj));
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

}
