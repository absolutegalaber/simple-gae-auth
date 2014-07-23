package org.simple.auth.fakeoauth.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Peter Schneider-Manzell
 */
public class FakeOAuthProviderProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        Map<String,String> profile = new HashMap<>();
        profile.put("email","test@test.de");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(pw,profile);
        pw.close();
    }
}
