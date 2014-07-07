package org.simple.auth.model.networks.v2;

import org.simple.auth.model.networks.DefaultOAuth2Network;
import org.simple.auth.model.v2.OAuth2ClientConfig;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class Github extends DefaultOAuth2Network {

    public Github(OAuth2ClientConfig config) {
        super(
                "github",
                config,
                "https://github.com/login/oauth/authorize",
                "https://github.com/login/oauth/access_token",
                "https://api.github.com/user"
        );
        isAccessTokenResponseJson = false;
    }
}
