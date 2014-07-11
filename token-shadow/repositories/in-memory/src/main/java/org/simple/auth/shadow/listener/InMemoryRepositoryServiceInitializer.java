package org.simple.auth.shadow.listener;

import org.simple.auth.shadow.service.IRepositoryService;
import org.simple.auth.shadow.service.InMemoryRepositoryService;

/**
 * @author Peter Schneider-Manzell
 */
public class InMemoryRepositoryServiceInitializer extends RepositoryServiceInitializer {
    InMemoryRepositoryService repositoryService = new InMemoryRepositoryService();

    @Override
    protected IRepositoryService getRepositoryService() {
        return repositoryService;
    }
}
