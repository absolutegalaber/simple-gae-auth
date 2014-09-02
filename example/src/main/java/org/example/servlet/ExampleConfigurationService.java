package org.example.servlet;

import lombok.extern.slf4j.Slf4j;
import org.simple.auth.model.ClientConfig;
import org.simple.auth.model.Network;
import org.simple.auth.model.networks.NetworkIdentifier;
import org.simple.auth.model.networks.v2.Facebook;
import org.simple.auth.model.networks.v2.Google;
import org.simple.auth.service.builder.NetworkConfigurationService;
import org.simple.auth.service.builder.v2.SystemPropertiesOAuth2ClientConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Slf4j
public class ExampleConfigurationService implements NetworkConfigurationService {
    private static final ExampleConfigurationService instance = new ExampleConfigurationService();
    private List<Network> networks = new ArrayList<>();

    private ExampleConfigurationService() {
        String propertyPrefix = "simpleauthexample";
        networks.add(new Google(new SystemPropertiesOAuth2ClientConfig("google", propertyPrefix)));
        networks.add(new Facebook(new SystemPropertiesOAuth2ClientConfig("facebook", propertyPrefix)));
    }

    public static ExampleConfigurationService getInstance() {
        return instance;
    }

    @Override
    public Iterable<Network> loadNetworks() {
        return networks;
    }

    @Override
    public Network getNetwork(NetworkIdentifier networkIdentifier) {
        for (Network network : loadNetworks()) {
            if (network.getName().equals(networkIdentifier.getKey())) {
                return network;
            }
        }
        return null;
    }

}
