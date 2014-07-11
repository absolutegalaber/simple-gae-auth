package org.simple.auth.shadow.model;

import org.simple.auth.model.INetworkToken;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Peter Schneider-Manzell
 */
public interface IShadowToken extends INetworkToken {

    Serializable getAccountId();

    String getClientId();

    void setAccountId(Serializable id);

    void setAccessToken(String accessToken);

    void setRefreshToken(String accessToken);

    void setExpiresAt(Date expiration);

    void setClientId(String clientId);
}
