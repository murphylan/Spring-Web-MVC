package com.utils.jaxb;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "loginName", "password", "name","email" })
public class XmlUser {
	private String loginName;
	private String password;
	private String name;
	private String email;
	
	public XmlUser(){
		
	}
	
	public XmlUser(String loginName, String pwd, String name, String email){
		this.loginName = loginName;
		this.password = pwd;
		this.name = name;
		this.email = email;
	}
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
