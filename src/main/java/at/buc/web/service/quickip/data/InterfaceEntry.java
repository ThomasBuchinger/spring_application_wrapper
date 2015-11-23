package at.buc.web.service.quickip.data;

public class InterfaceEntry {
	
	public String name;
	public String ip;
	public int mask;
	public boolean active;
	public boolean dhcp_enabled;
	public boolean connected;
	
	public String gateway;
	public String [] dnsServer;
	public String action;
	
	
	public InterfaceEntry(String name, String ip, int mask, boolean dhcp_enabled, boolean active, boolean connected, String gateway, String[] dnsserver, String action) {
		this.name=name;
		this.ip=ip;
		this.mask=mask;
		this.active=active;
		this.connected=connected;
		
		this.gateway=gateway;
		this.dnsServer=dnsserver;
		this.action=action;
	}
	
	public String getName() {
		return name;
	}


	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getMask() {
		return mask;
	}

	public void setMask(int mask) {
		this.mask = mask;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isConnected() {
		return connected;
	}


	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	public void setDnsServer(String[] dnsServer) {
		this.dnsServer = dnsServer;
	}
	public String getDnsServer() {
		return String.join(", ", dnsServer);
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public boolean isDhcp_enabled() {
		return dhcp_enabled;
	}

	public void setDhcp_enabled(boolean dhcp_enabled) {
		this.dhcp_enabled = dhcp_enabled;
	}
}
