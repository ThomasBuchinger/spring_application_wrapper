package at.buc.framework.web.ui.grid;

import java.util.Map;

public class GridData {
	private final String id;
	private final Map<String, ?> values;
	
	public GridData(String id, Map <String, ?>values) {
		this.id=id;
		this.values=values;
	}

	public String getId() {
		return id;
	}

	public Map<String, ?> getValues() {
		return values;
	}
}
