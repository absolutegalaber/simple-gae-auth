package org.simple.auth.shadow.service;

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
public class AuthService {

    private static IRepositoryService repositoryService;


    public IShadowToken getShadowToken(IClient client, INetworkToken networkToken, String networkUserId, Serializable accountId) throws OAuthException {
        IPersistentNetworkToken persistentNetworkToken = repositoryService.getPersistenNetworkTokenRepository().load(networkToken.getNetwork(), networkUserId);
        if (persistentNetworkToken == null) {
            createPersistentNetworkToken(accountId, networkToken, networkUserId);
        }
        return loadOrCreateShadowToken(accountId, client);
    }

    public IShadowToken getShadowToken(String shadowAccessToken) {
        return repositoryService.getShadowTokenRepository().loadByAccessToken(shadowAccessToken);
    }

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


    public IPersistentNetworkToken createPersistentNetworkToken(Serializable accountId, INetworkToken networkToken, String networkUserId) throws OAuthException {
        return repositoryService.getPersistenNetworkTokenRepository().create(accountId, networkUserId, networkToken);
    }


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
}
