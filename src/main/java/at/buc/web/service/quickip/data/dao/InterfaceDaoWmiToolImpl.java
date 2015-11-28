package at.buc.web.service.quickip.data.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import at.buc.framework.utils.ObjectManipulation;
import at.buc.framework.web.service.application_wrapper.ApplicationWrapperRestClient;
import at.buc.web.service.quickip.data.InterfaceEntry;

/*=============================================================================
 * BROKEN
 *=============================================================================
 * This class is broken and will be replaced by InterfaceDaoWmicImpl.
 * This class will be removed as soon as InterfaceDaoWmicImpl is working
 * ============================================================================
*/

/*
public class InterfaceDaoWmiToolImpl implements EntryDao{
	public static final String binPath="src/main/resources/bin/";
	
	private static final Logger logger=LoggerFactory.getLogger(InterfaceDaoWmiToolImpl.class);
	
	private Map<String, WmiToolInterface> setFields(String output) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, RuntimeException {
		Map<String, WmiToolInterface> ifaces = new HashMap<>();
		Class<WmiToolInterface> ifaceClass = WmiToolInterface.class;
		WmiToolInterface iface = new WmiToolInterface();
		String[] lines = output.split("\n");
		String line=null;
		int entry_num = 1;

		logger.trace(String.format("Creating new WmiToolInterface object: %s", iface.toString()));
		for (int i=0; i<lines.length; i++) {
			line=lines[i];
			if (line.trim().isEmpty()) {
				continue;
			}
			
			if (line.startsWith(entry_num + ".")) {
				line=line.replaceFirst("\\d+\\.", "");
				String[] kvPair = line.split("=");

				if (kvPair.length == 2) {
					kvPair[0]=kvPair[0].trim();
					kvPair[1]=kvPair[1].trim();
					
					logger.trace(String.format("Setting field in WmiToolInterface object: %s=%s, object=%s", kvPair[0], kvPair[1], iface.toString()));
					Field field=ifaceClass.getField(kvPair[0]);
					field.set(iface, kvPair[1]);
				} else {
					logger.trace(String.format("Invalid key-value pair: length=%d, key=%s", kvPair.length, kvPair[0]));
					throw new RuntimeException();
				}
			} else {
				//started with a new Interface
//				logger.warn(String.format("DEBUG: i=%d, entry=%d line=%s Iface=%s", i, entry_num, line, iface.toString()));
				entry_num++;
				ifaces.put(iface.Index, iface);
				iface=new WmiToolInterface();
				logger.trace(String.format("Creating new WmiToolInterface object: %s", iface.toString()));
				i--;
			}			
		}
		ifaces.put(iface.Index, iface);
		logger.warn(String.format("Interface Discovery finished: count=%d", entry_num));

		return ifaces;
	}

	@Override
	public InterfaceEntry getEntryByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<InterfaceEntry> getAllInterfaces() {
		String cmdAdapter = binPath+"WmiTool_x64.exe --sleep 0 --ping none --class Win32_NetworkAdapter  --where \"NetConnectionID IS NOT NULL\" --param AdapterType,AdapterTypeId,Availability,Description,GUID,Index,MACAddress,Name,NetConnectionID,NetConnectionStatus,NetEnabled,NetConnectionStatus,PhysicalAdapter";
		String cmdTmplAdapterConfig = binPath+"WmiTool_x64.exe --sleep 0 --ping none --class Win32_NetworkAdapterConfiguration --where \"%s\" --param DHCPEnabled,DHCPLeaseObtained,DHCPServer,DNSServerSearchOrder,DNSDomain,DefaultIPGateway,Description,IPAddress,IPSubnet,Index,MACAddress,NumForwardPackets";
		
		Map<String, Object> ret_adapter = ApplicationWrapperRestClient.simple_call(cmdAdapter);
		Map<String, WmiToolInterface> adapterList = null;
		Map<String, WmiToolInterface> adapterConfig = null;
		try {
			adapterList = setFields((String) ret_adapter.get("stdout"));
			StringBuilder sb=new StringBuilder();
			for (String key : adapterList.keySet()) {
				sb.append("Index="+key+ " OR ");
			}
			Map<String, Object> ret_adapterConfig = ApplicationWrapperRestClient.simple_call(String.format(cmdTmplAdapterConfig, sb.toString().substring(0, sb.toString().length()-4)));
			adapterConfig=setFields((String) ret_adapterConfig.get("stdout"));
			
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<InterfaceEntry> ret=new ArrayList<>();
		for (WmiToolInterface iface: adapterList.values()) {
		}
		return ret;
	}

	@Override
	public void updateInterface(InterfaceEntry updatedIface) {
		
	}

	@Override
	public Map<String, Object> asMap(InterfaceEntry entry) {
		return ObjectManipulation.asMap(entry, WmiToolInterface.class);
	}

	
	private class WmiToolInterface{
		//Win32_NetworkAdapter Properties
		public String AdapterType;
		public String AdapterTypeId;
		public String Availability;
		public String Description;
		public String GUID;
		public String Index;
		public String MACAddress;
		public String Name;
		public String NetConnectionID;
		public String NetConnectionStatus;
		public String NetEnabled;
		public String PhysicalAdapter;
		
		//Win32_NetworkAdapterConfiguration Properties
		public String DHCPEnabled;
		public String DHCPLeaseObtained;
		public String DHCPServer;
		public String DNSServerSearchOrder;
		public String DNSDomain;
		public String DefaultIPGateway;
		public String IPAddress;
		public String IPSubnet;
		public String NumForwardPackets;
		
	}
}
*/