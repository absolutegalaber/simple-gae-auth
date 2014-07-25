package org.simple.auth.shadow.service;

import org.simple.auth.model.IClient;
import org.simple.auth.model.INetworkToken;
import org.simple.auth.model.OAuthException;
import org.simple.auth.shadow.model.IPersistentNetworkToken;
import org.simple.auth.shadow.model.IShadowToken;

import java.io.Serializable;

/**
 * @author Peter Schneider-Manzell
 */
public interface IAuthService {

    IShadowToken getShadowToken(IClient client, IPersistentNetworkToken networkToken, String networkUserId) throws OAuthException;

    IShadowToken getShadowToken(String shadowAccessToken);

    IShadowToken loadOrCreateShadowToken(Serializable account, IClient client) throws OAuthException;

    IPersistentNetworkToken createPersistentNetworkToken(Serializable account, INetworkToken networkToken, String networkUserId) throws OAuthException;

    public IPersistentNetworkToken persist(INetworkToken networkToken, String networkUserId, Serializable accountId);

    boolean isShadowTokenValid(IShadowToken iShadowToken);
}
