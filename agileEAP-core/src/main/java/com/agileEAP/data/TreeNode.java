package com.agileEAP.data;

import java.util.List;

public class TreeNode {
private String id;
private String text;
private boolean expanded;
private String spriteCssClass;
private boolean hasChildren;
private List<TreeNode> items;
private boolean checked;
private String icon;

public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}
public boolean isExpanded() {
	return expanded;
}
public void setExpanded(boolean expanded) {
	this.expanded = expanded;
}
public String getSpriteCssClass() {
	return spriteCssClass;
}
public void setSpriteCssClass(String spriteCssClass) {
	this.spriteCssClass = spriteCssClass;
}
public List<TreeNode> getItems() {
	return items;
}
public void setItems(List<TreeNode> items) {
	this.items = items;
}
public boolean isHasChildren() {
	return hasChildren;
}
public void setHasChildren(boolean hasChildren) {
	this.hasChildren = hasChildren;
}
public boolean getChecked() {
	return checked;
}
public void setChecked(boolean checkedState) {
	this.checked = checkedState;
}
public String getIcon() {
	return icon;
}
public void setIcon(String icon) {
	this.icon = icon;
}
}
