package at.buc.web.service.quickip.data.dao;

import java.util.Collection;
import java.util.Map;

import at.buc.web.service.quickip.data.InterfaceEntry;

public interface InterfaceDao {
	public InterfaceEntry getEntryByName(String name);
	public Collection<InterfaceEntry> getAllInterfaces();
	public Map<String, Object> asMap (InterfaceEntry entry);
	
	public void updateInterface (InterfaceEntry original, InterfaceEntry update);
}
