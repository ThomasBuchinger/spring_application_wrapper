package at.buc.web.service.quickip.data.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

import at.buc.web.service.quickip.data.InterfaceEntry;

public abstract class AbstractInterfaceDaoImpl implements InterfaceDao {
	protected String binPath="/src/main/resources/bin/";
	
	protected Object callExecService(Object... params ) {
		RestTemplate tmpl=new RestTemplate();
		return tmpl.getForObject("/api/exec", Map.class, params);
	}
	
	@Override
	public HashMap<String, Object> asMap(InterfaceEntry iface){
		HashMap<String, Object> ret=new HashMap<>();
		ret.put("name", iface.getName());
		ret.put("ip", iface.getIp());
		ret.put("mask", String.valueOf(iface.getMask()));
		ret.put("active", String.valueOf(iface.isActive()));
		ret.put("connected", String.valueOf(iface.isConnected()));
		ret.put("gateway", iface.getGateway());
		ret.put("dnsserver", String.join(", ", iface.getDnsServer()));
		ret.put("action", iface.getAction());
		return ret;
	}
}
