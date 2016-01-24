package at.buc.framework.web.service.ui.editable_grid;

public class GridColumn {
	private final String name;
	private final String label;
	private final String datatype;
	private final boolean editable;
	
	public GridColumn(String name, String label, String datatype, boolean editable) {
		this.name=name;
		this.label=label;
		this.datatype=datatype;
		this.editable=editable;
	}

	public String getName() {
		return name;
	}

	public String getLabel() {
		return label;
	}

	public String getDatatype() {
		return datatype;
	}

	public boolean isEditable() {
		return editable;
	}
	
}
