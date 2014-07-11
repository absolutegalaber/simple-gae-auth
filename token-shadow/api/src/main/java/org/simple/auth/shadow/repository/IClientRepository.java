package org.simple.auth.shadow.repository;

import org.simple.auth.shadow.model.IClient;

/**
 * @author Peter Schneider-Manzell
 */
public interface IClientRepository<T extends IClient> {

    T load(String clientId);

    void save(T client);
}
