package org.simple.auth.shadow.service;

import org.simple.auth.model.IClient;
import org.simple.auth.model.INetworkToken;
import org.simple.auth.model.OAuthException;
import org.simple.auth.shadow.model.IPersistentNetworkToken;
import org.simple.auth.shadow.model.IShadowToken;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author Peter Schneider-Manzell
 */
public interface IAuthService {

    IShadowToken getShadowToken(IClient client, IPersistentNetworkToken networkToken, String networkUserId,Collection<String> scopes) throws OAuthException;

    IShadowToken getShadowToken(String shadowAccessToken);

    IShadowToken loadOrCreateShadowToken(String accountId, IClient client, Collection<String> scopes) throws OAuthException;

    IPersistentNetworkToken createPersistentNetworkToken(String account, INetworkToken networkToken, String networkUserId) throws OAuthException;

    public IPersistentNetworkToken persist(INetworkToken networkToken, String networkUserId, String accountId);

    boolean isShadowTokenValid(IShadowToken iShadowToken);
}
