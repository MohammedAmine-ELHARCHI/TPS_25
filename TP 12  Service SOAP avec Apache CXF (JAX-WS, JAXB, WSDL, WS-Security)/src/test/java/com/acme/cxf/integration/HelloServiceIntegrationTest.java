package com.acme.cxf.integration;

import com.acme.cxf.api.HelloService;
import com.acme.cxf.impl.HelloServiceImpl;
import com.acme.cxf.model.Person;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import jakarta.xml.ws.Service;
import javax.xml.namespace.QName;
import java.net.URL;

import static org.junit.Assert.*;


public class HelloServiceIntegrationTest {

    private static org.apache.cxf.endpoint.Server server;
    private static final String SERVICE_URL = "http://localhost:9999/services/hello-test";
    private static final String WSDL_URL = SERVICE_URL + "?wsdl";

    @BeforeClass
    public static void startServer() throws Exception {
        // Start embedded CXF server on different port to avoid conflicts
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        factory.setServiceClass(HelloServiceImpl.class);
        factory.setAddress(SERVICE_URL);
        server = factory.create();
        
        // Wait a bit for server to be ready
        Thread.sleep(500);
        System.out.println("Test server started at: " + SERVICE_URL);
    }

    @AfterClass
    public static void stopServer() {
        if (server != null) {
            server.stop();
            server.destroy();
            System.out.println("Test server stopped");
        }
    }

    @Test
    public void testSayHelloViaSOAP() throws Exception {
        HelloService port = getServicePort();
        String result = port.sayHello("Integration Test");
        assertEquals("Bonjour, Integration Test", result);
    }

    @Test
    public void testSayHelloWithNullViaSOAP() throws Exception {
        HelloService port = getServicePort();
        String result = port.sayHello(null);
        assertEquals("Bonjour, inconnu", result);
    }

    @Test
    public void testFindPersonByIdViaSOAP() throws Exception {
        HelloService port = getServicePort();
        Person person = port.findPersonById("TEST-123");
        
        assertNotNull(person);
        assertEquals("TEST-123", person.getId());
        assertEquals("Ada Lovelace", person.getName());
        assertEquals(36, person.getAge());
    }

    @Test
    public void testWSDLIsAccessible() throws Exception {
        URL wsdlURL = new URL(WSDL_URL);
        String wsdlContent = new String(wsdlURL.openStream().readAllBytes());
        
        assertTrue("WSDL should contain service definition", wsdlContent.contains("HelloService"));
        assertTrue("WSDL should contain SayHello operation", wsdlContent.contains("SayHello"));
        assertTrue("WSDL should contain FindPerson operation", wsdlContent.contains("FindPerson"));
    }

    /**
     * Helper method to create SOAP client proxy
     */
    private HelloService getServicePort() throws Exception {
        URL wsdlURL = new URL(WSDL_URL);
        QName qname = new QName("http://api.cxf.acme.com/", "HelloService");
        Service service = Service.create(wsdlURL, qname);
        return service.getPort(HelloService.class);
    }
}
