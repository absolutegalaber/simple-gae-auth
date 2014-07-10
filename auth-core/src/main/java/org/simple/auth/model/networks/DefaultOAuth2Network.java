package org.simple.auth.model.networks;

import com.google.api.client.auth.oauth2.*;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.simple.auth.model.Network;
import org.simple.auth.model.OAuthException;
import org.simple.auth.model.v2.OAuth2AccessToken;
import org.simple.auth.model.v2.OAuth2ClientConfig;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Slf4j
public class DefaultOAuth2Network extends Network<OAuth2AccessToken, OAuth2ClientConfig> {
    protected final String authUrl;
    protected final String accessTokenUrl;

    protected final Map<String, String> defaultQueryParams = new HashMap<>();
    protected final Map<String, String> defaultHeaders = new HashMap<>();
    protected Boolean isAccessTokenResponseJson = true;

    public DefaultOAuth2Network(String name, OAuth2ClientConfig config, String authUrl, String accessTokenUrl) {
        super(name, config);
        this.authUrl = authUrl;
        this.accessTokenUrl = accessTokenUrl;

    }



    @Override
    public String authorizationRedirect(HttpServletRequest request) throws OAuthException {
        return new AuthorizationCodeRequestUrl(authUrl, clientConfig.clientId())
                .setRedirectUri(clientConfig.callbackUrl())
                .setScopes(clientConfig.scope())
                .setState(clientConfig.state())
                .build();
    }

    @Override
    public OAuth2AccessToken accessToken(HttpServletRequest callbackRequest) throws OAuthException {
        try {
            String authCode = extractAuthCode(callbackRequest);
            AuthorizationCodeTokenRequest tokenRequest = new AuthorizationCodeTokenRequest(new NetHttpTransport(), jacksonFactory, new GenericUrl(accessTokenUrl), authCode)
                    .setGrantType("authorization_code")
                    .setRedirectUri(clientConfig.callbackUrl());
            addTokenRequestAuthorization(tokenRequest);
            return executeTokenRequest(tokenRequest);
        } catch (IOException e) {
            throw new OAuthException(e.getMessage());
        }
    }

    @Override
    public OAuth2AccessToken refreshToken(OAuth2AccessToken token) throws OAuthException {
        try {
            RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest(new NetHttpTransport(), jacksonFactory, new GenericUrl(accessTokenUrl), token.getRefreshToken())
                    .setGrantType("refresh_token");
            addTokenRequestAuthorization(refreshTokenRequest);
            return executeTokenRequest(refreshTokenRequest);
        } catch (IOException e) {
            throw new OAuthException(e.getMessage());
        }
    }


    @Override
    protected HttpResponse executeGet(String url, OAuth2AccessToken token, boolean withJsonParser) throws OAuthException {
        try {
            Credential credential = new Credential(myAccessMethod()).setAccessToken(token.getAccessToken());
            HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory(credential);
            HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(url));
            if (withJsonParser) {
                request.setParser(jsonObjectParser);
            }
            addDefaultParamsAndHeaders(request);
            HttpResponse response = request.execute();
            if (!response.isSuccessStatusCode()) {
                throw new OAuthException(response.getStatusMessage());
            }
            return response;
        } catch (IOException e) {
            throw new OAuthException(e.getMessage());
        }
    }

    private void addDefaultParamsAndHeaders(HttpRequest httpRequest) {
        for (Map.Entry<String, String> paramToAdd : defaultQueryParams.entrySet()) {
            httpRequest.getUrl().put(paramToAdd.getKey(), paramToAdd.getValue());
        }
        for (Map.Entry<String, String> headerToAdd : defaultHeaders.entrySet()) {
            httpRequest.getHeaders().put(headerToAdd.getKey(), headerToAdd.getValue());
        }
    }

    @Override
    public HttpResponse post(String url, OAuth2AccessToken token) throws OAuthException {
        return null;
    }

    protected String extractAuthCode(HttpServletRequest callbackRequest) throws OAuthException {
        StringBuffer fullUrlBuf = callbackRequest.getRequestURL();
        if (callbackRequest.getQueryString() != null) {
            fullUrlBuf.append('?').append(callbackRequest.getQueryString());
        }
        AuthorizationCodeResponseUrl authResponse = new AuthorizationCodeResponseUrl(fullUrlBuf.toString());
        if (authResponse.getError() != null) {
            throw new OAuthException(authResponse.getErrorDescription());
        }
        return authResponse.getCode();
    }


    protected void addTokenRequestAuthorization(TokenRequest request) {
        request.setClientAuthentication(
                new ClientParametersAuthentication(clientConfig.clientId(), clientConfig.secret())
        );
    }

    protected OAuth2AccessToken executeTokenRequest(TokenRequest tokenRequest) throws IOException {
        if (isAccessTokenResponseJson) {
            TokenResponse tokenResponse = tokenRequest.execute();
            return new OAuth2AccessToken(name, tokenResponse.getAccessToken(), tokenResponse.getRefreshToken(), tokenResponse.getExpiresInSeconds());
        } else {
            HttpResponse httpResponse = tokenRequest.executeUnparsed();
            String responseText = new Scanner(httpResponse.getContent()).useDelimiter("\\A").next();
            Iterable<String> responseItems = Splitter.on("&").trimResults().split(responseText);
            TokenResponse tokenResponse = new TokenResponse();
            for (String singleResponseItem : responseItems) {
                if (singleResponseItem.startsWith("access_token")) {
                    tokenResponse.setAccessToken(singleResponseItem.substring(singleResponseItem.indexOf("=") + 1));
                }
                if (singleResponseItem.startsWith("expires")) {
                    tokenResponse.setExpiresInSeconds(Long.valueOf(singleResponseItem.substring(singleResponseItem.indexOf("=") + 1)));
                }
            }
            return new OAuth2AccessToken(name, tokenResponse.getAccessToken(), null, tokenResponse.getExpiresInSeconds());
        }
    }

    protected Credential.AccessMethod myAccessMethod() {
        return BearerToken.authorizationHeaderAccessMethod();
    }

    public void headerDefaults(Map<String, String> defaults) {
        defaultHeaders.putAll(defaults);
    }

    public void queryParamsDefaults(Map<String, String> defaults) {
        defaultQueryParams.putAll(defaults);
    }

    public void setIsAccessTokenResponseJson(Boolean isAccessTokenResponseJson) {
        this.isAccessTokenResponseJson = isAccessTokenResponseJson;
    }
}
