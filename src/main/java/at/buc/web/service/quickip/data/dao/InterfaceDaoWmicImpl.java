package at.buc.web.service.quickip.data.dao;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.net.util.SubnetUtils;
import org.apache.commons.net.util.SubnetUtils.SubnetInfo;
import org.apache.log4j.Logger;

import at.buc.framework.utils.ObjectManipulation;
import at.buc.framework.web.service.application_wrapper.ApplicationWrapperRestClient;
import at.buc.web.service.quickip.data.AbstractEntry;
import at.buc.web.service.quickip.data.InterfaceEntry;
import at.buc.web.service.quickip.data.utils.WmicUtils;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;
import nu.xom.Nodes;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

/**
 *
 * Known Issues:
 * - WHERE-Clause currently only supports integer (or maybe
 * numeric) values
 */
public class InterfaceDaoWmicImpl implements EntryDao {

	private final Logger logger = Logger.getLogger(InterfaceDaoWmicImpl.class);

	private static final String cmdTmpl_get = "WMIC PATH %s WHERE %s GET %s /FORMAT:rawxml";
	private static final String cmdTmpl_getall = "WMIC PATH %s GET %s /FORMAT:rawxml";

	private static final String class_networkadapter = "Win32_NetworkAdapter";
	private static final String params_networkadapter = "AdapterType,AdapterTypeId,Availability,Description,GUID,Index,MACAddress,Name,NetConnectionID,NetConnectionStatus,NetEnabled,NetConnectionStatus,PhysicalAdapter";

	private static final String class_networkadapterconfiguration = "Win32_NetworkAdapterConfiguration";
	private static final String params_networkadapterconfiguration = "DHCPEnabled,DHCPLeaseObtained,DHCPServer,DNSServerSearchOrder,DNSDomain,DefaultIPGateway,Description,IPAddress,IPSubnet,Index,MACAddress,NumForwardPackets";

	private final Map<String, Integer> indexCache = new HashMap<String, Integer>();

	public InterfaceDaoWmicImpl() {
		indexCache.put("Wi-Fi", 1);
		indexCache.put("Ethernet", 2);
		indexCache.put("Ethernet 2", 3);
		indexCache.put("Bluetooth Network Connection", 5);
		indexCache.put("vEthernet-int", 10);
		indexCache.put("wlan_bridge", 12);
		indexCache.put("vEthernet-ext", 13);
	}

	@Override
	public InterfaceEntry getEntryByName(String name) {
		int index = indexCache.get(name);
		Map<String, String> adapter = WmicUtils.getPropertiesFromCmd(String.format(cmdTmpl_get, class_networkadapter, "Index=" + index, params_networkadapter));
		Map<String, String> config = WmicUtils.getPropertiesFromCmd(String.format(cmdTmpl_get, class_networkadapterconfiguration, "Index=" + index, params_networkadapterconfiguration));

		InterfaceEntry entry = new InterfaceEntry("phy_" + adapter.get("NetConnectionID"), adapter.get("NetConnectionID"), "www", false);

		// ConnectionID
		entry.setConnectionID(adapter.get("NetConnectionID"));
		// ConnectionStatus
		int connectionStatus = Integer.valueOf(adapter.get("NetConnectionStatus"));
		if (connectionStatus == 0 
				| connectionStatus == 1
				| connectionStatus == 3
				| connectionStatus == 7) {
			entry.setConnected(false);
		} else if (connectionStatus == 2) {
			entry.setConnected(true);
		} else {
			logger.info(String.format("Interface: %s is in an unknown state. NetConnectionStatus=%d. See MOF for more details", adapter.get("NetConnectionID"), connectionStatus));
		}
		// DHCP Enabled
		entry.setDhcp_enabled(Boolean.valueOf(config.get("DHCPEnabled")));
		for (String dns : config.get("DNSServerSearchOrder").split("\n")) {
			if (!dns.trim().isEmpty()) {
				entry.addDnsServer(dns);
			}
		}
		// Default Geateway
		for (String gateway : config.get("DefaultIPGateway").split("\n")) {
			if (!gateway.trim().isEmpty() && gateway.matches("\\d\\d?\\d?\\.\\d\\d?\\d?\\.\\d\\d?\\d?\\.\\d\\d?\\d?")) {
				entry.setGateway(gateway);
			}
		}
		// Interface Enabled
		entry.setIface_enabled(Boolean.valueOf(adapter.get("NetEnabled")));
		// IP Address (IPv4 only)
		for (String ip : config.get("IPAddress").split("\n")) {
			if (!ip.trim().isEmpty() && ip.matches("\\d\\d?\\d?\\.\\d\\d?\\d?\\.\\d\\d?\\d?\\.\\d\\d?\\d?")) {
				entry.setIp(ip);
			}
		}
		//Subnet mask
		for (String mask : config.get("IPSubnet").split("\n")) {
			if (!mask.trim().isEmpty() && mask.matches("\\d\\d?\\d?\\.\\d\\d?\\d?\\.\\d\\d?\\d?\\.\\d\\d?\\d?")) {
				try {
					InetAddress netmask = InetAddress.getByName(mask);
					entry.setMask(convertNetmaskToCIDR(netmask));
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		return entry;
	}

	@Override
	public Collection<AbstractEntry> getAllEntries() {
		ArrayList<AbstractEntry> toReturn = new ArrayList<>();

		// Map<String, String>
		// values=AbastractWmiUtis.getPropertiesFromCmd(String.format(cmdTmpl_get,
		// class_networkadapter, params_networkadapter));

		// toReturn.add(new InterfaceEntry("phy_wlan", "wlan", "10.0.0.2", 24,
		// true, true, "10.0.0.254", "8.8.8.8,8.8.4.4".split(","), "action",
		// false));
		// toReturn.add(new InterfaceEntry("phy_lan", "lan", "10.0.0.1", 24,
		// true, false, "10.0.0.254", "8.8.8.8,8.8.4.4".split(","), "action",
		// false));
		// toReturn.add(new InterfaceEntry("phy_vmnet", "vmnetX", "192.168.1.1",
		// 16, false, false, "10.0.0.254", "8.8.8.8,8.8.4.4".split(","),
		// "action", false));
		// toReturn.add(new InterfaceEntry("tmpl_dhcp", "lan", "", 16, true,
		// true, "", "".split(","), "action", true));
		// toReturn.add(new InterfaceEntry("tmpl_proj", "lan", "192.168.2.2",
		// 24, false, true, "192.168.2.254", "8.8.8.8,8.8.4.4".split(","),
		// "action", true));
		return toReturn;
	}

	@Override
	public Map<String, Object> asMap(AbstractEntry entry) {
		return ObjectManipulation.asMap(entry, InterfaceEntry.class);
	}

	@Override
	public void updateEntry(AbstractEntry updatedIface) {
		// TODO Auto-generated method stub

	}

	public static int convertNetmaskToCIDR(InetAddress netmask){

        byte[] netmaskBytes = netmask.getAddress();
        int cidr = 0;
        boolean zero = false;
        for(byte b : netmaskBytes){
            int mask = 0x80;

            for(int i = 0; i < 8; i++){
                int result = b & mask;
                if(result == 0){
                    zero = true;
                }else if(zero){
                    throw new IllegalArgumentException("Invalid netmask.");
                } else {
                    cidr++;
                }
                mask >>>= 1;
            }
        }
        return cidr;
    }
	
}
