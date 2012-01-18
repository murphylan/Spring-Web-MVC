package com.utils.jaxb;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.dom4j.io.XMLResult;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;

import com.google.common.collect.Lists;

public class Jaxb2Test extends Jaxb2RootElementHttpMessageConverter {

	public Object readFromSource(Class<Object> clazz, HttpHeaders headers, Source source) throws IOException {
		return super.readFromSource(clazz, headers, source);
	}
	/**
	 * @param args
	 * @throws IOException
	 * @throws HttpMessageNotWritableException
	 * @throws ClassNotFoundException 
	 * @throws TransformerFactoryConfigurationError 
	 * @throws TransformerConfigurationException 
	 */
	public static void main(String[] args)
			throws HttpMessageNotWritableException, IOException, ClassNotFoundException, TransformerConfigurationException, TransformerFactoryConfigurationError {
		Jaxb2Test jaxb2 = new Jaxb2Test();
		List<XmlAuthority> xmlAuthorityList=Lists.newArrayList();
		XmlAuthority xmlAuthoritya = new XmlAuthority("admin","/www/baidu/com");
		XmlAuthority xmlAuthorityb = new XmlAuthority("custom","/dsd/ssd1/23");
		xmlAuthorityList.add(xmlAuthoritya);
		xmlAuthorityList.add(xmlAuthorityb);
		CollectionWrapper collectionWrapper=new CollectionWrapper();
		collectionWrapper.setAuthority(xmlAuthorityList);
		
		List<XmlRole> roleList = Lists.newArrayList();
		XmlRole role = new XmlRole("zhangssan");
		roleList.add(role);
		collectionWrapper.setRole(roleList);
		
		List<XmlUser> UserList = Lists.newArrayList();
		XmlUser user = new XmlUser("admin","admin","Admin","test@hotmail.com");
		UserList.add(user);
		collectionWrapper.setUser(UserList);
		
		StringWriter writer = new StringWriter();
		javax.xml.transform.Result result = new XMLResult(writer);
		HttpHeaders headers = new HttpHeaders();
		jaxb2.writeToResult(collectionWrapper, headers, result);
		System.out.println(writer.toString());
		
		String path = System.getProperty("user.dir").replace("\\", "/") + "/src/main/resources/";
		Resource resource = new FileSystemResource(path+"initAuthority.xml");
		javax.xml.transform.Source xmlSource =new javax.xml.transform.stream.StreamSource(resource.getInputStream());
		
		StringReader reader = new StringReader(writer.toString());
		Class<Object> clazz= (Class<Object>) Class.forName("com.utils.jaxb.CollectionWrapper");
//		javax.xml.transform.Source xmlSource = new javax.xml.transform.stream.StreamSource(reader);
		CollectionWrapper temp=(CollectionWrapper)jaxb2.readFromSource(clazz, headers, xmlSource);
		for(XmlAuthority o:temp.getAuthority()){
//			System.out.println(o.getName()+"---"+o.getPath());
		}
	}
}
