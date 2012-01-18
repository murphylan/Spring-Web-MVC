package com.utils.transform;

import java.io.FileOutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

public class HowToXSLT {
	public static void main(String[] args) {
		try {
			String path = "src/main/java/com/utils/transform/";
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory
					.newTransformer(new javax.xml.transform.stream.StreamSource(
							path + "howto.xsl"));
			transformer.transform(new javax.xml.transform.stream.StreamSource(
					path + "howto.xml"),
					new javax.xml.transform.stream.StreamResult(
							new FileOutputStream(path + "howto.html")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
