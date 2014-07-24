package org.simple.auth.fakeoauth.servlet;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Peter Schneider-Manzell
 */
@Slf4j
public class FakeOauthProviderAuthorizationServlet extends HttpServlet {

    private static ConcurrentHashMap<String, String> clientSecrets = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, String> clientRedirectURIs = new ConcurrentHashMap<>();
    private static Set<String> validCodes = new HashSet<>();



    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        String clientId = config.getInitParameter("clientId");
        String clientSecret = config.getInitParameter("clientSecret");
        String redirectUri = config.getInitParameter("redirectUri");
        log.error("Entering init with clientid {}, secret {} and redirect URI {}", clientId, clientSecret, redirectUri);
        if (clientId != null && clientSecret != null && redirectUri != null) {
            addClient(clientId, clientSecret, redirectUri);
        }
    }

    public static void addClient(String clientId, String clientSecret, String clientRedirectURI) {
        log.info("Adding client with id {}",clientId);
        clientSecrets.put(clientId, clientSecret);
        clientRedirectURIs.put(clientId, clientRedirectURI);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String responseType = req.getParameter("code");
        String clientId = req.getParameter("client_id");
        String redirectURI = req.getParameter("redirect_uri");
        String state = req.getParameter("state");
        if (redirectURI == null) {
            sendError("parameter redirect_uri is missing!", resp);

        } else if (clientId == null) {
            sendError("parameter client_id is missing!", resp);
        } else {
            String baseRedirectURI = getRedirectURI(clientId);
            if (baseRedirectURI == null) {
                sendError("Unknown client_id", resp);
            } else if (!redirectURI.startsWith(baseRedirectURI)) {
                sendError("Invalid redirect_uri (got "+redirectURI+" but expected sub of "+baseRedirectURI, resp);
            } else {
                String finalRedirectURI = createRedirectURI(redirectURI, state);
                resp.sendRedirect(finalRedirectURI);
            }
        }
    }

    public static String getRedirectURI(String clientId) {
        return clientRedirectURIs.get(clientId);
    }

    public static String getSecret(String clientId) {
        return clientSecrets.get(clientId);
    }

    public static boolean isValidCode(String code) {
        return validCodes.contains(code);
    }

    public static void addValidCode(String code){
        validCodes.add(code);
    }

    private String createRedirectURI(String redirectURI, String state) {
        String code = UUID.randomUUID().toString().replaceAll("-", "");
        StringBuilder sb = new StringBuilder(redirectURI);
        sb.append("?");
        sb.append("code=");
        sb.append(code);
        if (state != null) {
            sb.append("&state=");
            sb.append(state);
        }
        addValidCode(code);
        return sb.toString();
    }

    private void sendError(String errorMessage, HttpServletResponse resp) {
        try {
            resp.setStatus(HttpStatus.SC_BAD_REQUEST);
            PrintWriter pw = resp.getWriter();
            pw.write("{\"error\":\"invalid_request\",\"error_description\":\"" + errorMessage + "\"}");
            pw.flush();
            pw.close();

        } catch (IOException e) {
        }
    }


}
