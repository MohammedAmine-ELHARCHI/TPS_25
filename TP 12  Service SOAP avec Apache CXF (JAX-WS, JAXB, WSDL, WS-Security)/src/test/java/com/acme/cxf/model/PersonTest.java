package com.acme.cxf.model;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.Assert.*;


public class PersonTest {

    @Test
    public void testPersonCreation() {
        Person person = new Person("P-001", "Ada Lovelace", 36);
        
        assertEquals("P-001", person.getId());
        assertEquals("Ada Lovelace", person.getName());
        assertEquals(36, person.getAge());
    }

    @Test
    public void testPersonSetters() {
        Person person = new Person();
        person.setId("P-002");
        person.setName("Alan Turing");
        person.setAge(41);
        
        assertEquals("P-002", person.getId());
        assertEquals("Alan Turing", person.getName());
        assertEquals(41, person.getAge());
    }

    @Test
    public void testPersonToString() {
        Person person = new Person("P-003", "Grace Hopper", 85);
        String result = person.toString();
        
        assertTrue(result.contains("P-003"));
        assertTrue(result.contains("Grace Hopper"));
        assertTrue(result.contains("85"));
    }

    @Test
    public void testJAXBMarshalling() throws Exception {
        Person person = new Person("P-004", "John von Neumann", 53);
        
        JAXBContext context = JAXBContext.newInstance(Person.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        
        StringWriter writer = new StringWriter();
        marshaller.marshal(person, writer);
        String xml = writer.toString();
        
        assertTrue("XML should contain Person root element", xml.contains("<Person>"));
        assertTrue("XML should contain id", xml.contains("<id>P-004</id>"));
        assertTrue("XML should contain name", xml.contains("<name>John von Neumann</name>"));
        assertTrue("XML should contain age", xml.contains("<age>53</age>"));
    }

    @Test
    public void testJAXBUnmarshalling() throws Exception {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                     "<Person>\n" +
                     "    <id>P-005</id>\n" +
                     "    <name>Donald Knuth</name>\n" +
                     "    <age>85</age>\n" +
                     "</Person>";
        
        JAXBContext context = JAXBContext.newInstance(Person.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        
        Person person = (Person) unmarshaller.unmarshal(new StringReader(xml));
        
        assertNotNull(person);
        assertEquals("P-005", person.getId());
        assertEquals("Donald Knuth", person.getName());
        assertEquals(85, person.getAge());
    }

    @Test
    public void testJAXBRoundTrip() throws Exception {
        Person originalPerson = new Person("P-006", "Dennis Ritchie", 70);
        
        // Marshal to XML
        JAXBContext context = JAXBContext.newInstance(Person.class);
        Marshaller marshaller = context.createMarshaller();
        StringWriter writer = new StringWriter();
        marshaller.marshal(originalPerson, writer);
        String xml = writer.toString();
        
        // Unmarshal back to object
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Person unmarshalledPerson = (Person) unmarshaller.unmarshal(new StringReader(xml));
        
        assertEquals(originalPerson.getId(), unmarshalledPerson.getId());
        assertEquals(originalPerson.getName(), unmarshalledPerson.getName());
        assertEquals(originalPerson.getAge(), unmarshalledPerson.getAge());
    }

    @Test
    public void testDefaultConstructor() {
        Person person = new Person();
        assertNull(person.getId());
        assertNull(person.getName());
        assertEquals(0, person.getAge());
    }
}
