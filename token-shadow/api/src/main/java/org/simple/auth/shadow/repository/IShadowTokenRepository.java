package org.simple.auth.shadow.repository;

import org.simple.auth.model.IClient;
import org.simple.auth.shadow.model.IShadowToken;

import java.io.Serializable;


/**
 * @author Peter Schneider-Manzell
 */
public interface IShadowTokenRepository<T extends IShadowToken> {


    T loadByAccessToken(String accessToken);

    T loadByRefreshToken(String refreshToken);

    T loadByAccountAndClient(Serializable accountId, String clientId);

    T createShadowToken(Serializable accountId, IClient client);
}
