package at.buc.web.service.quickip;

public class InterfaceEntry {
	private String name;
	private String ip;
	private int mask;
	private boolean active;
	private boolean connected;
	
	private String gateway;
	private String [] dnsServer;
	private String action;
	
	public InterfaceEntry(String name, String ip, int mask, boolean active, boolean connected, String gateway, String[] dnsserver) {
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

	public void setName(String name) {
		this.name = name;
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

	public void setConnected(boolean connected) {
		this.connected = connected;
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

}
