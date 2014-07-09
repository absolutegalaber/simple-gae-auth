package org.simple.auth.service.builder;

import com.google.common.base.Preconditions;
import org.simple.auth.model.networks.DefaultOAuth1Network;
import org.simple.auth.model.networks.DefaultOAuth2Network;
import org.simple.auth.model.networks.v1.Twitter;
import org.simple.auth.model.networks.v2.*;
import org.simple.auth.model.v1.OAuth1ClientConfig;
import org.simple.auth.model.v2.OAuth2ClientConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class NetworkBuilder {
    private String name;
    private String authUrl;
    private String requestTokenUrl;
    private String accessTokenUrl;
    private String profileUrl;
    private Map<String, String> defaultQueryParams = new HashMap<>();
    private Map<String, String> defaultHeaders = new HashMap<>();
    private Boolean isAccessTokenResponseJson = true;


    public NetworkBuilder() {
    }

    public NetworkBuilder name(String name) {
        this.name = name;
        return this;
    }

    public NetworkBuilder authorizeUrl(String authorizeUrl) {
        this.authUrl = authorizeUrl;
        return this;
    }

    public NetworkBuilder requestTokenUrl(String requestTokenUrl) {
        this.requestTokenUrl = requestTokenUrl;
        return this;
    }

    public NetworkBuilder accessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
        return this;
    }

    public NetworkBuilder profileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
        return this;
    }

    public NetworkBuilder addDefaultHeader(String name, String value) {
        defaultHeaders.put(name, value);
        return this;
    }

    public NetworkBuilder addDefaultQueryParam(String name, String value) {
        defaultQueryParams.put(name, value);
        return this;
    }

    public NetworkBuilder isAccessTokenResponseJson(Boolean isAccessTokenResponseJson) {
        this.isAccessTokenResponseJson = isAccessTokenResponseJson;
        return this;
    }

    public DefaultOAuth2Network buildOauth2Network(OAuth2ClientConfig clientConfig) {
        Preconditions.checkNotNull(name, "Name required");
        Preconditions.checkNotNull(authUrl, "AuthorizationUrl required");
        Preconditions.checkNotNull(accessTokenUrl, "AccessTokenUrl required");
        Preconditions.checkNotNull(clientConfig, "ClientConfig required");
        DefaultOAuth2Network toReturn = new DefaultOAuth2Network(name, clientConfig, authUrl, accessTokenUrl, profileUrl);
        toReturn.headerDefaults(defaultHeaders);
        toReturn.queryParamsDefaults(defaultQueryParams);
        toReturn.setIsAccessTokenResponseJson(isAccessTokenResponseJson);
        return toReturn;
    }

    public DefaultOAuth1Network buildOauth1Network(OAuth1ClientConfig clientConfig) {
        Preconditions.checkNotNull(name, "Name required");
        Preconditions.checkNotNull(authUrl, "AuthorizationUrl required");
        Preconditions.checkNotNull(accessTokenUrl, "AccessTokenUrl required");
        Preconditions.checkNotNull(requestTokenUrl, "RequestTokenUrl required");
        Preconditions.checkNotNull(clientConfig, "ClientConfig required");
        DefaultOAuth1Network toReturn = new DefaultOAuth1Network(name, clientConfig, requestTokenUrl, authUrl, accessTokenUrl, profileUrl);
        toReturn.headerDefaults(defaultHeaders);
        toReturn.queryParamsDefaults(defaultQueryParams);
        return toReturn;
    }

    public DefaultOAuth2Network google(OAuth2ClientConfig clientConfig) {
        return new Google(clientConfig);
    }

    public DefaultOAuth2Network facebook(OAuth2ClientConfig clientConfig) {
        return new Facebook(clientConfig);
    }

    public DefaultOAuth2Network linkedIn(OAuth2ClientConfig clientConfig) {
        return new LinkedIn(clientConfig);
    }

    public DefaultOAuth2Network fourSquare(OAuth2ClientConfig clientConfig) {
        return new FourSquare(clientConfig);
    }

    public DefaultOAuth2Network github(OAuth2ClientConfig clientConfig) {
        return new Github(clientConfig);
    }

    public DefaultOAuth1Network twitter(OAuth1ClientConfig clientConfig) {
        return new Twitter(clientConfig);
    }
}
