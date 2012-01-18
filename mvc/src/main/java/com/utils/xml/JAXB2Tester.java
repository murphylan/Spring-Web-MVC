package com.utils.xml;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JAXB2Tester {
    public static void main(String[] args) throws JAXBException,IOException {
        JAXBContext context = JAXBContext.newInstance(Person.class);
        //下面代码演示将对象转变为xml
        Marshaller m = context.createMarshaller();
        Address address = new Address("China","Beijing","Beijing","ShangDi West","100080");
        Person p = new Person(Calendar.getInstance(),"JAXB2",address,Gender.MALE,"SW");
        FileWriter fw = new FileWriter("person.xml");
        m.marshal(p,fw);

        //下面代码演示将上面生成的xml转换为对象
        FileReader fr = new FileReader("person.xml");
        Unmarshaller um = context.createUnmarshaller();
        Person p2 = (Person)um.unmarshal(fr);
        System.out.println("Country:"+p2.getAddress().getCountry());
    }
}

