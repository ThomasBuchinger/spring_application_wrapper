package at.buc.web.service.quickip.data.dao;

import java.util.Collection;
import java.util.Map;

import at.buc.framework.utils.ObjectManipulation;
import at.buc.web.service.quickip.data.AbstractEntry;

public class VirtualEntryDaoImpl implements EntryDao{

	@Override
	public Map<String, Object> asMap(AbstractEntry entry) {
		return ObjectManipulation.asMap(entry, entry.getClass());
	}

	@Override
	public Collection<AbstractEntry> getAllEntries() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractEntry getEntryByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateEntry(AbstractEntry updatedEntry) {
		// TODO Auto-generated method stub
		
	}

}
