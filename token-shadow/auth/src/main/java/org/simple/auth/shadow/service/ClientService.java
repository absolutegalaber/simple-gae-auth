package org.simple.auth.shadow.service;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.simple.auth.model.IClient;
import org.simple.auth.model.OAuthException;
import org.simple.auth.shadow.OAuthRequestParameter;
import org.simple.auth.shadow.repository.IClientRepository;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Peter Schneider-Manzell
 */
@Slf4j
public class ClientService {
    private static final String CLIENT_ID_KEY = "client_id";
    private static final String REDIRECT_URI_KEY = "redirect_uri";
    private static IClientRepository clientRepository;


    public IClient fromRequest(HttpServletRequest req) throws OAuthException {
        log.info("Trying to detect client_id from request parameter {}", OAuthRequestParameter.CLIENT_ID.getParamName());
        Optional<String> clientId = OAuthRequestParameter.CLIENT_ID.getValue(req);
        log.info("Got clientId {}",clientId);
        if(!clientId.isPresent()){
          throw new OAuthException(OAuthRequestParameter.CLIENT_ID.getParamName() + " is required!");
        }
        String redirectUri = redirectUriFromRequest(req);
        IClient client = fromClientId(clientId.get());
        checkRedirectURI(client, redirectUri);
        return client;
    }

    private void checkRedirectURI(IClient client, String presentedRedirectUri) throws OAuthException {
        if (!presentedRedirectUri.startsWith(client.callbackUrl())) {
            throw new OAuthException("Redirect does not match client's redirect!");
        }
    }

    public String redirectUriFromRequest(HttpServletRequest req) throws OAuthException {
        log.info("Trying to detect redirectUri from request parameter {}", OAuthRequestParameter.REDIRECT_URI.getParamName());
        Optional<String> redirectUri = OAuthRequestParameter.REDIRECT_URI.getValue(req);
        if(!redirectUri.isPresent()){
            throw new OAuthException(OAuthRequestParameter.REDIRECT_URI.getParamName() + " is required!");
        }
        log.info("Detected redirectUri {} from request parameter {}", redirectUri.get(), OAuthRequestParameter.REDIRECT_URI.getParamName());
        return redirectUri.get();
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
        log.info("Storing client ID in session under key {}", CLIENT_ID_KEY);
        req.getSession().setAttribute(CLIENT_ID_KEY, client.clientId());
    }

    public IClient fromSession(HttpServletRequest req) throws OAuthException {
        log.info("Trying to load client ID from session under key {}", CLIENT_ID_KEY);
        String clientId = (String) req.getSession().getAttribute(CLIENT_ID_KEY);
        log.info("Detected client ID {} from session under key {}", clientId, CLIENT_ID_KEY);
        return fromClientId(clientId);
    }

    public static void setClientRepository(IClientRepository clientRepository) {
        ClientService.clientRepository = clientRepository;
    }

    public void redirectUriToSession(HttpServletRequest req, IClient client, String redirectUri) {
        String key = getSessionRedirectKey(client);
        log.info("Storing client redirectURI {} in session under key {}", redirectUri, key);
        req.getSession().setAttribute(key, redirectUri);
    }

    private String getSessionRedirectKey(IClient client) {
        StringBuilder sb = new StringBuilder("client_");
        sb.append(client.clientId());
        sb.append("_");
        sb.append(REDIRECT_URI_KEY);
        return sb.toString();
    }

    public String redirectUriFromSession(IClient client, HttpServletRequest req) {
        String key = getSessionRedirectKey(client);
        log.info("Trying to load client redirectURI from session under key {}", key);
        String redirectUri = (String) req.getSession().getAttribute(key);
        Preconditions.checkNotNull(redirectUri, key + " is required but not found in session!");
        log.info("Detected redirectURI {} for key {} in session", redirectUri, key);
        return redirectUri;
    }
}
