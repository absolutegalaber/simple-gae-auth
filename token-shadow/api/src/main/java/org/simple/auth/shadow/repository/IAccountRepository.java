package org.simple.auth.shadow.repository;

import org.simple.auth.shadow.model.IAccount;

import java.io.Serializable;

/**
 * @author Peter Schneider-Manzell
 */
public interface IAccountRepository<T extends IAccount> {

    T load(Serializable id);

    void save(T account);

    T createTransient();
}
