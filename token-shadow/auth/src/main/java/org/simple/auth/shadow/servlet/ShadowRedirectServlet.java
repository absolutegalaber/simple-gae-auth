package org.simple.auth.shadow.servlet;

import org.simple.auth.model.Network;
import org.simple.auth.model.OAuthException;
import org.simple.auth.servlet.AbstractAuthorizationRedirect;
import org.simple.auth.shadow.model.IClient;
import org.simple.auth.shadow.service.ClientService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Peter Schneider-Manzell
 */
public abstract class ShadowRedirectServlet extends AbstractAuthorizationRedirect {


    ClientService clientService = new ClientService();


    @Override
    public void beforeRedirect(HttpServletRequest req, HttpServletResponse resp, Network network) throws OAuthException {
        super.beforeRedirect(req, resp, network);
        if (!network.isProfileAware()) {
            throw new OAuthException(network.getName() + " is not configured to load profiles");
        }
        checkAndStoreClientInformation(req, resp);
    }

    private void checkAndStoreClientInformation(HttpServletRequest req, HttpServletResponse resp) throws OAuthException {
        IClient client = clientService.fromRequest(req);
        clientService.toSession(req, client);
        String redirectUri = clientService.redirectUriFromRequest(req);
        clientService.redirectUriToSession(req, client, redirectUri);
    }


}
