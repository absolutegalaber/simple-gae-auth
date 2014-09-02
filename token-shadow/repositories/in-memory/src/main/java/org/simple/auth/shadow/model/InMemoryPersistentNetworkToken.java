package org.simple.auth.shadow.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Peter Schneider-Manzell
 */
public class InMemoryPersistentNetworkToken implements IPersistentNetworkToken {

    @Getter
    String networkUserId;
    @Getter
    String accountId;
    @Getter
    @Setter
    String accessToken;
    @Getter
    @Setter
    String tokenSecret;
    @Getter
    @Setter
    String refreshToken;
    @Getter
    @Setter
    Date expiresAt;
    @Getter
    String network;

    public InMemoryPersistentNetworkToken(String networkUserId, String accountId, String accessToken, String tokenSecret, String refreshToken, Date expiresAt, String network) {
        this.networkUserId = networkUserId;
        this.accountId = accountId;
        this.accessToken = accessToken;
        this.tokenSecret = tokenSecret;
        this.refreshToken = refreshToken;
        this.expiresAt = expiresAt;
        this.network = network;
    }
}
