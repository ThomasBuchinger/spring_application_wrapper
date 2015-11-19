package at.buc.web.service.quickip.data;

import java.util.HashMap;

public class RouteEntry {
	private final String network;
	private final int mask;
	private final String gateway;
	private final boolean active;
	private final int metric;
	private final int preference;
	private final String action;
	
	public RouteEntry(String network, int mask, String gateway, boolean active, int metric, int preference, String action) {
		this.network=network;
		this.mask=mask;
		this.gateway=gateway;
		this.active=active;
		this.metric=metric;
		this.preference=preference;
		this.action=action;
	}
	public HashMap<String, Object> asMap(){
		HashMap<String, Object> ret=new HashMap<>();
		ret.put("network", network);
		ret.put("mask", String.valueOf(mask));
		ret.put("active", String.valueOf(active));
		ret.put("gateway", gateway);
		ret.put("metric", String.valueOf(metric));
		ret.put("preference", String.valueOf(preference));
		ret.put("action", action);
		return ret;
	}
	public String getNetwork() {
		return network;
	}
	public int getMask() {
		return mask;
	}
	public String getGateway() {
		return gateway;
	}
	public boolean isActive() {
		return active;
	}
	public String getAction() {
		return action;
	}
	
}
