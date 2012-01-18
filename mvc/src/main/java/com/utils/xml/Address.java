package com.utils.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

class Address {
	@XmlAttribute
	String country;
	@XmlElement
	String state;
	@XmlElement
	String city;
	@XmlElement
	String street;
	String zipcode; // 由于没有添加@XmlElement,所以该元素不会出现在输出的xml中

	public Address() {
	}

	public Address(String country, String state, String city, String street,
			String zipcode) {
		this.country = country;
		this.state = state;
		this.city = city;
		this.street = street;
		this.zipcode = zipcode;
	}

	public String getCountry() {
		return country;
	}
}