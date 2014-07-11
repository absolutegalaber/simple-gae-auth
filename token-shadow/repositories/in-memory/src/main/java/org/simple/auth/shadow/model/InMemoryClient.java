package org.simple.auth.shadow.model;

import org.simple.auth.model.ClientConfig;

/**
 * @author Peter Schneider-Manzell
 */
public class InMemoryClient extends ClientConfig {

    public InMemoryClient(String clientId, String secret, String callbackURL) {
        super.setClientId(clientId);
        super.setSecret(secret);
        super.setCallbackUrl(callbackURL);
    }
}
