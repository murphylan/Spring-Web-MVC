package com.utils.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class XmlRole {
	private String name;
	
	public XmlRole(){
		
	}
	
	public XmlRole(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
