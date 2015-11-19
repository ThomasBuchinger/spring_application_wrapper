package at.buc.web.service.quickip.data.dao;

import java.util.ArrayList;
import java.util.Map;

import at.buc.web.service.quickip.data.InterfaceEntry;

public class WmiInterfaceDaoImpl extends AbstractInterfaceDaoImpl {

	private ArrayList<Map<String, String>> convertWmiToolOutput(String stdout){
		
		return null;
	}
	
	@Override
	public InterfaceEntry getEntryByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<InterfaceEntry> getAllInterfaces() {
		
		String cmdAdapterConfig=binPath+"WmiTool_x64.exe --class Win32_NetworkadAdapterConfiguration";
		Map<String, Object> ret=(Map<String, Object>) callExecService(cmdAdapterConfig);
		
		return null;
	}

	@Override
	public void updateInterface(InterfaceEntry iface) {
		
	}

}
