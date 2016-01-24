package at.buc.web.service.quickip.data.dao;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import at.buc.framework.utils.ObjectManipulation;
import at.buc.web.service.quickip.data.AbstractEntry;
import at.buc.web.service.quickip.data.InterfaceEntry;
import at.buc.web.service.quickip.data.utils.WmicUtils;

/**
 *
 * Known Issues: - WHERE-Clause currently only supports integer (or maybe
 * numeric) values
 */
public class InterfaceDaoWmicImpl implements EntryDao {

	private final Logger logger = Logger.getLogger(InterfaceDaoWmicImpl.class);

	private static final String cmdTmpl_get = "WMIC PATH %s WHERE \"%s\" GET %s /FORMAT:csv";

	private static final String class_networkadapter = "Win32_NetworkAdapter";
	private static final String params_networkadapter = "Availability,Description,GUID,Index,MACAddress,Manufacturer,Name,NetConnectionID,NetConnectionStatus,NetEnabled";

	private static final String class_networkadapterconfiguration = "Win32_NetworkAdapterConfiguration";
	private static final String params_networkadapterconfiguration = "DHCPEnabled,DHCPLeaseObtained,DHCPServer,DNSServerSearchOrder,DNSDomain,DefaultIPGateway,Description,IPAddress,IPSubnet,Index,MACAddress,NumForwardPackets";

	private final Map<String, Integer> indexCache = new HashMap<String, Integer>();

	public InterfaceDaoWmicImpl() {
		indexCache.put("Wi-Fi", 2);
		indexCache.put("Ethernet", 3);
		indexCache.put("Ethernet 2", 1);
		indexCache.put("Bluetooth Network Connection", 6);
		indexCache.put("vEthernet-int", 11);
		indexCache.put("wlan_bridge", 4);
		indexCache.put("vEthernet-ext", 10);
	}

	@Override
	public InterfaceEntry getEntryByName(String name) {
		int index = indexCache.get(name);
		Map<String, String> adapter = WmicUtils.getSingleInstanceProperties(String.format(cmdTmpl_get, class_networkadapter, "Index=" + index, params_networkadapter));
		Map<String, String> config = WmicUtils.getSingleInstanceProperties(String.format(cmdTmpl_get, class_networkadapterconfiguration, "Index=" + index, params_networkadapterconfiguration));

		return constructFromWmi(adapter, config);
	}

	@Override
	public Collection<AbstractEntry> getAllEntries() {
		ArrayList<AbstractEntry> toReturn = new ArrayList<>();
		TreeSet<Map<String, String>> adapters = new TreeSet<Map<String, String>>(new ListOfMapsComperator("Index"));
		TreeSet<Map<String, String>> configs = new TreeSet<Map<String, String>>(new ListOfMapsComperator("Index"));
		adapters.addAll(WmicUtils.getMultipleInstanceProperties(String.format(cmdTmpl_get, class_networkadapter, "NetConnectionID != null", params_networkadapter)));
		configs.addAll(WmicUtils.getMultipleInstanceProperties(String.format(cmdTmpl_get, class_networkadapterconfiguration, "Index != null", params_networkadapterconfiguration)));
		configs.retainAll(adapters);

		if (adapters.size() != configs.size()) {
			logger.fatal(String.format("Query for Adapter and AdapterConfig returned Arrays of diffrent size! Adapter=%d AdapterConfig=%d", adapters.size(), configs.size()));
			throw new IllegalStateException(String.format("Query for Adapter and AdapterConfig returned Arrays of diffrent size! Adapter=%d AdapterConfig=%d", adapters.size(), configs.size()));
		}
		while (!adapters.isEmpty()) {
			toReturn.add(constructFromWmi(adapters.pollFirst(), configs.pollFirst()));
		}
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

	public static int convertNetmaskToCIDR(InetAddress netmask) {

		byte[] netmaskBytes = netmask.getAddress();
		int cidr = 0;
		boolean zero = false;
		for (byte b : netmaskBytes) {
			int mask = 0x80;

			for (int i = 0; i < 8; i++) {
				int result = b & mask;
				if (result == 0) {
					zero = true;
				} else if (zero) {
					throw new IllegalArgumentException("Invalid netmask.");
				} else {
					cidr++;
				}
				mask >>>= 1;
			}
		}
		return cidr;
	}

	public InterfaceEntry constructFromWmi(Map<String, String> adapter, Map<String, String> config) {
		InterfaceEntry entry = new InterfaceEntry("phy_" + adapter.get("NetConnectionID"), adapter.get("NetConnectionID"), false);

		// ConnectionID
		entry.setConnectionID(adapter.get("NetConnectionID"));
		// ConnectionStatus
		int connectionStatus = Integer.valueOf(adapter.get("NetConnectionStatus"));
		if (connectionStatus == 0 | connectionStatus == 1 | connectionStatus == 3 | connectionStatus == 7) {
			entry.setConnected(false);
		} else if (connectionStatus == 2) {
			entry.setConnected(true);
		} else {
			logger.info(String.format("Interface: %s is in an unknown state. NetConnectionStatus=%d. See MOF for more details", adapter.get("NetConnectionID"), connectionStatus));
		}
		// DHCP Enabled
		entry.setDhcp_enabled(Boolean.valueOf(config.get("DHCPEnabled")));
		String config_dns = config.get("DNSServerSearchOrder");
		if (config_dns.length() > 0) {
			for (String dns : config.get("DNSServerSearchOrder").substring(1,  config_dns.length()-1).split(";")) {
				if (!dns.trim().isEmpty()) {
					entry.addDnsServer(dns);
				}
			}
		}
		// Default Geateway
		String config_gateway = config.get("DefaultIPGateway");
		if (config_gateway.length() > 0) {
			for (String gateway : config_gateway.substring(1, config_gateway.length() - 1).split(";")) {
				if (!gateway.trim().isEmpty() && gateway.matches("\\d\\d?\\d?\\.\\d\\d?\\d?\\.\\d\\d?\\d?\\.\\d\\d?\\d?")) {
					entry.setGateway(gateway);
				}
			}
		}
		// Interface Enabled
		entry.setIface_enabled(Boolean.valueOf(adapter.get("NetEnabled")));
		// IP Address (IPv4 only)
		String config_ip = config.get("IPAddress");
		if (config_ip.length() > 0) {
			for (String ip : config_ip.substring(1, config_ip.length() - 1).split(";")) {
				if (!ip.trim().isEmpty() && ip.matches("\\d\\d?\\d?\\.\\d\\d?\\d?\\.\\d\\d?\\d?\\.\\d\\d?\\d?")) {
					entry.setIp(ip);
				}
			}
		}
		// Subnet mask
		String config_mask = config.get("IPSubnet");
		if (config_ip.length() > 0) {
			for (String mask : config_mask.substring(1, config_mask.length() - 1).split(";")) {
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
		}
		return entry;
	}

	private class ListOfMapsComperator implements Comparator<Map<String, String>> {
		private String key;
		public ListOfMapsComperator(String key) {
			this.key = key;
		}

		@Override
		public int compare(Map<String, String> o1, Map<String, String> o2) {
			String key1 = o1.get(key);
			String key2 = o2.get(key);
			return key1.compareTo(key2);
		}

	}
}
