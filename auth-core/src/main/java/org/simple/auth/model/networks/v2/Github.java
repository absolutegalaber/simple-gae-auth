package org.simple.auth.model.networks.v2;

import org.simple.auth.model.networks.ProfileAwareOAuth2Network;
import org.simple.auth.model.v2.OAuth2ClientConfig;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class Github extends ProfileAwareOAuth2Network<GithubProfile> {

    public static final String NAME = "github";
    private static final String AUTH_URL = "https://github.com/login/oauth/authorize";
    private static final String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String PROFILE_URL = "https://api.github.com/user";

    public Github(OAuth2ClientConfig config) {
        super(
                NAME,
                config,
                AUTH_URL,
                ACCESS_TOKEN_URL,
                PROFILE_URL,
                GithubProfile.class
        );
        isAccessTokenResponseJson = false;
    }
}