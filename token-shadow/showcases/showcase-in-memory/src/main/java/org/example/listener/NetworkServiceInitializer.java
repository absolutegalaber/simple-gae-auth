package org.example.listener;

import org.simple.auth.fakeoauth.FakeOauthClientConfig;
import org.simple.auth.listener.AbstractNetworkServiceInitializer;
import org.simple.auth.model.IClient;
import org.simple.auth.model.Network;
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
        IClient config = new SystemPropertiesOAuth2ClientConfig("fake", propertyPrefix);
        networks.add(new FakeOauthClientConfig(config));
        networkService.configureUnmutableNetworks(networks);
    }
}
