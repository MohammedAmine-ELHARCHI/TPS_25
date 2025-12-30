package com.acme.cxf.client;

import com.acme.cxf.api.HelloService;
import com.acme.cxf.model.Person;

import javax.xml.namespace.QName;
import jakarta.xml.ws.Service;
import java.net.URL;


public class ClientDemo {
    
    public static void main(String[] args) {
        try {
            System.out.println("  SOAP Client Demo");

            // Connect to the WSDL
            URL wsdlURL = new URL("http://localhost:8080/services/hello?wsdl");
            QName qname = new QName("http://api.cxf.acme.com/", "HelloService");
            Service service = Service.create(wsdlURL, qname);
            HelloService port = service.getPort(HelloService.class);
            
            System.out.println("\n1. Testing SayHello operation:");

            // Test sayHello with different names
            String response1 = port.sayHello("ClientJava");
            System.out.println("   Request:  sayHello(\"ClientJava\")");
            System.out.println("   Response: " + response1);
            
            String response2 = port.sayHello("Lachgar");
            System.out.println("\n   Request:  sayHello(\"Lachgar\")");
            System.out.println("   Response: " + response2);
            
            String response3 = port.sayHello(null);
            System.out.println("\n   Request:  sayHello(null)");
            System.out.println("   Response: " + response3);
            
            System.out.println("\n2. Testing FindPerson operation:");

            // Test findPersonById
            Person person1 = port.findPersonById("P-001");
            System.out.println("   Request:  findPersonById(\"P-001\")");
            System.out.println("   Response: " + person1);
            
            Person person2 = port.findPersonById("P-777");
            System.out.println("\n   Request:  findPersonById(\"P-777\")");
            System.out.println("   Response: " + person2);
            System.out.println("   Name:     " + person2.getName());
            System.out.println("   Age:      " + person2.getAge());
            
            System.out.println("  All operations completed successfully!");

        } catch (Exception e) {
            System.err.println("Error calling SOAP service:");
            e.printStackTrace();
            System.err.println("\nMake sure the Server is running on port 8080!");
        }
    }
}
