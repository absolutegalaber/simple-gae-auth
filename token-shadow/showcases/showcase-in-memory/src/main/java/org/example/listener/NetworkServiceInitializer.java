package org.example.listener;

import org.simple.auth.listener.AbstractNetworkServiceInitializer;
import org.simple.auth.model.Network;
import org.simple.auth.model.networks.v2.Google;
import org.simple.auth.service.NetworkService;
import org.simple.auth.service.builder.v2.SystemPropertiesOAuth2ClientConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Peter Schneider-Manzell
 */
public class NetworkServiceInitializer extends AbstractNetworkServiceInitializer {
    @Override
    public void configureNetworks(NetworkService networkService) {
        String propertyPrefix = "simpleauthexample";
        List<Network> networks = new ArrayList<>();
        networks.add(new Google(new SystemPropertiesOAuth2ClientConfig("google", propertyPrefix)));
        networkService.configureNetworks(networks);
    }
}
