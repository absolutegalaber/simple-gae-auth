package org.simple.auth.shadow.repository;

import org.simple.auth.model.INetworkToken;
import org.simple.auth.shadow.model.IPersistentNetworkToken;

import java.io.Serializable;

/**
 * @author Peter Schneider-Manzell
 */
public interface IPersistenNetworkTokenRepository<T extends IPersistentNetworkToken,N extends INetworkToken> {

    T load(String network, Serializable networkUserId);


    T create(Serializable accountId, Serializable networkUserid, N networkToken);

}
