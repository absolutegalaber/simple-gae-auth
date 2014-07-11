package org.simple.auth.shadow.service;

import org.simple.auth.shadow.repository.*;
import org.simple.auth.shadow.repository.v1.InMemoryPersistentOAuth1NetworkTokenRepository;
import org.simple.auth.shadow.repository.v2.InMemoryPersistentOAuth2NetworkTokenRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Peter Schneider-Manzell
 */
public class InMemoryRepositoryService implements IRepositoryService {

    final InMemoryAccountRepository accountRepository;
    final InMemoryShadowTokenRepository shadowTokenRepository;
    final InMemoryClientRepository clientRepository;
    final List<IPersistenNetworkTokenRepository> persistenNetworkTokenRepositories = new ArrayList<>();


    public InMemoryRepositoryService() {
        accountRepository = new InMemoryAccountRepository();
        shadowTokenRepository = new InMemoryShadowTokenRepository();
        clientRepository = new InMemoryClientRepository();
        clientRepository.registerClients();
        persistenNetworkTokenRepositories.add(new InMemoryPersistentOAuth1NetworkTokenRepository());
        persistenNetworkTokenRepositories.add(new InMemoryPersistentOAuth2NetworkTokenRepository());
    }

    @Override
    public IAccountRepository getAccountRepository() {
        return accountRepository;
    }

    @Override
    public IShadowTokenRepository getShadowTokenRepository() {
        return shadowTokenRepository;
    }

    @Override
    public IClientRepository getClientRepository() {
        return clientRepository;
    }

    @Override
    public List<IPersistenNetworkTokenRepository> getPersistenNetworkTokenRepositories() {
        return persistenNetworkTokenRepositories;
    }
}
