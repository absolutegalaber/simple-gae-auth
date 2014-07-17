package org.simple.auth.shadow.service;

import org.simple.auth.shadow.repository.*;

/**
 * @author Peter Schneider-Manzell
 */
public class InMemoryRepositoryService implements IRepositoryService {

    final InMemoryShadowTokenRepository shadowTokenRepository;
    final InMemoryClientRepository clientRepository;
    final IPersistenNetworkTokenRepository persistenNetworkTokenRepositories;


    public InMemoryRepositoryService() {
        shadowTokenRepository = new InMemoryShadowTokenRepository();
        clientRepository = new InMemoryClientRepository();
        clientRepository.registerClients();
        persistenNetworkTokenRepositories = new InMemoryPersistentNetworkTokenRepository();
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
    public IPersistenNetworkTokenRepository getPersistenNetworkTokenRepository() {
        return persistenNetworkTokenRepositories;
    }
}
