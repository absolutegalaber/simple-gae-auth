package org.simple.auth.service.builder.v1;

import org.simple.auth.model.v1.OAuth1ClientConfig;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class HardcodedOAuth1ClientConfig implements OAuth1ClientConfig {
    private final String clientId;
    private final String secret;
    private final String callbackUrl;
    private final String state;

    public HardcodedOAuth1ClientConfig(String clientId, String secret, String callbackUrl, String state) {
        this.clientId = clientId;
        this.secret = secret;
        this.callbackUrl = callbackUrl;
        this.state = state;
    }

    @Override
    public String clientId() {
        return clientId;
    }

    @Override
    public String secret() {
        return secret;
    }

    @Override
    public String callbackUrl() {
        return callbackUrl;
    }

    @Override
    public String state() {
        return state;
    }
}
