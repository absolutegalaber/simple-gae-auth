package org.simple.auth.shadow.model;

import lombok.Getter;

/**
 * @author Peter Schneider-Manzell
 */
public class InMemoryClient implements IClient {

    @Getter
    String clientId;
    @Getter
    String clientSecret;
    @Getter
    String callbackUrl;

    public InMemoryClient(String clientId, String clientSecret, String callbackUrl) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.callbackUrl = callbackUrl;
    }
}
