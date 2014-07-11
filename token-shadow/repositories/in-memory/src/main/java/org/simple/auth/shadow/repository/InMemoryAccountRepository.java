package org.simple.auth.shadow.repository;

import org.simple.auth.shadow.model.InMemoryAccount;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Peter Schneider-Manzell
 */
public class InMemoryAccountRepository implements IAccountRepository<InMemoryAccount> {

    ConcurrentHashMap<String, InMemoryAccount> accounts = new ConcurrentHashMap<>();

    @Override
    public InMemoryAccount load(Serializable id) {
        return accounts.get(id);
    }

    @Override
    public void save(InMemoryAccount account) {
        if (account.getId() == null) {
            String id = UUID.randomUUID().toString();
            while (accounts.contains(id)) {
                id = UUID.randomUUID().toString();
            }
            account.setId(id);
            accounts.put(id, account);
        }
    }

    @Override
    public InMemoryAccount createTransient() {
        return new InMemoryAccount();
    }
}
