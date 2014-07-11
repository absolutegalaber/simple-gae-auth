package org.simple.auth.shadow.service;

import org.simple.auth.shadow.repository.IAccountRepository;
import org.simple.auth.shadow.repository.IClientRepository;
import org.simple.auth.shadow.repository.IPersistenNetworkTokenRepository;
import org.simple.auth.shadow.repository.IShadowTokenRepository;

import java.util.List;

/**
 * @author Peter Schneider-Manzell
 */
public interface IRepositoryService {

    IAccountRepository getAccountRepository();

    IShadowTokenRepository getShadowTokenRepository();

    IClientRepository getClientRepository();

    List<IPersistenNetworkTokenRepository> getPersistenNetworkTokenRepositories();


}
