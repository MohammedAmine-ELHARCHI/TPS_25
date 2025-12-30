package com.acme.cxf.impl;

import com.acme.cxf.api.HelloService;
import com.acme.cxf.model.Person;
import jakarta.jws.WebService;

@WebService(
    serviceName = "HelloService",
    portName = "HelloServicePort",
    endpointInterface = "com.acme.cxf.api.HelloService",
    targetNamespace = "http://api.cxf.acme.com/"
)
public class HelloServiceImpl implements HelloService {


    @Override
    public String sayHello(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Bonjour, inconnu";
        }
        return "Bonjour, " + name;
    }


    @Override
    public Person findPersonById(String id) {
        // Mock implementation - in reality, would fetch from database
        return new Person(id, "Ada Lovelace", 36);
    }
}
