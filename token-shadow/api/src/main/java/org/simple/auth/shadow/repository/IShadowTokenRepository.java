package org.simple.auth.shadow.repository;

import org.simple.auth.shadow.model.IAccount;
import org.simple.auth.shadow.model.IClient;
import org.simple.auth.shadow.model.IShadowToken;

import java.io.Serializable;


/**
 * @author Peter Schneider-Manzell
 */
public interface IShadowTokenRepository<T extends IShadowToken> {


    T loadByAccessToken(String accessToken);

    T loadByRefreshToken(String refreshToken);

    T loadByAccountAndClient(Serializable accountId, String clientId);

    T createShadowToken(IAccount account, IClient client);
}
