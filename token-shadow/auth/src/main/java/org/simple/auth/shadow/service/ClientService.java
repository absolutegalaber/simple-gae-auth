package org.simple.auth.shadow.service;

import com.google.common.base.Preconditions;
import org.simple.auth.model.OAuthException;
import org.simple.auth.shadow.model.IClient;
import org.simple.auth.shadow.repository.IClientRepository;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Peter Schneider-Manzell
 */
public class ClientService {
    private static final String CLIENT_ID_KEY = "client_id";
    private static final String REDIRECT_URI_KEY = "redirect_uri";
    private static IClientRepository clientRepository;


    public IClient fromRequest(HttpServletRequest req) throws OAuthException {
        String clientId = req.getParameter(CLIENT_ID_KEY);
        String redirectUri = redirectUriFromRequest(req);
        IClient client = fromClientId(clientId);
        if (!redirectUri.startsWith(client.getCallbackUrl())) {
            throw new OAuthException("Redirect does not match client's redirect!");
        }
        return client;
    }

    public String redirectUriFromRequest(HttpServletRequest req) {
        String redirectUri = req.getParameter(REDIRECT_URI_KEY);
        Preconditions.checkNotNull(redirectUri, REDIRECT_URI_KEY + " is required!");
        return redirectUri;
    }

    private IClient fromClientId(String clientId) throws OAuthException {
        Preconditions.checkNotNull(clientId, CLIENT_ID_KEY + " is required!");
        IClient client = clientRepository.load(clientId);
        if (client == null) {
            throw new OAuthException(String.format("No client with ID [%s] found!", clientId));
        }
        return client;
    }

    public void toSession(HttpServletRequest req, IClient client) {
        req.getSession().setAttribute(CLIENT_ID_KEY, client.getClientId());
    }

    public IClient fromSession(HttpServletRequest req) throws OAuthException {
        String clientId = (String) req.getSession().getAttribute(CLIENT_ID_KEY);
        return fromClientId(clientId);
    }

    public static void setClientRepository(IClientRepository clientRepository) {
        ClientService.clientRepository = clientRepository;
    }

    public void redirectUriToSession(HttpServletRequest req, IClient client, String redirectUri) {
        req.getSession().setAttribute(getSessionRedirectKey(client), redirectUri);
    }

    private String getSessionRedirectKey(IClient client) {
        StringBuilder sb = new StringBuilder("client_");
        sb.append(client.getClientId());
        sb.append("_");
        sb.append(REDIRECT_URI_KEY);
        return sb.toString();
    }

    public String redirectUriFromSession(IClient client, HttpServletRequest req) {
        String redirectUri = (String) req.getSession().getAttribute(getSessionRedirectKey(client));
        Preconditions.checkNotNull(redirectUri, REDIRECT_URI_KEY + " is required but not found in session!");
        return redirectUri;
    }
}
