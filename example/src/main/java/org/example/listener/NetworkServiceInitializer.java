package org.example.listener;

import org.example.servlet.ExampleConfigurationService;
import org.simple.auth.listener.AbstractNetworkServiceInitializer;
import org.simple.auth.service.NetworkService;

/**
 * @author Peter Schneider-Manzell
 */
public class NetworkServiceInitializer extends AbstractNetworkServiceInitializer {
    @Override
    public void configureNetworks(NetworkService networkService) {
        networkService.configureNetworks(ExampleConfigurationService.getInstance());
    }
}
