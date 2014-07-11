package org.simple.auth.model.networks.v2;

import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import org.simple.auth.model.ClientConfig;
import org.simple.auth.model.networks.ProfileAwareOAuth2Network;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class Google extends ProfileAwareOAuth2Network<GoogleProfile> {

    public static final String NAME = "google";
    private static final String AUTH_URL = "https://accounts.google.com/o/oauth2/auth";
    private static final String ACCESS_TOKEN_URL = "https://accounts.google.com/o/oauth2/token";
    private static final String PROFILE_URL = "https://www.googleapis.com/oauth2/v2/userinfo";

    public Google(ClientConfig config) {
        super(
                NAME,
                config,
                AUTH_URL,
                ACCESS_TOKEN_URL,
                PROFILE_URL,
                GoogleProfile.class
        );
    }

    @Override
    protected void networkSpecific(AuthorizationCodeTokenRequest authorizationCodeTokenRequest) {
        authorizationCodeTokenRequest.set("access_type", "offline");
    }
}
