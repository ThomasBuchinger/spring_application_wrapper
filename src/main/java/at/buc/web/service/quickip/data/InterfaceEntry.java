package at.buc.web.service.quickip.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * This class holds an interface configuration. The configuration can be 
 * real (physical interface) or a template which can be applied to a physical interface 
*/
public class InterfaceEntry extends AbstractEntry{
	
	
	//editable parameter
	private String connectionID;
	private String ip;
	private int mask;
	private boolean iface_enabled;
	private boolean dhcp_enabled;
	private boolean connected;
	
	private String gateway;
	private List<String> dnsServer;
	
	
	public InterfaceEntry(String name, String NetConnectionID, String ip, int mask, boolean active, boolean dhcp_enabled, String gateway, String[] dnsserver, String action, boolean isTemplate) {
		super(name, action, isTemplate);
		this.connectionID=NetConnectionID;
		this.ip=ip;
		this.mask=mask;
		this.iface_enabled=active;
		this.dhcp_enabled=dhcp_enabled;
		
		this.gateway=gateway;
		this.dnsServer=Arrays.asList(dnsserver);
	}
	public InterfaceEntry(String name, String NetConnectionID, String action, boolean isTemplate) {
		super(name, action, isTemplate);
		this.connectionID=NetConnectionID;
		dnsServer=new ArrayList<>();
	}

	public void addDnsServer(String dns){
		dnsServer.add(dns);
	}
	
//	===== GETTERS & SETTERS ======================================
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public boolean isTemplate() {
		return isTemplate;
	}


	public void setTemplate(boolean isTemplate) {
		this.isTemplate = isTemplate;
	}


	public String getConnectionID() {
		return connectionID;
	}


	public void setConnectionID(String connectionID) {
		this.connectionID = connectionID;
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


	public boolean isIface_enabled() {
		return iface_enabled;
	}


	public void setIface_enabled(boolean iface_enabled) {
		this.iface_enabled = iface_enabled;
	}


	public boolean isDhcp_enabled() {
		return dhcp_enabled;
	}


	public void setDhcp_enabled(boolean dhcp_enabled) {
		this.dhcp_enabled = dhcp_enabled;
	}


	public boolean isConnected() {
		return connected;
	}


	public void setConnected(boolean connected) {
		this.connected = connected;
	}


	public String getUuid() {
		return uuid;
	}


	public String getAction() {
		return action;
	}


	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String[] getDnsServer() {
		return (String[]) dnsServer.toArray();
	}




	public void setDnsServer(String[] dnsServer) {
		this.dnsServer = Arrays.asList(dnsServer);
	}
	

}
