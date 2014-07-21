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

    IShadowToken getShadowToken(IClient client, IPersistentNetworkToken networkToken) throws OAuthException;

    IShadowToken getShadowToken(String shadowAccessToken);

    IShadowToken loadOrCreateShadowToken(Serializable accountId, IClient client);

    IPersistentNetworkToken createPersistentNetworkToken(Serializable accountId, INetworkToken networkToken) throws OAuthException;

    boolean isShadowTokenValid(IShadowToken iShadowToken);
}
