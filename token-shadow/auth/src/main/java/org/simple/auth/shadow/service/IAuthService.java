package org.simple.auth.shadow.service;

import org.simple.auth.model.IClient;
import org.simple.auth.model.INetworkToken;
import org.simple.auth.model.OAuthException;
import org.simple.auth.shadow.model.IAccount;
import org.simple.auth.shadow.model.IPersistentNetworkToken;
import org.simple.auth.shadow.model.IShadowToken;

/**
 * @author Peter Schneider-Manzell
 */
public interface IAuthService {

    IShadowToken getShadowToken(IClient client, INetworkToken networkToken, String networkUserId) throws OAuthException;

    IShadowToken getShadowToken(String shadowAccessToken);

    IShadowToken loadOrCreateShadowToken(IAccount account, IClient client) throws OAuthException;

    IPersistentNetworkToken createPersistentNetworkToken(IAccount account, INetworkToken networkToken, String networkUserId) throws OAuthException;

    boolean isShadowTokenValid(IShadowToken iShadowToken);
}
