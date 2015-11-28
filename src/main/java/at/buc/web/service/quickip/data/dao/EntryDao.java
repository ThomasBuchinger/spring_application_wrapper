package at.buc.web.service.quickip.data.dao;

import java.util.Collection;
import java.util.Map;

import at.buc.web.service.quickip.data.AbstractEntry;
import at.buc.web.service.quickip.data.InterfaceEntry;

public interface EntryDao {
	public Map<String, Object> asMap (AbstractEntry entry);
	public Collection<AbstractEntry> getAllEntries();
	
	public AbstractEntry getEntryByName(String name);
	public void updateEntry (AbstractEntry updatedEntry);
	
}
