package at.buc.framework.web.service.ui.grid;

import java.util.ArrayList;

public class Grid {
	private final ArrayList<GridColumn> metadata;
	private final ArrayList<GridData> data;
	public Grid(ArrayList<GridColumn> metadata, ArrayList<GridData> data) {
		this.metadata=metadata;
		this.data=data;
	}
	public ArrayList<GridColumn> getMetadata() {
		return metadata;
	}
	public ArrayList<GridData> getData() {
		return data;
	}
}
