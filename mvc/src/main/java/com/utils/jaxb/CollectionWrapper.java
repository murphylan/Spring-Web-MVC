package com.utils.jaxb;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;
//根节点
@XmlRootElement
public class CollectionWrapper {
	Collection<XmlAuthority> authority;
	Collection<XmlRole> role;
	Collection<XmlUser> user;

	public Collection<XmlAuthority> getAuthority() {
		return authority;
	}

	public void setAuthority(Collection<XmlAuthority> authority) {
		this.authority = authority;
	}

	public Collection<XmlRole> getRole() {
		return role;
	}

	public void setRole(Collection<XmlRole> role) {
		this.role = role;
	}

	public Collection<XmlUser> getUser() {
		return user;
	}

	public void setUser(Collection<XmlUser> user) {
		this.user = user;
	}

}
