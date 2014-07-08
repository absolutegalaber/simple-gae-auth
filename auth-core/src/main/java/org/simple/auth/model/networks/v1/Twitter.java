package org.simple.auth.model.networks.v1;

import org.simple.auth.model.networks.DefaultOAuth1Network;
import org.simple.auth.model.v1.OAuth1ClientConfig;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class Twitter extends DefaultOAuth1Network {
    public Twitter(OAuth1ClientConfig clientConfig) {
        super(
                "twitter",
                clientConfig,
                "https://api.twitter.com/oauth/request_token",
                "https://api.twitter.com/oauth/authorize",
                "https://api.twitter.com/oauth/access_token",
                "https://api.twitter.com/1.1/account/verify_credentials.json"
        );
    }
}
