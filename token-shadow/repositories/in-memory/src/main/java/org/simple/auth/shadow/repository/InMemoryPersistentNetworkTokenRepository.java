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
    public IPersistentNetworkToken load(String network, Serializable networkUserId) {
        return tokens.get(generateKey(network, networkUserId));
    }

    @Override
    public IPersistentNetworkToken create(Serializable accountId, Serializable networkUserid, INetworkToken networkToken) {
        IPersistentNetworkToken persistentToken = new InMemoryPersistentNetworkToken(networkUserid, accountId, networkToken.getAccessToken(), networkToken.getNetwork(), networkToken.getRefreshToken(), networkToken.getExpiresAt(), networkToken.getNetwork());
        tokens.put(generateKey(persistentToken.getNetwork(), persistentToken.getNetworkUserId()), persistentToken);
        return persistentToken;
    }

    @Override
    public boolean supports(Class networkTokenType) {
        return IPersistentNetworkToken.class.isAssignableFrom(networkTokenType);
    }

    private String generateKey(String network, Serializable networkUserId) {
        StringBuilder sb = new StringBuilder("{");
        sb.append(network);
        sb.append("}-{");
        sb.append(networkUserId);
        sb.append("}");
        return sb.toString();
    }
}
