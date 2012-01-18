package com.utils.xml;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
// 表示person是一个根元素
class Person {
	@XmlElement
	Calendar birthDay; // birthday将作为person的子元素

	@XmlAttribute
	String name; // name将作为person的的一个属性

	public Address getAddress() {
		return address;
	}

	@XmlElement
	Address address; // address将作为person的子元素

	@XmlElement
	Gender gender; // gender将作为person的子元素

	@XmlElement
	String job; // job将作为person的子元素

	public Person() {
	}

	public Person(Calendar birthDay, String name, Address address,
			Gender gender, String job) {
		this.birthDay = birthDay;
		this.name = name;
		this.address = address;
		this.gender = gender;
		this.job = job;
	}
}
