package at.buc.web.service.quickip;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import at.buc.web.service.quickip.data.AbstractEntry;
import at.buc.web.service.quickip.data.InterfaceEntry;
import at.buc.web.service.quickip.data.dao.EntryDao;
import at.buc.web.service.quickip.data.dao.InterfaceDaoWmicImpl;
import at.buc.web.service.quickip.data.dao.VirtualEntryDaoImpl;

public class QuickipService {

	private static Map<String, AbstractEntry> tmpl_config = new TreeMap<>();
	private Map<String, AbstractEntry> hist_config = new TreeMap<>();
	public int history_size = 2;

	private EntryDao virtualDao = new VirtualEntryDaoImpl();
	private EntryDao interfaceDao = new InterfaceDaoWmicImpl();

	public QuickipService() { }

	public void addInterfaceConfig(InterfaceEntry to_add) {
		tmpl_config.put(to_add.getName(), to_add);
	}

	public Collection<AbstractEntry> getPhysicalInterfaceConfig() {
		return (Collection<AbstractEntry>) interfaceDao.getAllEntries();
	}

	public Collection<AbstractEntry> getAllInterfaces() {
		ArrayList<AbstractEntry> re = new ArrayList<>();
		re.addAll(getPhysicalInterfaceConfig());
		re.addAll(hist_config.values());
		re.addAll(tmpl_config.values());
		return re;
	}

	public AbstractEntry getInterfaceForName(String name) {
		AbstractEntry re=null;
		re=tmpl_config.get(name);
		re=hist_config.get(name);
		re=tmpl_config.get(name);
		return re;
	}

}
