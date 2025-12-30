package com.acme.cxf.security;

import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.junit.Before;
import org.junit.Test;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


public class UTPasswordCallbackTest {

    private UTPasswordCallback callback;
    private Map<String, String> testUsers;

    @Before
    public void setUp() {
        testUsers = new HashMap<>();
        testUsers.put("student", "secret123");
        testUsers.put("admin", "admin456");
        callback = new UTPasswordCallback(testUsers);
    }

    @Test
    public void testValidUserPassword() throws Exception {
        WSPasswordCallback pc = new WSPasswordCallback("student", WSPasswordCallback.USERNAME_TOKEN);
        callback.handle(new Callback[]{pc});
        assertEquals("secret123", pc.getPassword());
    }

    @Test
    public void testAdminUserPassword() throws Exception {
        WSPasswordCallback pc = new WSPasswordCallback("admin", WSPasswordCallback.USERNAME_TOKEN);
        callback.handle(new Callback[]{pc});
        assertEquals("admin456", pc.getPassword());
    }

    @Test
    public void testInvalidUser() {
        WSPasswordCallback pc = new WSPasswordCallback("invaliduser", WSPasswordCallback.USERNAME_TOKEN);
        try {
            callback.handle(new Callback[]{pc});
            fail("Should throw IOException for unknown user");
        } catch (IOException e) {
            assertTrue(e.getMessage().contains("Unknown user"));
        } catch (UnsupportedCallbackException e) {
            fail("Should not throw UnsupportedCallbackException");
        }
    }

    @Test
    public void testMultipleCallbacks() throws Exception {
        WSPasswordCallback pc1 = new WSPasswordCallback("student", WSPasswordCallback.USERNAME_TOKEN);
        WSPasswordCallback pc2 = new WSPasswordCallback("admin", WSPasswordCallback.USERNAME_TOKEN);
        
        callback.handle(new Callback[]{pc1, pc2});
        
        assertEquals("secret123", pc1.getPassword());
        assertEquals("admin456", pc2.getPassword());
    }

    @Test
    public void testEmptyUserMap() {
        UTPasswordCallback emptyCallback = new UTPasswordCallback(new HashMap<>());
        WSPasswordCallback pc = new WSPasswordCallback("anyuser", WSPasswordCallback.USERNAME_TOKEN);
        
        try {
            emptyCallback.handle(new Callback[]{pc});
            fail("Should throw IOException for unknown user");
        } catch (IOException e) {
            assertTrue(e.getMessage().contains("Unknown user"));
        } catch (UnsupportedCallbackException e) {
            fail("Should not throw UnsupportedCallbackException");
        }
    }
}
