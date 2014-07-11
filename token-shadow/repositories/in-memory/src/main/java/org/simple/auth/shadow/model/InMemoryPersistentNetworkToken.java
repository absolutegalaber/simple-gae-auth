package org.simple.auth.shadow.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Peter Schneider-Manzell
 */
public class InMemoryPersistentNetworkToken implements IPersistentNetworkToken {

    @Getter
    Serializable networkUserId;
    @Getter
    Serializable accountId;
    @Getter
    @Setter
    String accessToken;
    @Getter
    String network;

    public InMemoryPersistentNetworkToken(Serializable networkUserId, Serializable accountId, String accessToken, String network) {
        this.networkUserId = networkUserId;
        this.accountId = accountId;
        this.accessToken = accessToken;
        this.network = network;
    }
}
