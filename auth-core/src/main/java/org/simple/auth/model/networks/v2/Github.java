package org.simple.auth.model.networks.v2;

import org.simple.auth.model.networks.ProfileAwareOAuth2Network;
import org.simple.auth.model.v2.OAuth2ClientConfig;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class Github extends ProfileAwareOAuth2Network<GithubProfile> {

    public Github(OAuth2ClientConfig config) {
        super(
                "github",
                config,
                "https://github.com/login/oauth/authorize",
                "https://github.com/login/oauth/access_token",
                "https://api.github.com/user",
                GithubProfile.class
        );
        isAccessTokenResponseJson = false;
    }
}
