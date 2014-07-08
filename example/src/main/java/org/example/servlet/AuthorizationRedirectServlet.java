package org.example.servlet;

import lombok.extern.slf4j.Slf4j;
import org.simple.auth.model.OAuthException;
import org.simple.auth.model.networks.v2.Google;
import org.simple.auth.model.v2.OAuth2ClientConfig;
import org.simple.auth.model.v2.SystemPropertiesClientConfig;
import org.simple.auth.service.NetworkProvider;
import org.simple.auth.servlet.AbstractAuthorizationRedirect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Peter Schneider-Manzell
 */
@Slf4j
public class AuthorizationRedirectServlet extends AbstractAuthorizationRedirect {

    @Override
    public void configureNetworks(NetworkProvider provider) {
        provider.addNetwork( new Google(loadGoogleConfig()));
    }

    private OAuth2ClientConfig loadGoogleConfig() {
        return new SystemPropertiesClientConfig("google","simpleauthexample");
    }

    @Override
    public void onError(OAuthException authException, HttpServletRequest req, HttpServletResponse resp) {
        log.error("Error during authorziation redirect!",authException);
    }
}
