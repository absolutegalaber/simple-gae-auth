package org.simple.auth.shadow.model.v1;

import org.simple.auth.shadow.model.InMemoryPersistentNetworkToken;

import java.io.Serializable;

/**
 * @author Peter Schneider-Manzell
 */
public class InMemoryPersistentOAuth1NetworkToken extends InMemoryPersistentNetworkToken implements IPersistentOAuth1NetworkToken{

    public InMemoryPersistentOAuth1NetworkToken(Serializable networkUserId, Serializable accountId, String accessToken, String network) {
        super(networkUserId, accountId, accessToken, network);
    }
}
