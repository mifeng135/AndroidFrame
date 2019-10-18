package com.mifeng.mf.MFNavigation.Utils;

import org.json.JSONObject;

import java.util.List;

public class LayoutNode {
	public enum Type {
		Stack,
		BottomTabs,
		Component,
		UIVIEWCONTROLLER
	}

	public final String id;
	public final Type type;
	public final JSONObject data;

	public final List<LayoutNode> children;

	public LayoutNode(String id, Type type, JSONObject data, List<LayoutNode> children) {
		this.id = id;
		this.type = type;
		this.data = data;
		this.children = children;
	}
}
