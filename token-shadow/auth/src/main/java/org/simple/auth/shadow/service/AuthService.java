package org.simple.auth.shadow.service;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.simple.auth.model.IClient;
import org.simple.auth.model.INetworkToken;
import org.simple.auth.model.OAuthException;
import org.simple.auth.shadow.model.IPersistentNetworkToken;
import org.simple.auth.shadow.model.IShadowToken;

import java.io.Serializable;
import java.util.Date;


/**
 * @author Peter Schneider-Manzell
 */
@Slf4j
public class AuthService implements IAuthService {

    private static IRepositoryService repositoryService;

    @Override
    public IShadowToken getShadowToken(IClient client, IPersistentNetworkToken persistentNetworkToken) throws OAuthException {
        return loadOrCreateShadowToken(persistentNetworkToken.getAccountId(), client);
    }

    @Override
    public IShadowToken getShadowToken(String shadowAccessToken) {
        return repositoryService.getShadowTokenRepository().loadByAccessToken(shadowAccessToken);
    }

    @Override
    public IShadowToken loadOrCreateShadowToken(Serializable accountId, IClient client) {
        IShadowToken token = repositoryService.getShadowTokenRepository().loadByAccountAndClient(accountId, client.clientId());
        if (token != null) {
            return token;
        }
        return createShadowToken(accountId, client);
    }

    private IShadowToken createShadowToken(Serializable accountId, IClient client) {
        return repositoryService.getShadowTokenRepository().createShadowToken(accountId, client);
    }


    @Override
    public IPersistentNetworkToken createPersistentNetworkToken(Serializable accountId, INetworkToken networkToken) throws OAuthException {
        return repositoryService.getPersistenNetworkTokenRepository().create(accountId, networkToken);
    }


    @Override
    public boolean isShadowTokenValid(IShadowToken iShadowToken) {
        if (iShadowToken == null) {
            log.info("ShadowToken is invalid, because no shadow token presented");
            return false;
        } else if (iShadowToken.getExpiresAt().before(new Date())) {
            log.info("ShadowToken is invalid, because it is outdated (expiry {})", iShadowToken.getExpiresAt());
            return false;
        }
        return true;
    }


    public static void setRepositoryService(IRepositoryService repositoryService) {
        AuthService.repositoryService = repositoryService;
    }

    public static IRepositoryService getRepositoryService() {
        return repositoryService;
    }

    public IPersistentNetworkToken persist(INetworkToken networkToken, Serializable accountId) {
        Preconditions.checkNotNull(accountId, "An account id must be provided");
        return getRepositoryService().getPersistenNetworkTokenRepository().create(accountId, networkToken);
    }
}
