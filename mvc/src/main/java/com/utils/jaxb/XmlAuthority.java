package com.utils.jaxb;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 使用JAXB2.0标注的待转换Java Bean.
 */
// 根节点
@XmlRootElement
// 指定子节点的顺序
@XmlType(propOrder = { "name", "path", "remark" })
public class XmlAuthority {

	private String name;
	private String path;
	private String remark;

	public XmlAuthority() {
	}

	public XmlAuthority(String name, String path) {
		this.path = path;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
