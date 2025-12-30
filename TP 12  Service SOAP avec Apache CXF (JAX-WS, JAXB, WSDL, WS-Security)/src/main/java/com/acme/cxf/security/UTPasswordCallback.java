package com.acme.cxf.security;

import org.apache.wss4j.common.ext.WSPasswordCallback;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;
import java.util.Map;


public class UTPasswordCallback implements CallbackHandler {
    
    private final Map<String, String> users;

    public UTPasswordCallback(Map<String, String> users) {
        this.users = users;
    }

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (Callback callback : callbacks) {
            if (callback instanceof WSPasswordCallback) {
                WSPasswordCallback pc = (WSPasswordCallback) callback;
                String username = pc.getIdentifier();
                String password = users.get(username);
                
                if (password != null) {
                    pc.setPassword(password);
                } else {
                    throw new IOException("Unknown user: " + username);
                }
            } else {
                throw new UnsupportedCallbackException(callback, "Unrecognized Callback");
            }
        }
    }
}
