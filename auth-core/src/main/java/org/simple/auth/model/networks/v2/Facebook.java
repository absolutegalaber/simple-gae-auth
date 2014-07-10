package org.simple.auth.model.networks.v2;

import org.simple.auth.model.networks.ProfileAwareOAuth2Network;
import org.simple.auth.model.v2.OAuth2ClientConfig;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class Facebook extends ProfileAwareOAuth2Network<FacebookProfile> {

    public static final String NAME = "facebook";
    private static final String AUTH_URL = "https://graph.facebook.com/oauth/authorize";
    private static final String ACCESS_TOKEN_URL = "https://graph.facebook.com/oauth/access_token";
    private static final String PROFILE_URL = "https://graph.facebook.com/v2.0/me";

    public Facebook(OAuth2ClientConfig config) {
        super(
                NAME,
                config,
                AUTH_URL,
                ACCESS_TOKEN_URL,
                PROFILE_URL,
                FacebookProfile.class
        );
        isAccessTokenResponseJson = false;
    }
}
