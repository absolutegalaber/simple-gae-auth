package org.simple.auth.shadow.service;

import lombok.extern.slf4j.Slf4j;
import org.simple.auth.model.IClient;
import org.simple.auth.model.INetworkToken;
import org.simple.auth.model.OAuthException;
import org.simple.auth.shadow.model.IAccount;
import org.simple.auth.shadow.model.IPersistentNetworkToken;
import org.simple.auth.shadow.model.IShadowToken;

import java.util.Date;


/**
 * @author Peter Schneider-Manzell
 */
@Slf4j
public class AuthService {

    private static IRepositoryService repositoryService;


    public IShadowToken getShadowToken(IClient client, INetworkToken networkToken, String networkUserId) throws OAuthException {
        IPersistentNetworkToken persistentNetworkToken = repositoryService.getPersistenNetworkTokenRepository().load(networkToken.getNetwork(), networkUserId);
        IAccount account;
        if (persistentNetworkToken == null) {
            account = repositoryService.getAccountRepository().createTransient();
            repositoryService.getAccountRepository().save(account);
            createPersistentNetworkToken(account, networkToken, networkUserId);
        } else {
            account = repositoryService.getAccountRepository().load(persistentNetworkToken.getAccountId());
        }

        return loadOrCreateShadowToken(account, client);
    }

    public IShadowToken getShadowToken(String shadowAccessToken){
        return repositoryService.getShadowTokenRepository().loadByAccessToken(shadowAccessToken);
    }

    public IShadowToken loadOrCreateShadowToken(IAccount account, IClient client) {
        IShadowToken token = repositoryService.getShadowTokenRepository().loadByAccountAndClient(account.getId(), client.clientId());
        if (token != null) {
            return token;
        }
        return createShadowToken(account, client);
    }

    private IShadowToken createShadowToken(IAccount account, IClient client) {
        return repositoryService.getShadowTokenRepository().createShadowToken(account, client);
    }


    public IPersistentNetworkToken createPersistentNetworkToken(IAccount account, INetworkToken networkToken, String networkUserId) throws OAuthException {
        return repositoryService.getPersistenNetworkTokenRepository().create(account.getId(), networkUserId, networkToken);
    }



    public boolean isShadowTokenValid(IShadowToken iShadowToken) {
        if (iShadowToken == null) {
            log.info("ShadowToken is invalid, because no shadow token presented");
            return false;
        } else if (iShadowToken.getExpiresAt().before(new Date())) {
            log.info("ShadowToken is invalid, because it is outdated (expiry {})",iShadowToken.getExpiresAt());
            return false;
        }
        return true;
    }


    public static void setRepositoryService(IRepositoryService repositoryService) {
        AuthService.repositoryService = repositoryService;
    }
}
