package at.buc.framework.web.ui;

import at.buc.framework.utils.TreeNode;

public class NavElement extends TreeNode{
	private String href;
	private String alt;
	private String icon_name;
	
	public NavElement(String name, String href, String alt, String icon_name) {
		super(name);
		this.href=href;
		this.alt=alt;
		this.icon_name=icon_name;
	}
	public NavElement(String name, String href) {
		this(name, href, "", "fa-desktop");
	}
	public String getHref() {
		return href;
	}
	public String getAlt() {
		return alt;
	}
	public String getIcon_name() {
		return icon_name;
	}
	
}
