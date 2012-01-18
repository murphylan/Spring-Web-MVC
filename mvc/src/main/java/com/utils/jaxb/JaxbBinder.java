package com.utils.jaxb;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.namespace.QName;

/**
 * 使用Jaxb2.0实现XML<->Java Object的Binder.
 * 
 * 特别支持Root对象是List的情形.
 * 
 * @author sanshang
 */
public class JaxbBinder {
	//多线程安全的Context.
	private JAXBContext jaxbContext;

	/**
	 * @param types 所有需要序列化的Root对象的类型.
	 */
	public JaxbBinder(Class<?>... types) {
		try {
			jaxbContext = JAXBContext.newInstance(types);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Java->Xml.
	 */
	public String toXml(Object root) {
		try {
			StringWriter writer = new StringWriter();
			getMarshaller().marshal(root, writer);
			return writer.toString();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Java->Xml, 特别支持对Root Element是Collection的情形.
	 */
	@SuppressWarnings("rawtypes")
	public String toXml(Collection root, String rootName) {
		try {
			CollectionWrapper wrapper = new CollectionWrapper();
			wrapper.collection = root;

			JAXBElement<CollectionWrapper> wrapperElement = new JAXBElement<CollectionWrapper>(new QName(rootName),
					CollectionWrapper.class, wrapper);

			StringWriter writer = new StringWriter();
			getMarshaller().marshal(wrapperElement, writer);

			return writer.toString();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Xml->Java.
	 */
	@SuppressWarnings("unchecked")
	public <T> T fromXml(String xml) {
		try {
			StringReader reader = new StringReader(xml);
			return (T) getUnmarshaller().unmarshal(reader);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	public Marshaller getMarshaller() {
		try {
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
			return marshaller;
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	public Unmarshaller getUnmarshaller() {
		try {
			return jaxbContext.createUnmarshaller();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 封装Root Element 是 Collection的情况.
	 */
	public static class CollectionWrapper {
		@SuppressWarnings("unchecked")
		@XmlAnyElement
		Collection collection;
	}
}
