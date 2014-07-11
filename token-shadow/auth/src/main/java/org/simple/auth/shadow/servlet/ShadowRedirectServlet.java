package org.simple.auth.shadow.servlet;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.simple.auth.model.Network;
import org.simple.auth.model.OAuthException;
import org.simple.auth.servlet.AbstractAuthorizationRedirect;
import org.simple.auth.shadow.model.IClient;
import org.simple.auth.shadow.service.ClientService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Peter Schneider-Manzell
 */
@Slf4j
public class ShadowRedirectServlet extends AbstractAuthorizationRedirect {


    ClientService clientService = new ClientService();


    @Override
    public void beforeRedirect(HttpServletRequest req, HttpServletResponse resp, Network network) throws OAuthException {
        super.beforeRedirect(req, resp, network);
        if (!network.isProfileAware()) {
            throw new OAuthException(network.getName() + " is not configured to load profiles");
        }
        checkAndStoreClientInformation(req, resp);
    }

    @Override
    public void onError(Exception authException, HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.getWriter().write("{'error':'invalid_request','error_description':'" + authException.getMessage() + "'}");
            resp.getWriter().flush();
            resp.getWriter().close();
            resp.sendError(HttpStatus.SC_BAD_REQUEST);
        } catch (IOException e) {
            log.error("Could not write error message to stream!", e);
        }
    }

    private void checkAndStoreClientInformation(HttpServletRequest req, HttpServletResponse resp) throws OAuthException {
        IClient client = clientService.fromRequest(req);
        clientService.toSession(req, client);
        String redirectUri = clientService.redirectUriFromRequest(req);
        clientService.redirectUriToSession(req, client, redirectUri);
    }


}
