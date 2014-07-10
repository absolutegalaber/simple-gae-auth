package org.simple.auth.shadow.repository;

import org.simple.auth.model.INetworkToken;
import org.simple.auth.shadow.model.IPersistentNetworkToken;

import java.io.Serializable;

/**
 * @author Peter Schneider-Manzell
 */
public interface IPersistenNetworkTokenRepository<T extends IPersistentNetworkToken> {

    T load(String network, Serializable networkUserId);

    void save(T networkToken);

    T create(Serializable accountId, Serializable networkUserid, INetworkToken networkToken);

    boolean supports(Class<? extends INetworkToken> networkTokenType);
}
