package com.acme.cxf;

import com.acme.cxf.api.HelloService;
import com.acme.cxf.impl.HelloServiceImpl;
import com.acme.cxf.model.Person;
import org.junit.Test;
import static org.junit.Assert.*;


public class HelloServiceTest {

    @Test
    public void testSayHello() {
        HelloService service = new HelloServiceImpl();
        String result = service.sayHello("Test");
        assertEquals("Bonjour, Test", result);
    }

    @Test
    public void testSayHelloWithNull() {
        HelloService service = new HelloServiceImpl();
        String result = service.sayHello(null);
        assertEquals("Bonjour, inconnu", result);
    }

    @Test
    public void testFindPersonById() {
        HelloService service = new HelloServiceImpl();
        Person person = service.findPersonById("P-123");
        assertNotNull(person);
        assertEquals("P-123", person.getId());
        assertEquals("Ada Lovelace", person.getName());
        assertEquals(36, person.getAge());
    }
}
