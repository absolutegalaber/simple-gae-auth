package org.simple.auth.shadow.repository.v2;

import org.simple.auth.model.INetworkToken;
import org.simple.auth.model.v2.IOAuth2NetworkToken;
import org.simple.auth.shadow.model.v1.InMemoryPersistentOAuth1NetworkToken;
import org.simple.auth.shadow.model.v2.InMemoryPersistentOAuth2NetworkToken;
import org.simple.auth.shadow.repository.IPersistenNetworkTokenRepository;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Peter Schneider-Manzell
 */
public class InMemoryPersistentOAuth2NetworkTokenRepository implements IPersistenNetworkTokenRepository<InMemoryPersistentOAuth2NetworkToken,IOAuth2NetworkToken> {

    Map<String, InMemoryPersistentOAuth2NetworkToken> tokens = new ConcurrentHashMap<>();

    @Override
    public InMemoryPersistentOAuth2NetworkToken load(String network, Serializable networkUserId) {
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
    public InMemoryPersistentOAuth2NetworkToken create(Serializable accountId, Serializable networkUserid, IOAuth2NetworkToken networkToken) {
        InMemoryPersistentOAuth2NetworkToken persistentToken = new InMemoryPersistentOAuth2NetworkToken(networkUserid,accountId,networkToken.getAccessToken(),networkToken.getNetwork(),networkToken.getRefreshToken(),networkToken.getExpiresAt());
        tokens.put(generateKey(persistentToken.getNetwork(), persistentToken.getNetworkUserId()), persistentToken);
        return persistentToken;
    }

    @Override
    public boolean supports(Class<? extends INetworkToken> networkTokenType) {
        return  IOAuth2NetworkToken.class.isAssignableFrom(networkTokenType);
    }
}
