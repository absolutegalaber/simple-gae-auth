package org.simple.auth.fakeoauth.servlet;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * @author Peter Schneider-Manzell
 */
@Slf4j
public class FakeOAuthProviderAccessTokenServlet extends HttpServlet {






    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String clientId = req.getParameter("client_id");
        String clientSecret = req.getParameter("client_secret");
        String redirectURI = req.getParameter("redirect_uri");
        String state = req.getParameter("state");
        String code = req.getParameter("code");
        String grantType = req.getParameter("grant_type");
        log.info("Entering doPost with client_id {}, client_secret {}, redirectUri {}, state {}, code {}",clientId,clientSecret,redirectURI,state, code);
        if(grantType == null){
            sendError("parameter grant_type is missing!", resp);
        } else if(!grantType.equals("authorization_code")){
            sendError("Only grant_type authorization_code is allowed!", resp);
        }
        else if(code == null){
            sendError("Parameter code is required!", resp);
        }
        else if(!FakeOauthProviderAuthorizationServlet.isValidCode(code)){
            sendError("Invalid code!", resp);
        }
        else if (redirectURI == null) {
            sendError("parameter redirect_uri is missing!", resp);

        } else if (clientId == null) {
            sendError("parameter client_id is missing!", resp);
        } else if (clientSecret == null) {
            sendError("parameter client_secret is missing!", resp);
        } else {
            String expectedSecret = FakeOauthProviderAuthorizationServlet.getSecret(clientId);
            String baseRedirectURI = FakeOauthProviderAuthorizationServlet.getRedirectURI(clientId);
            if (expectedSecret == null) {
                sendError("Unknown client_id ", resp);
            } else if (!expectedSecret.equals(clientSecret)) {
                sendError("Invalid client_secret", resp);
            } else if (!redirectURI.startsWith(baseRedirectURI)) {
                sendError("Invalid redirect_uri", resp);
            } else {
                PrintWriter pw=resp.getWriter();
                pw.write(createJSON(state));
                pw.close();
            }
        }
    }



    private String createJSON(String state) {
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"");
        sb.append("access_token");
        sb.append("\"");
        sb.append(":");
        sb.append("\"");
        sb.append(UUID.randomUUID().toString().replaceAll("-", ""));
        sb.append("\"");
        sb.append("}");
        return sb.toString();
    }

    private void sendError(String errorMessage, HttpServletResponse resp) {
        try {
            resp.setStatus(HttpStatus.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"invalid_request\",\"error_description\":\"" + errorMessage + "\"}");
            resp.getWriter().flush();
            resp.getWriter().close();

        } catch (IOException e) {
        }
    }
}
