package org.example.test;

import junit.framework.Assert;
import org.apache.http.HttpStatus;
import org.junit.Test;

/**
 * @author Peter Schneider-Manzell
 */
public class ServerIsRunningTest extends BaseFunctionalTest {

    @Test
    public void serverIsRunningTest() throws Exception {
        String result = doGet("index.html", HttpStatus.SC_OK);
        Assert.assertTrue(result.contains("Simple Auth Test with bearer token"));
    }

    @Test
    public void apiIsProtected() throws Exception {
            doGet("api/protected", HttpStatus.SC_FORBIDDEN);

    }


}
