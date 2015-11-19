package at.buc.web.service.quickip.data.dao;

import java.util.ArrayList;
import java.util.Map;

import at.buc.web.service.quickip.data.InterfaceEntry;

public interface InterfaceDao {
	public InterfaceEntry getEntryByName(String name);
	public ArrayList<InterfaceEntry> getAllInterfaces();
	public Map<String, Object> asMap (InterfaceEntry entry);
	
	public void updateInterface (InterfaceEntry iface);
}
