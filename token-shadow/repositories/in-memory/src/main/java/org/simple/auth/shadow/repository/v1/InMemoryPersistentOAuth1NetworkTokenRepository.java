package org.simple.auth.shadow.repository.v1;

import org.simple.auth.model.INetworkToken;
import org.simple.auth.model.v1.IOAuth1NetworkToken;
import org.simple.auth.shadow.model.v1.InMemoryPersistentOAuth1NetworkToken;
import org.simple.auth.shadow.repository.IPersistenNetworkTokenRepository;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Peter Schneider-Manzell
 */
public class InMemoryPersistentOAuth1NetworkTokenRepository implements IPersistenNetworkTokenRepository<InMemoryPersistentOAuth1NetworkToken, IOAuth1NetworkToken> {

    Map<String, InMemoryPersistentOAuth1NetworkToken> tokens = new ConcurrentHashMap<>();

    @Override
    public InMemoryPersistentOAuth1NetworkToken load(String network, Serializable networkUserId) {
        return tokens.get(generateKey(network, networkUserId));
    }

    private String generateKey(String network, Serializable networkUserId) {
        StringBuilder sb = new StringBuilder("{");
        sb.append(network);
        sb.append("}-{");
        sb.append(networkUserId);
        sb.append("}");
        return sb.toString();
    }


    @Override
    public InMemoryPersistentOAuth1NetworkToken create(Serializable accountId, Serializable networkUserid, IOAuth1NetworkToken networkToken) {
        InMemoryPersistentOAuth1NetworkToken persistentToken = new InMemoryPersistentOAuth1NetworkToken(networkUserid, accountId, networkToken.getAccessToken(), networkToken.getNetwork());
        tokens.put(generateKey(persistentToken.getNetwork(), persistentToken.getNetworkUserId()), persistentToken);
        return persistentToken;
    }

    @Override
    public boolean supports(Class<? extends INetworkToken> networkTokenType) {
        return  IOAuth1NetworkToken.class.isAssignableFrom(networkTokenType);
    }
}
