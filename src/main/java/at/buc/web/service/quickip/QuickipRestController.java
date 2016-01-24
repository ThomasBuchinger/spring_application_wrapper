package at.buc.web.service.quickip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import at.buc.framework.web.service.ui.editable_grid.Grid;
import at.buc.framework.web.service.ui.editable_grid.GridColumn;
import at.buc.framework.web.service.ui.editable_grid.GridData;
import at.buc.web.service.quickip.data.AbstractEntry;
import at.buc.web.service.quickip.data.InterfaceEntry;
import at.buc.web.service.quickip.data.RouteEntry;
import at.buc.web.service.quickip.data.dao.EntryDao;
import at.buc.web.service.quickip.data.dao.InterfaceDaoWmicImpl;

@RestController
public class QuickipRestController {

	QuickipService service=new QuickipService();
	
	@RequestMapping("/api/quickip")
	public Object get() {
		return "unfinished - Here should be an HATEOS Endpoint Description pointing to everything that is available as part of Quick IP";
	}
	
	@RequestMapping(value="/api/quickip/iface/{name}", method=RequestMethod.POST, params="ip")
	public Object addStaticInterfaceConfig(
			@RequestParam(value="name", required=true)String name, 
			@RequestParam(value="interface", required=true) String iface,
			@RequestParam(value="ip", required=true) String ip,
			@RequestParam(value="subnetmask", required=true) int mask,
			@RequestParam(value="gateway", required=false, defaultValue = "") String gateway,
			@RequestParam(value="dns", required=false) String [] dns,
			@RequestParam(value="enabled", required=false) boolean iface_enabled) {
//		ifaces.add(new InterfaceEntry("phy_lan", "Ethernet", "", 24, false, false, "", "".split(","), "/api/quickip/phy_lan", false));
		if (dns ==null) {
			dns=new String[0];
		}
		service.addInterfaceConfig(new InterfaceEntry(name, iface, ip, mask, false, false, gateway, dns, true));
		return "static";
	}
	@RequestMapping(value="/api/quickip/iface/{name}", method=RequestMethod.POST, params="dhcp=true")
	public Object addDhcpInterfaceConfig(
			@RequestParam(value="name", required=true)String name, 
			@RequestParam(value="interface", required=true) String iface,
			@RequestParam(value="dhcp", required=true) boolean dhcp_enabled,
			@RequestParam(value="dns", required=false) String [] dns,
			@RequestParam(value="enabled", required=false) boolean iface_enabled) {
		if (dns ==null) {
			dns=new String[0];
		}
		service.addInterfaceConfig(new InterfaceEntry(name, iface, "0.0.0.0", 0, false, true, "0.0.0.0", dns, true));
		
		return "dhcp";
	}
	@RequestMapping(value="/api/quickip/iface/{name}", method=RequestMethod.DELETE)
	public Object deleteInterfaceConfig(@RequestParam(value="name", required=true)String name) {
		
		return null;
	}
	@RequestMapping(value="/api/quickip/iface/{name}", method=RequestMethod.GET)
	public Object getInterfaceConfig(@RequestParam(value="name", required=true)String name) {
		return service.getInterfaceForName(name);
	}
	@RequestMapping(value="/api/quickip/iface", method=RequestMethod.GET)
	public Object getInterfaceConfig() {
		return service.getAllInterfaces();
	}
	
	@RequestMapping("/api/quickip/debug")
	public Object debug_endpoint(@RequestParam(value="ifaces", required=false)String [] iface_names) {
		RestTemplate post_interface=new RestTemplate();
		MultiValueMap<String, String>params =new LinkedMultiValueMap<>();
		params.add("name", "tmpl_static");
		params.add("interface", "lan");
		params.add("ip", "1.2.3.4");
		params.add("subnetmask", "24");
		params.add("gateway", "1.1.1.1");
		params.add("dns", "8.8.8.8,8.8.4.4");
		params.add("enabled", "false");
		System.err.println(post_interface.postForEntity("http://localhost:8080/api/quickip/iface/"+params.getFirst("name"), params, String.class));
		params.clear();
		params.add("name", "tmpl_dhcp");
		params.add("interface", "wlan");
		params.add("dhcp", "true");
		params.add("enabled", "true");
		System.err.println(post_interface.postForEntity("http://localhost:8080/api/quickip/iface/"+params.getFirst("name"), params, String.class));
		
		return post_interface.getForObject("http://localhost:8080/api/quickip/iface", Collection.class);
		//return null;
	}
	
	@RequestMapping("/api/quickip/grid/iface")
	public Object getIfaceTable() {
		ArrayList<GridColumn> metadata=new ArrayList<>();
		metadata.add(new GridColumn("connectionID", "Interface", "string", false));
		metadata.add(new GridColumn("ip", "IP Address", "string", true));
		metadata.add(new GridColumn("mask", "Mask", "integer", true));
		metadata.add(new GridColumn("iface_enabled", "Active", "boolean", true));
		metadata.add(new GridColumn("dhcp_enabled", "DHCP", "boolean", true));
		metadata.add(new GridColumn("connected", "Connected", "boolean", false));
		metadata.add(new GridColumn("gateway", "Gateway", "string", false));
		metadata.add(new GridColumn("dns", "DNS Server", "html", false));
		metadata.add(new GridColumn("isTemplate", "T", "boolean", false));
//		metadata.add(new GridColumn("action", " ", "html", false));
		metadata.add(new GridColumn("name", "ID", "string", true));
		
		String[] dns= {"10.0.0.254", "8.8.8.8", "8.8.4.4"};
		ArrayList<GridData> data=new ArrayList<>();
		EntryDao dao=new InterfaceDaoWmicImpl();
		for (AbstractEntry iface : service.getAllInterfaces()) {
			iface=(InterfaceEntry) iface;
			data.add(new GridData(iface.getUuid(), dao.asMap(iface) ));
		}
		Grid grid=new Grid(metadata, data);
		
		return grid;
	}
	@RequestMapping("/api/quickip/grid/route")
	public Object getRouteTable() {
		ArrayList<GridColumn> metadata=new ArrayList<>();
		metadata.add(new GridColumn("network", "Network", "string", true));
		metadata.add(new GridColumn("mask", "Mask", "integer", true));
		metadata.add(new GridColumn("gateway", "Gateway", "string", true));
		metadata.add(new GridColumn("active", "Active", "boolean", true));
		metadata.add(new GridColumn("metric", "Metric", "integer", false));
		metadata.add(new GridColumn("preference", "pref", "integer", false));
		metadata.add(new GridColumn("action", " ", "html", false));
		
		String[] dns= {"10.0.0.254", "8.8.8.8", "8.8.4.4"};
		ArrayList<GridData> data=new ArrayList<>();
		data.add(new GridData("route_1", new RouteEntry("10.0.0.0", 24, "Wireless LAN", true, 1, 2, "X").asMap()));
		data.add(new GridData("route_2", new RouteEntry("10.0.0.0", 24, "LAN", true, 1, 2, "X").asMap()));
		data.add(new GridData("route_3", new RouteEntry("default", 0, "10.0.0.254", true, 1, 2, "X").asMap()));
		data.add(new GridData("route_new", new RouteEntry(" ", 0, " ", false, 1, 2, "+").asMap()));
		
		Grid grid=new Grid(metadata, data);
		
		return grid;
	}
	
}
