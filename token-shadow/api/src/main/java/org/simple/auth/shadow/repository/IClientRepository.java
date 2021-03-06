package org.simple.auth.shadow.repository;


import org.simple.auth.model.IClient;

import java.util.Collection;

/**
 * @author Peter Schneider-Manzell
 */
public interface IClientRepository<T extends IClient> {

    T load(String clientId);

    void save(T client);

    Collection<T> all();


}
