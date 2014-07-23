package org.example.test;


import junit.framework.Assert;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Peter Schneider-Manzell
 */
public abstract class BaseFunctionalTest {
    public static final int HTTP_PORT = 8088;
    public static final String BASE_URL = "http://localhost:"+HTTP_PORT+"/";

    protected String doGet(String relativeURL, Map<String,String> headers, int expectedHttpStatus) throws Exception{
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(createUri(relativeURL));
        for (Map.Entry<String, String> headerEntry : headers.entrySet()) {
            httpGet.setHeader(headerEntry.getKey(),headerEntry.getValue());
        }
        try {
            HttpResponse response = httpClient.execute(httpGet);
            Assert.assertEquals(expectedHttpStatus,response.getStatusLine().getStatusCode());
            ResponseHandler<String> handler = new BasicResponseHandler();
            return handler.handleResponse(response);
        }
        catch (HttpResponseException e){
            Assert.assertEquals(expectedHttpStatus,e.getStatusCode());
            return null;
        }

    }

    protected String doGet(String relativeURL, int expectedHttpStatus) throws Exception{
      return doGet(relativeURL,new HashMap<String, String>(),expectedHttpStatus);
    }

    private String createUri(String relativeURL) {
        return BASE_URL + relativeURL;
    }
}
