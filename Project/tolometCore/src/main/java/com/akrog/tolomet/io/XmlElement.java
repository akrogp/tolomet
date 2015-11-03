package com.akrog.tolomet.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlElement {
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Map<String, String> getAttributes() {
		return attributes;
	}
	
	public String getAttribute( String name ) {
		return attributes.get(name);
	}
	
	public List<XmlElement> getSubElements() {
		return subElements;
	}

	private String name;
	private final Map<String, String> attributes = new HashMap<String,String>();
	private final List<XmlElement> subElements = new ArrayList<XmlElement>();
}
