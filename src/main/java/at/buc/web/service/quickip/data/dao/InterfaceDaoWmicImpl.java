package at.buc.web.service.quickip.data.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


import at.buc.framework.utils.ObjectManipulation;
import at.buc.framework.web.service.application_wrapper.ApplicationWrapperRestClient;
import at.buc.web.service.quickip.data.InterfaceEntry;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;
import nu.xom.Nodes;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

public class InterfaceDaoWmicImpl  implements InterfaceDao {

	private static final String cmdTmpl_get = "WMIC PATH %s GET %s /FORMAT:rawxml";
	

	private static final String class_networkadapter="Win32_Networkadapter";
	private static final String params_networkadapter="AdapterType,AdapterTypeId,Availability,Description,GUID,Index,MACAddress,Name,NetConnectionID,NetConnectionStatus,NetEnabled,NetConnectionStatus,PhysicalAdapter";
	
	private static final String class_networkadapterconfiguration="Win32_NetworkadapterConfiguration";
	private static final String params_networkadapterconfiguration="DHCPEnabled,DHCPLeaseObtained,DHCPServer,DNSServerSearchOrder,DNSDomain,DefaultIPGateway,Description,IPAddress,IPSubnet,Index,MACAddress,NumForwardPackets";
	
	@Override
	public InterfaceEntry getEntryByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<InterfaceEntry> getAllInterfaces() {
		ArrayList<InterfaceEntry> toReturn=new ArrayList<>();
		
		Map<String, String> values=AbastractWmiUtis.getPropertiesFromCmd(String.format(cmdTmpl_get, class_networkadapter, params_networkadapter));
		
		toReturn.add(new InterfaceEntry("wlan", "10.0.0.2", 24, false, true, true, "10.0.0.254", "8.8.8.8,8.8.4.4".split(","), "action"));
		toReturn.add(new InterfaceEntry("lan", "10.0.0.1", 24, false, true, false, "10.0.0.254", "8.8.8.8,8.8.4.4".split(","), "action"));
		toReturn.add(new InterfaceEntry("vmnetX", "192.168.1.1", 16, true, false, false, "10.0.0.254", "8.8.8.8,8.8.4.4".split(","), "action"));
		return toReturn;
	}

	@Override
	public Map<String, Object> asMap(InterfaceEntry entry) {
		return ObjectManipulation.asMap(entry, InterfaceEntry.class);
	}

	@Override
	public void updateInterface(InterfaceEntry original, InterfaceEntry update) {
		// TODO Auto-generated method stub

	}

}
