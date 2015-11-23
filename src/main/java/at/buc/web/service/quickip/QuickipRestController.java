package at.buc.web.service.quickip;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.buc.framework.utils.ObjectManipulation;
import at.buc.framework.web.service.ui.grid.Grid;
import at.buc.framework.web.service.ui.grid.GridColumn;
import at.buc.framework.web.service.ui.grid.GridData;
import at.buc.web.service.quickip.data.InterfaceEntry;
import at.buc.web.service.quickip.data.RouteEntry;
import at.buc.web.service.quickip.data.dao.InterfaceDao;
import at.buc.web.service.quickip.data.dao.InterfaceDaoWmiToolImpl;
import at.buc.web.service.quickip.data.dao.InterfaceDaoWmicImpl;

@RestController
public class QuickipRestController {

	@RequestMapping("/api/quickip")
	public Object get() {
		return "unfinished - Here should be an HATEOS Endpoint Description pointing to everything that is available as part of Quick IP";
	}
	
	@RequestMapping("/api/quickip/debug")
	public Object debug_endpoint() {
		ArrayList<GridColumn> metadata=new ArrayList<>();
		metadata.add(new GridColumn("NetConnectionID", "Interface", "string", false));
		metadata.add(new GridColumn("IPAddress", "IP Address", "string", true));
		metadata.add(new GridColumn("IPSubnet", "Mask", "integer", true));
		metadata.add(new GridColumn("NetEnabled", "Active", "boolean", true));
		metadata.add(new GridColumn("Availability", "Connected", "boolean", false));
		metadata.add(new GridColumn("IPDefaultGateway", "Gateway", "string", false));
		metadata.add(new GridColumn("DNSName", "DNS Server", "html", false));
//		metadata.add(new GridColumn("action", " ", "html", false));
		
		String[] dns= {"10.0.0.254", "8.8.8.8", "8.8.4.4"};
		ArrayList<GridData> data=new ArrayList<>();
		InterfaceDao ifaceDao=new InterfaceDaoWmiToolImpl();
		for (InterfaceEntry iface : ifaceDao.getAllInterfaces()) {
			data.add(new GridData("iface_1", ifaceDao.asMap(iface)));
			
		}
//		data.add(new GridData("iface_2", ifaceDao.asMap(new InterfaceEntry("LAN", "10.0.0.2", 24, false, false, "10.0.0.254", dns, "X"))));
		Grid grid=new Grid(metadata, data);
		
		return grid;
	}
	
	@RequestMapping("/api/quickip/grid/iface")
	public Object getIfaceTable() {
		ArrayList<GridColumn> metadata=new ArrayList<>();
		metadata.add(new GridColumn("name", "Interface", "string", false));
		metadata.add(new GridColumn("ip", "IP Address", "string", true));
		metadata.add(new GridColumn("mask", "Mask", "integer", true));
		metadata.add(new GridColumn("active", "Active", "boolean", true));
		metadata.add(new GridColumn("connected", "Connected", "boolean", false));
		metadata.add(new GridColumn("gateway", "Gateway", "string", false));
		metadata.add(new GridColumn("dns", "DNS Server", "html", false));
//		metadata.add(new GridColumn("action", " ", "html", false));
		
		String[] dns= {"10.0.0.254", "8.8.8.8", "8.8.4.4"};
		ArrayList<GridData> data=new ArrayList<>();
		InterfaceDao ifaceDao=new InterfaceDaoWmicImpl();
		ifaceDao.getAllInterfaces();
		for (InterfaceEntry iface : ifaceDao.getAllInterfaces()) {
			data.add(new GridData("iface_1", ifaceDao.asMap(iface)));
			
		}
//		data.add(new GridData("iface_2", ifaceDao.asMap(new InterfaceEntry("LAN", "10.0.0.2", 24, false, false, "10.0.0.254", dns, "X"))));
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
