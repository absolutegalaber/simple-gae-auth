package org.simple.auth.shadow.service;

import org.simple.auth.model.IClient;
import org.simple.auth.model.INetworkToken;
import org.simple.auth.model.OAuthException;
import org.simple.auth.shadow.model.IAccount;
import org.simple.auth.shadow.model.IPersistentNetworkToken;
import org.simple.auth.shadow.model.IShadowToken;
import org.simple.auth.shadow.repository.IPersistenNetworkTokenRepository;

import java.util.Date;


/**
 * @author Peter Schneider-Manzell
 */
public class AuthService {

    private static IRepositoryService repositoryService;


    public IShadowToken getShadowToken(IClient client, INetworkToken networkToken, String networkUserId) throws OAuthException {
        IPersistentNetworkToken persistentNetworkToken = getMatchingPersistenNetworkTokenRepository(networkToken.getClass()).load(networkToken.getNetwork(), networkUserId);
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
        IPersistenNetworkTokenRepository persistenNetworkTokenRepository = getMatchingPersistenNetworkTokenRepository(networkToken.getClass());
        return persistenNetworkTokenRepository.create(account.getId(), networkUserId, networkToken);
    }

    private IPersistenNetworkTokenRepository getMatchingPersistenNetworkTokenRepository(Class<? extends INetworkToken> networkTokenType) throws OAuthException {
        for (IPersistenNetworkTokenRepository iPersistenNetworkTokenRepository : repositoryService.getPersistenNetworkTokenRepositories()) {
            if (iPersistenNetworkTokenRepository.supports(networkTokenType)) {
                return iPersistenNetworkTokenRepository;
            }
        }
        throw new OAuthException(String.format("No PersistenNetworkTokenRepository for type [%s] defined!",networkTokenType.getSimpleName()));
    }

    public boolean isShadowTokenValid(String shadowAccessToken) {
        IShadowToken iShadowToken = repositoryService.getShadowTokenRepository().loadByAccessToken(shadowAccessToken);
        if (iShadowToken == null) {
            return false;
        } else if (iShadowToken.getExpiresAt().after(new Date())) {
            return false;
        }
        return true;
    }


    public static void setRepositoryService(IRepositoryService repositoryService) {
        AuthService.repositoryService = repositoryService;
    }
}
