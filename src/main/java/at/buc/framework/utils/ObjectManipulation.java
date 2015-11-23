package at.buc.framework.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import at.buc.web.service.quickip.data.InterfaceEntry;


public abstract class ObjectManipulation {
		
	public static Object updateValues(Object original, Object update, Class<?> c) {
		Field[] fields=c.getFields();
		try {
			for (Field field : fields) {
				Object val=field.get(c.cast(update));
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

	public static Map<String, Object> asMap(Object obj, Class<?> c){
		Field[] fields=c.getFields();
		HashMap<String, Object> ret=new HashMap<>();
		try {
			for (Field field : fields) {
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
	
//	@Override
//	public HashMap<String, Object> asMap(InterfaceEntry iface){
//		HashMap<String, Object> ret=new HashMap<>();
//		ret.put("name", iface.getName());
//		ret.put("ip", iface.getIp());
//		ret.put("mask", String.valueOf(iface.getMask()));
//		ret.put("active", String.valueOf(iface.isActive()));
//		ret.put("connected", String.valueOf(iface.isConnected()));
//		ret.put("gateway", iface.getGateway());
//		ret.put("dnsserver", String.join(", ", iface.getDnsServer()));
//		ret.put("action", iface.getAction());
//		return ret;
//	}
}
