package org.simple.auth.service.builder;

import com.google.common.base.Preconditions;
import org.simple.auth.model.BasicUserProfile;
import org.simple.auth.model.ClientConfig;
import org.simple.auth.model.networks.DefaultOAuth1Network;
import org.simple.auth.model.networks.DefaultOAuth2Network;
import org.simple.auth.model.networks.ProfileAwareOAuth1Network;
import org.simple.auth.model.networks.ProfileAwareOAuth2Network;
import org.simple.auth.model.networks.v1.Twitter;
import org.simple.auth.model.networks.v2.*;

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
    private Class<? extends BasicUserProfile> profileClass;
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

    public NetworkBuilder profileClass(Class<? extends BasicUserProfile> profileClass) {
        this.profileClass = profileClass;
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

    public DefaultOAuth2Network buildOauth2Network(ClientConfig clientConfig) {
        Preconditions.checkNotNull(name, "Name required");
        Preconditions.checkNotNull(authUrl, "AuthorizationUrl required");
        Preconditions.checkNotNull(accessTokenUrl, "AccessTokenUrl required");
        Preconditions.checkNotNull(clientConfig, "ClientConfig required");
        DefaultOAuth2Network toReturn = new DefaultOAuth2Network(name, clientConfig, authUrl, accessTokenUrl);
        toReturn.headerDefaults(defaultHeaders);
        toReturn.queryParamsDefaults(defaultQueryParams);
        toReturn.setIsAccessTokenResponseJson(isAccessTokenResponseJson);
        return toReturn;
    }

    public ProfileAwareOAuth2Network buildProfileAwareOauth2Network(ClientConfig clientConfig) {
        Preconditions.checkNotNull(name, "Name required");
        Preconditions.checkNotNull(authUrl, "AuthorizationUrl required");
        Preconditions.checkNotNull(accessTokenUrl, "AccessTokenUrl required");
        Preconditions.checkNotNull(clientConfig, "ClientConfig required");
        Preconditions.checkNotNull(profileUrl, "Profile URL required");
        Preconditions.checkNotNull(profileClass, "Profile class required");
        ProfileAwareOAuth2Network toReturn = new ProfileAwareOAuth2Network(name, clientConfig, authUrl, accessTokenUrl, profileUrl, profileClass);
        toReturn.headerDefaults(defaultHeaders);
        toReturn.queryParamsDefaults(defaultQueryParams);
        toReturn.setIsAccessTokenResponseJson(isAccessTokenResponseJson);
        return toReturn;
    }

    public DefaultOAuth1Network buildOauth1Network(ClientConfig clientConfig) {
        Preconditions.checkNotNull(name, "Name required");
        Preconditions.checkNotNull(authUrl, "AuthorizationUrl required");
        Preconditions.checkNotNull(accessTokenUrl, "AccessTokenUrl required");
        Preconditions.checkNotNull(requestTokenUrl, "RequestTokenUrl required");
        Preconditions.checkNotNull(clientConfig, "ClientConfig required");
        DefaultOAuth1Network toReturn = new DefaultOAuth1Network(name, clientConfig, requestTokenUrl, authUrl, accessTokenUrl);
        toReturn.headerDefaults(defaultHeaders);
        toReturn.queryParamsDefaults(defaultQueryParams);
        return toReturn;
    }

    public DefaultOAuth1Network buildProfileAwareOauth1Network(ClientConfig clientConfig) {
        Preconditions.checkNotNull(name, "Name required");
        Preconditions.checkNotNull(authUrl, "AuthorizationUrl required");
        Preconditions.checkNotNull(accessTokenUrl, "AccessTokenUrl required");
        Preconditions.checkNotNull(requestTokenUrl, "RequestTokenUrl required");
        Preconditions.checkNotNull(clientConfig, "ClientConfig required");
        Preconditions.checkNotNull(profileUrl, "Profile URL required");
        Preconditions.checkNotNull(profileClass, "Profile class required");
        ProfileAwareOAuth1Network toReturn = new ProfileAwareOAuth1Network(name, clientConfig, requestTokenUrl, authUrl, accessTokenUrl, profileUrl, profileClass);
        toReturn.headerDefaults(defaultHeaders);
        toReturn.queryParamsDefaults(defaultQueryParams);
        return toReturn;
    }

    public ProfileAwareOAuth2Network<GoogleProfile> google(ClientConfig clientConfig) {
        return new Google(clientConfig);
    }

    public ProfileAwareOAuth2Network<FacebookProfile> facebook(ClientConfig clientConfig) {
        return new Facebook(clientConfig);
    }

    public DefaultOAuth2Network linkedIn(ClientConfig clientConfig) {
        return new LinkedIn(clientConfig);
    }

    public DefaultOAuth2Network fourSquare(ClientConfig clientConfig) {
        return new FourSquare(clientConfig);
    }

    public ProfileAwareOAuth2Network<GithubProfile> github(ClientConfig clientConfig) {
        return new Github(clientConfig);
    }

    public DefaultOAuth1Network twitter(ClientConfig clientConfig) {
        return new Twitter(clientConfig);
    }
}
