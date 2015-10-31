package at.buc.utils;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

	private String name;
	private TreeNode parent;
	private List<TreeNode> children;

	public TreeNode(String name, List<TreeNode> children) {
		this.name = name;
		this.children = children;
		for (TreeNode child : children) {
			child.parent = this;
		}
	}

	public TreeNode(String name) {
		this.name = name;
		children=new ArrayList<TreeNode>();
	}
	public String getName() {
		return name;
	}
	public TreeNode getParent() {
		return parent;
	}
	public List<TreeNode> getChildren() {
		return children;
	}
	
	public void setParent(TreeNode parent) {
		this.parent = parent;
	}
	
	public TreeNode addNode(TreeNode toAdd) {
		children.add(toAdd);
		toAdd.setParent(this);
		return this;
	}
	public void print(int i) {
		for (int j = 0; j < i; j++) {
			System.out.print("   ");
		}
		System.out.println(String.format("Name: %s | Parent: %s | Children: %s", name, parent, children));
		i++;
		for (TreeNode child : children) {
			child.print(i);
		}
	}
	@Override
	public String toString() {
		return "Node:"+name;
	}
}