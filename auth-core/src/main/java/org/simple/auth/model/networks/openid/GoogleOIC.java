package org.simple.auth.model.networks.openid;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.TokenRequest;
import com.google.api.client.auth.openidconnect.IdToken;
import com.google.api.client.auth.openidconnect.IdTokenResponse;
import com.google.api.client.auth.openidconnect.IdTokenVerifier;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import lombok.extern.slf4j.Slf4j;
import org.simple.auth.model.AccessToken;
import org.simple.auth.model.ClientConfig;
import org.simple.auth.model.OAuthException;
import org.simple.auth.model.networks.OpenIdConnectNetwork;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Slf4j
public class GoogleOIC extends OpenIdConnectNetwork {
    public static final String NAME = "google_oic";
    private static final String AUTH_URL = "https://accounts.google.com/o/oauth2/auth";
    private static final String ACCESS_TOKEN_URL = "https://accounts.google.com/o/oauth2/token";
    private static final String PROFILE_URL = "https://www.googleapis.com/oauth2/v2/userinfo";


    public GoogleOIC(ClientConfig config) {
        super(NAME, config, AUTH_URL, ACCESS_TOKEN_URL);
    }

    @Override
    public String authorizationRedirect(HttpServletRequest request, String csrfToken) throws OAuthException {
        String redirectUri = new AuthorizationCodeRequestUrl(authUrl, clientConfig.clientId())
                .setRedirectUri(clientConfig.callbackUrl())
                .setScopes(Arrays.asList("openid", "profile", "email"))
                .setState(csrfToken)
                .setResponseTypes(Arrays.asList("code"))
                .build();
        log.info("Redirecting to: {}", redirectUri);
        return redirectUri;
    }

    @Override
    public AccessToken accessToken(HttpServletRequest callbackRequest) throws OAuthException {
        try {
            String code = extractAuthCode(callbackRequest);
//        AuthorizationCodeTokenRequest tokenRequest = new AuthorizationCodeTokenRequest(new NetHttpTransport(), jacksonFactory, new GenericUrl(accessTokenUrl), code)
//                .setGrantType("authorization_code")
//                .setRedirectUri(clientConfig.callbackUrl())
//                .set("client_secret", clientConfig.secret())
//                .set("access_type", "offline");

            TokenRequest request = new TokenRequest(new NetHttpTransport(), jacksonFactory, new GenericUrl(accessTokenUrl), "authorization_code");
            request.set("client_id", clientConfig.clientId());
            request.set("client_secret", clientConfig.secret());
            request.set("redirect_uri", clientConfig.callbackUrl());
            request.set("code", code);

            IdTokenResponse idTokenResponse = IdTokenResponse.execute(request);
            IdToken idToken = idTokenResponse.parseIdToken();
            log.info("idToken: {}", idToken);
            IdTokenVerifier idTokenVerifier = new IdTokenVerifier.Builder()
                    .setIssuer("accounts.google.com").setAudience(Arrays.asList(clientConfig.clientId())).build();
            boolean verify = idTokenVerifier.verify(idToken);
            log.info("Verified: {}", verify);
            return AccessToken.openIdconnectToken(name, idTokenResponse.getAccessToken(), idTokenResponse.getRefreshToken(), idTokenResponse.getExpiresInSeconds(), idToken);
        } catch (IOException e) {
            throw new OAuthException(e.getMessage());
        }
    }
}
