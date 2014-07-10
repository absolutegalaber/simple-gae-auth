package org.simple.auth.model.networks;

import com.google.api.client.auth.oauth.*;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.common.base.Optional;
import lombok.extern.slf4j.Slf4j;
import org.simple.auth.model.Network;
import org.simple.auth.model.OAuthException;
import org.simple.auth.model.v1.OAuth1AccessToken;
import org.simple.auth.model.v1.OAuth1ClientConfig;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Slf4j
public class DefaultOAuth1Network extends Network<OAuth1AccessToken, OAuth1ClientConfig> {
    private final String requestTokenUrl;
    private final String authUrl;
    private final String accessTokenUrl;

    protected final Map<String, String> defaultQueryParams = new HashMap<>();
    protected final Map<String, String> defaultHeaders = new HashMap<>();


    public DefaultOAuth1Network(String name, OAuth1ClientConfig clientConfig, String requestTokenUrl, String authUrl, String accessTokenUrl) {
        super(name, clientConfig);
        this.requestTokenUrl = requestTokenUrl;
        this.authUrl = authUrl;
        this.accessTokenUrl = accessTokenUrl;

    }



    @Override
    public String authorizationRedirect(HttpServletRequest request) throws OAuthException {
        try {
            //signer
            OAuthHmacSigner signer = new OAuthHmacSigner();
            signer.clientSharedSecret = clientConfig.secret();
            //get a request token
            OAuthGetTemporaryToken temporaryToken = new OAuthGetTemporaryToken(requestTokenUrl);
            temporaryToken.signer = signer;
            temporaryToken.callback = clientConfig.callbackUrl();
            temporaryToken.transport = new NetHttpTransport();
            temporaryToken.consumerKey = clientConfig.clientId();
            OAuthCredentialsResponse temporaryTokenResponse = temporaryToken.execute();
            OAuthAuthorizeTemporaryTokenUrl accessTempToken = new OAuthAuthorizeTemporaryTokenUrl(authUrl);
            accessTempToken.temporaryToken = temporaryTokenResponse.token;
            request.getSession().setAttribute(name + "_req_token", temporaryTokenResponse.token);
            request.getSession().setAttribute(name + "_req_token_secret", temporaryTokenResponse.tokenSecret);
            return accessTempToken.build();
        } catch (IOException e) {
            e.printStackTrace();
            throw new OAuthException(e.getMessage());
        }
    }

    @Override
    public OAuth1AccessToken accessToken(HttpServletRequest callbackRequest) throws OAuthException {
        try {
            String requestToken = (String) callbackRequest.getSession().getAttribute(name + "_req_token");
            String requestTokenSecret = (String) callbackRequest.getSession().getAttribute(name + "_req_token_secret");
            String oauthVerifier = callbackRequest.getParameter("oauth_verifier");

            OAuthHmacSigner signer = new OAuthHmacSigner();
            signer.clientSharedSecret = clientConfig.secret();
            signer.tokenSharedSecret = requestTokenSecret;

            OAuthGetAccessToken getAccessToken = new OAuthGetAccessToken(accessTokenUrl);
            getAccessToken.signer = signer;
            getAccessToken.temporaryToken = requestToken;
            getAccessToken.transport = new NetHttpTransport();
            getAccessToken.verifier = oauthVerifier;
            getAccessToken.consumerKey = clientConfig.clientId();
            OAuthCredentialsResponse accessTokenResponse = getAccessToken.execute();
            return new OAuth1AccessToken(name, accessTokenResponse.token, accessTokenResponse.tokenSecret);
        } catch (IOException e) {
            throw new OAuthException(e.getMessage());
        }
    }

    @Override
    public OAuth1AccessToken refreshToken(OAuth1AccessToken token) throws OAuthException {
        throw new OAuthException("Refreshing not part of OAuth 1.0");
    }

    @Override
    protected HttpResponse executeGet(String url, OAuth1AccessToken token, boolean withJsonParser) throws OAuthException {
        try {
            OAuthHmacSigner signer = new OAuthHmacSigner();
            signer.clientSharedSecret = clientConfig.secret();
            signer.tokenSharedSecret = token.getTokenSecret();

            OAuthParameters parameters = new OAuthParameters();
            parameters.consumerKey = clientConfig.clientId();
            parameters.token = token.getAccessToken();
            parameters.signer = signer;

            HttpRequestFactory factory = new NetHttpTransport().createRequestFactory(parameters);
            HttpRequest req = factory.buildGetRequest(new GenericUrl(url));
            addDefaultParamsAndHeaders(req);
            if (withJsonParser) {
                req.setParser(jsonObjectParser);
            }
            return req.execute();
        } catch (IOException e) {
            throw new OAuthException(e.getMessage());
        }
    }


    @Override
    public HttpResponse post(String url, OAuth1AccessToken token) throws OAuthException {
        return null;
    }

    private void addDefaultParamsAndHeaders(HttpRequest httpRequest) {
        for (Map.Entry<String, String> paramToAdd : defaultQueryParams.entrySet()) {
            httpRequest.getUrl().put(paramToAdd.getKey(), paramToAdd.getValue());
        }
        for (Map.Entry<String, String> headerToAdd : defaultHeaders.entrySet()) {
            httpRequest.getHeaders().put(headerToAdd.getKey(), headerToAdd.getValue());
        }
    }

    public void headerDefaults(Map<String, String> defaults) {
        defaultHeaders.putAll(defaults);
    }

    public void queryParamsDefaults(Map<String, String> defaults) {
        defaultQueryParams.putAll(defaults);
    }


}