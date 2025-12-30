package com.acme.cxf;

import com.acme.cxf.impl.HelloServiceImpl;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

public class Server {
    
    public static void main(String[] args) {
        String address = "http://localhost:8080/services/hello";
        
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        factory.setServiceClass(HelloServiceImpl.class);
        factory.setAddress(address);
        factory.create();
        
        System.out.println("  SOAP Service started successfully!");
        System.out.println("  Endpoint: " + address);
        System.out.println("  WSDL:     " + address + "?wsdl");
        System.out.println("  Press Ctrl+C to stop the server");
    }
}
