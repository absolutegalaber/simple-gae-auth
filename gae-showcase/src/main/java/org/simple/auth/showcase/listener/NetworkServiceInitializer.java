package org.simple.auth.showcase.listener;

import org.simple.auth.listener.AbstractNetworkServiceInitializer;
import org.simple.auth.service.NetworkService;
import org.simple.auth.showcase.service.factory.SocialNetworkFactory;

/**
 * @author Peter Schneider-Manzell
 */
public class NetworkServiceInitializer extends AbstractNetworkServiceInitializer {

    @Override
    public void configureNetworks(NetworkService networkService) {
        networkService.configureUnmutableNetworks(new SocialNetworkFactory().loadNetworks());
    }
}
