package org.simple.auth.shadow.model;

import com.google.api.client.auth.openidconnect.IdToken;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

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

    @Override
    public IdToken getIdToken() {
        return null;
    }

    public InMemoryPersistentNetworkToken(Serializable networkUserId, Serializable accountId, String accessToken, String tokenSecret, String refreshToken, Date expiresAt, String network) {
        this.networkUserId = networkUserId;
        this.accountId = accountId;
        this.accessToken = accessToken;
        this.tokenSecret = tokenSecret;
        this.refreshToken = refreshToken;
        this.expiresAt = expiresAt;
        this.network = network;
    }
}
