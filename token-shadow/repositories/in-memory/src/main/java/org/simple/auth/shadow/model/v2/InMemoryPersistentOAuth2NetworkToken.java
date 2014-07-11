package org.simple.auth.shadow.model.v2;

import lombok.Getter;
import org.simple.auth.shadow.model.InMemoryPersistentNetworkToken;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Peter Schneider-Manzell
 */
public class InMemoryPersistentOAuth2NetworkToken extends InMemoryPersistentNetworkToken implements IPersistentOAuth2NetworkToken {

    @Getter
    String refreshToken;

    @Getter
    Date expiresAt;

    public InMemoryPersistentOAuth2NetworkToken(Serializable networkUserId, Serializable accountId, String accessToken, String network, String refreshToken, Date expiresAt) {
        super(networkUserId, accountId, accessToken, network);
        this.refreshToken = refreshToken;
        this.expiresAt = expiresAt;
    }
}
