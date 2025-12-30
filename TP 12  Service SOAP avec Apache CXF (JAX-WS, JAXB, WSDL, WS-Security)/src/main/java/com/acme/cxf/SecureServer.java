package com.acme.cxf;

import com.acme.cxf.impl.HelloServiceImpl;
import com.acme.cxf.security.UTPasswordCallback;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;

import java.util.HashMap;
import java.util.Map;

public class SecureServer {
    
    public static void main(String[] args) {
        // Configure WS-Security properties
        Map<String, Object> inProps = new HashMap<>();
        inProps.put("action", "UsernameToken");
        inProps.put("passwordType", "PasswordText");
        
        // Create password callback with valid credentials
        Map<String, String> users = new HashMap<>();
        users.put("student", "secret123");
        users.put("admin", "admin456");
        inProps.put("passwordCallbackRef", new UTPasswordCallback(users));
        
        // Create WS-Security interceptor
        WSS4JInInterceptor wssIn = new WSS4JInInterceptor(inProps);
        
        // Configure and create the service
        String address = "http://localhost:8080/services/hello-secure";
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        factory.setServiceClass(HelloServiceImpl.class);
        factory.setAddress(address);
        Server server = factory.create();
        
        // Add WS-Security interceptor to the endpoint
        server.getEndpoint().getInInterceptors().add(wssIn);
        
        System.out.println("  SECURE SOAP Service started successfully!");
        System.out.println("  Endpoint: " + address);
        System.out.println("  WSDL:     " + address + "?wsdl");
        System.out.println("  Valid credentials:");
        System.out.println("    Username: student  | Password: secret123");
        System.out.println("    Username: admin    | Password: admin456");
        System.out.println("  Press Ctrl+C to stop the server");
    }
}
