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
    public IPersistentNetworkToken load(String network, String networkUserId) {
        return tokens.get(generateKey(network, networkUserId));
    }

    @Override
    public IPersistentNetworkToken create(String accountId, String networkUserid, INetworkToken networkToken) {
        IPersistentNetworkToken persistentToken = new InMemoryPersistentNetworkToken(networkUserid, accountId, networkToken.getAccessToken(), networkToken.getNetwork(), networkToken.getRefreshToken(), networkToken.getExpiresAt(), networkToken.getNetwork());
        tokens.put(generateKey(persistentToken.getNetwork(), persistentToken.getNetworkUserId()), persistentToken);
        return persistentToken;
    }


    private String generateKey(String network, String networkUserId) {
        StringBuilder sb = new StringBuilder("{");
        sb.append(network);
        sb.append("}-{");
        sb.append(networkUserId);
        sb.append("}");
        return sb.toString();
    }
}
