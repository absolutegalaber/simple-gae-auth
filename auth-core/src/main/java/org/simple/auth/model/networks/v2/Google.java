package org.simple.auth.model.networks.v2;

import org.simple.auth.model.networks.ProfileAwareOAuth2Network;
import org.simple.auth.model.v2.OAuth2ClientConfig;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class Google extends ProfileAwareOAuth2Network<GoogleProfile> {

    public Google(OAuth2ClientConfig config) {
        super(
                "google",
                config,
                "https://accounts.google.com/o/oauth2/auth",
                "https://accounts.google.com/o/oauth2/token",
                "https://www.googleapis.com/oauth2/v2/userinfo",
                GoogleProfile.class
        );
    }
}
