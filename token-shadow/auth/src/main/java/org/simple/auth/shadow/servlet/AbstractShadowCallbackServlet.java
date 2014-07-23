package org.simple.auth.shadow.servlet;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.simple.auth.model.BasicUserProfile;
import org.simple.auth.model.IClient;
import org.simple.auth.model.INetworkToken;
import org.simple.auth.model.OAuthException;
import org.simple.auth.servlet.AbstractProfileLoadingAuthorizationCallback;
import org.simple.auth.shadow.model.IPersistentNetworkToken;
import org.simple.auth.shadow.model.IShadowToken;
import org.simple.auth.shadow.service.AuthService;
import org.simple.auth.shadow.service.ClientService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Peter Schneider-Manzell
 */
@Slf4j
public abstract class AbstractShadowCallbackServlet extends AbstractProfileLoadingAuthorizationCallback {

    ClientService clientService = new ClientService();
    AuthService authService = new AuthService();


    @Override
    public void onProfileLoaded(INetworkToken accessToken, BasicUserProfile userProfile, HttpServletRequest req, HttpServletResponse resp) throws OAuthException, IOException {
        log.info("Trying to detect client...");
        IClient client = clientService.fromSession(req);
        log.info("Found client, creating shadow token");
        Serializable accountId = connectWithAccount(accessToken, userProfile, req);
        Preconditions.checkNotNull(accountId, "An account Id must be provided!");
        IPersistentNetworkToken persistentNetworkToken = authService.persist(accessToken, connectWithAccount(accessToken, userProfile, req));
        IShadowToken token = authService.getShadowToken(client, persistentNetworkToken);
        redirect(client, token, req, resp);
    }

    /**
     * Override this if you require account semantics.
     *
     * @param accessToken The network Token obtained from a Network (a.k.a. IdentityProvider).
     * @param userProfile The BasicUserProfile obtained from a Network (a.k.a. IdentityProvider).
     * @param request     The oroginal HttpServlet Callback Request.
     * @return A Account Id to be stored with the token and shadow token, if account semantics are required / desired.
     */
    protected abstract Serializable connectWithAccount(INetworkToken accessToken, BasicUserProfile userProfile, HttpServletRequest request);

    protected void redirect(IClient client, IShadowToken token, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Generating redirect URI...");
        String redirectURI = generateRedirectURI(client, token, req);
        log.info("Redirecting to {}", redirectURI);
        resp.sendRedirect(redirectURI);
    }

    private String generateRedirectURI(IClient client, IShadowToken token, HttpServletRequest req) {
        String baseURI = clientService.redirectUriFromSession(client, req);
        Map<String, String> parametersToAppend = generateQueryParams(token);
        StringBuilder finalRedirectURI = new StringBuilder(baseURI);
        String concatChar = "?";
        if (baseURI.contains(concatChar)) {
            concatChar = "&";
        }
        for (Map.Entry<String, String> parameter : parametersToAppend.entrySet()) {
            finalRedirectURI.append(concatChar);
            finalRedirectURI.append(parameter.getKey());
            finalRedirectURI.append("=");
            finalRedirectURI.append(parameter.getValue());
            concatChar = "&";
        }
        return finalRedirectURI.toString();
    }

    protected Map<String, String> generateQueryParams(IShadowToken token) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("access_token", token.getAccessToken());
        parameters.put("expires_in", Long.toString(getTimeDifferenceInSeconds(new Date(), token.getExpiresAt())));
        return parameters;
    }

    protected long getTimeDifferenceInSeconds(Date start, Date end) {
        long nowInSeconds = start.getTime() / 1000;
        long expirationInSeconds = end.getTime() / 1000;
        return expirationInSeconds - nowInSeconds;
    }

    @Override
    public void onError(Exception authException, HttpServletRequest req, HttpServletResponse resp) {
        log.error("An error occured", authException);
    }
}