package at.buc.web.service.quickip;

import java.util.ArrayList;
import java.util.Collection;

import at.buc.web.service.quickip.data.InterfaceEntry;
import at.buc.web.service.quickip.data.dao.EntryDao;
import at.buc.web.service.quickip.data.dao.InterfaceDaoWmicImpl;
import at.buc.web.service.quickip.data.dao.VirtualEntryDaoImpl;

public class QuickipService {
	
	static ArrayList<InterfaceEntry> ifaces=new ArrayList<>();
	EntryDao virtualDao=new VirtualEntryDaoImpl();
	EntryDao interfaceDao=new InterfaceDaoWmicImpl();
	
	public QuickipService() {
		
		
		ifaces.add(new InterfaceEntry("phy_lan", "Ethernet", "", 24, false, false, "", "".split(","), "/api/quickip/phy_lan", false));
		ifaces.add(new InterfaceEntry("phy_wlan", "wlan_bridge", "", 24, false, false, "", "".split(","), "/api/quickip/phy_wlan", false));
		ifaces.add(new InterfaceEntry("tmpl_lan_dhcp", "Ethernet", "", 24, false, true, "", "".split(","), "/api/quickip/tmpl_lan_dhcp", true));
		ifaces.add(new InterfaceEntry("tmpl_lan_proj1", "Ethernet", "192.168.80.1", 24, false, false, "192.168.80.254", "dc1, dc2".split(","), "/api/quickip/tmpl_lan_jroj", true));
		ifaces.add(new InterfaceEntry("tmpl_lan_proj2", "Ethernet", "172.16.32.42", 24, false, false, "192.168.80.254", "dc1, dc2".split(","), "/api/quickip/tmpl_lan_jroj", true));
		ifaces.add(new InterfaceEntry("tmpl_lan_proj3", "Ethernet", "193.8.172.33", 24, false, false, "192.168.80.254", "dc1, dc2".split(","), "/api/quickip/tmpl_lan_jroj", true));
		ifaces.add(new InterfaceEntry("tmpl_bt", "Bluetooth", "192.168.80.1", 24, false, false, "192.168.80.254", "dc1, dc2".split(","), "/api/quickip/tmpl_lan_jroj", true));
		ifaces.add(new InterfaceEntry("tmpl_isdn", "ISDN", "192.168.80.1", 24, false, false, "192.168.80.254", "dc1, dc2".split(","), "/api/quickip/tmpl_lan_jroj", true));
		ifaces.add(new InterfaceEntry("tmpl_usb", "USB", "192.168.80.1", 24, false, false, "192.168.80.254", "dc1, dc2".split(","), "/api/quickip/tmpl_lan_jroj", true));
	}
	
	public Collection<InterfaceEntry> getInterfacesForName(String name){
		ArrayList<InterfaceEntry> result=new ArrayList<>();
		for (InterfaceEntry iface : ifaces) {
			if (iface.getConnectionID().equals(name)) {
				if (!iface.isTemplate()) {
					result.add((InterfaceEntry) interfaceDao.getEntryByName(name));
				}else {
					result.add(iface);
				}
			}
		}
		
		return result;
	}
	
}
