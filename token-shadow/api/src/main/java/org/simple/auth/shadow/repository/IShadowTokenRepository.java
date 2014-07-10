package org.simple.auth.shadow.repository;

import org.simple.auth.shadow.model.IAccount;
import org.simple.auth.shadow.model.IClient;
import org.simple.auth.shadow.model.IShadowToken;

import java.io.Serializable;


/**
 * @author Peter Schneider-Manzell
 */
public interface IShadowTokenRepository<T extends IShadowToken> {


    void save(T shadowToken);

    T loadByAccessToken(String accessToken);

    T loadByRefreshToken(String refreshToken);

    T loadByAccountAndClient(Serializable accountId, String clientId);


    boolean isAccessTokenUsed(String accessToken);

    boolean isRefreshTokenUsed(String refreshToken);

    T createShadowToken(IAccount account, IClient client);
}
