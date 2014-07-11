package org.example.servlet;

import org.simple.auth.shadow.filter.ShadowTokenFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author Peter Schneider-Manzell
 */
public class ProtectedResourceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Serializable accountId = ShadowTokenFilter.getAccountId(req);
        String clientId = ShadowTokenFilter.getClientId(req);
        resp.getWriter().write("{\"message\":\"You accessed secure data\",\"account_id\":\"" + accountId + "\",\"client_id\":\"" + clientId + "\"}");
    }
}
