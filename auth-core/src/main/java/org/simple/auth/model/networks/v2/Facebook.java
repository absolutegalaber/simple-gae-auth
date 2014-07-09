package org.simple.auth.model.networks.v2;

import org.simple.auth.model.networks.ProfileAwareOAuth2Network;
import org.simple.auth.model.v2.OAuth2ClientConfig;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class Facebook extends ProfileAwareOAuth2Network<FacebookProfile> {

    public Facebook(OAuth2ClientConfig config) {
        super(
                "facebook",
                config,
                "https://graph.facebook.com/oauth/authorize",
                "https://graph.facebook.com/oauth/access_token",
                "https://graph.facebook.com/v2.0/me",
                FacebookProfile.class
        );
        isAccessTokenResponseJson = false;
    }
}
