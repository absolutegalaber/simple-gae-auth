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
public class AuthService implements IAuthService {

    private static IRepositoryService repositoryService;


    @Override
    public IShadowToken getShadowToken(IClient client, INetworkToken networkToken, String networkUserId) throws OAuthException {
        if (log.isDebugEnabled()) {
            log.debug("Loading shadow token for client {}, network token {} and networkUserId {}", client, networkToken, networkUserId);
        }

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

    @Override
    public IShadowToken getShadowToken(String shadowAccessToken) {
        if (log.isDebugEnabled()) {
            log.debug("Loading shadow token by access token");
        }
        return repositoryService.getShadowTokenRepository().loadByAccessToken(shadowAccessToken);
    }

    @Override
    public IShadowToken loadOrCreateShadowToken(IAccount account, IClient client) {
        if (log.isDebugEnabled()) {
            log.debug("Loading or creating shadow token account {} and client {}", account, client);
        }
        IShadowToken token = repositoryService.getShadowTokenRepository().loadByAccountAndClient(account.getId(), client.clientId());
        if (token != null) {
            return token;
        }
        return createShadowToken(account, client);
    }

    private IShadowToken createShadowToken(IAccount account, IClient client) {
        if (log.isDebugEnabled()) {
            log.debug("Creating shadow token account {} and client {}", account, client);
        }
        return repositoryService.getShadowTokenRepository().createShadowToken(account, client);
    }


    @Override
    public IPersistentNetworkToken createPersistentNetworkToken(IAccount account, INetworkToken networkToken, String networkUserId) throws OAuthException {
        if (log.isDebugEnabled()) {
            log.debug("Creating persistent network token with account{}, networkToken {}, networkUserId {}", account, networkToken, networkUserId);
        }
        return repositoryService.getPersistenNetworkTokenRepository().create(account.getId(), networkUserId, networkToken);
    }


    @Override
    public boolean isShadowTokenValid(IShadowToken iShadowToken) {
        if (iShadowToken == null) {
            if(log.isDebugEnabled()) {
                log.debug("ShadowToken is invalid, because no shadow token presented");
            }
            return false;
        } else if (iShadowToken.getExpiresAt().before(new Date())) {
            if(log.isDebugEnabled()) {
                log.debug("ShadowToken is invalid, because it is outdated (expiry {})", iShadowToken.getExpiresAt());
            }
            return false;
        }
        return true;
    }


    public static void setRepositoryService(IRepositoryService repositoryService) {
        AuthService.repositoryService = repositoryService;
    }
}
