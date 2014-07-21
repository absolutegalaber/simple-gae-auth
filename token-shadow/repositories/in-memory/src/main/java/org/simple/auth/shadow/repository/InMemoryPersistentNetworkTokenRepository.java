package org.simple.auth.shadow.repository;

import org.simple.auth.model.INetworkToken;
import org.simple.auth.shadow.model.IPersistentNetworkToken;
import org.simple.auth.shadow.model.InMemoryPersistentNetworkToken;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class InMemoryPersistentNetworkTokenRepository implements IPersistenNetworkTokenRepository {

    Map<String, IPersistentNetworkToken> tokens = new ConcurrentHashMap<>();

    @Override
    public IPersistentNetworkToken load(String network, Serializable accountId) {
        return tokens.get(generateKey(network, accountId));
    }

    @Override
    public IPersistentNetworkToken create(Serializable accountId, INetworkToken networkToken) {
        IPersistentNetworkToken persistentToken = new InMemoryPersistentNetworkToken(accountId, networkToken.getAccessToken(), networkToken.getNetwork(), networkToken.getRefreshToken(), networkToken.getExpiresAt(), networkToken.getNetwork());
        tokens.put(generateKey(persistentToken.getNetwork(), accountId), persistentToken);
        return persistentToken;
    }


    private String generateKey(String network, Serializable accountId) {
        return "{" + network + "}-{" + accountId + "}";
    }
}
