package org.simple.auth.shadow.service;

import com.googlecode.objectify.ObjectifyService;
import lombok.extern.slf4j.Slf4j;
import org.simple.auth.model.Network;
import org.simple.auth.model.networks.NetworkIdentifier;
import org.simple.auth.service.builder.NetworkConfigurationService;
import org.simple.auth.shadow.model.OfyNetworkClientConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Peter Schneider-Manzell
 */
@Slf4j
public class OfySocialNetworkFactory implements NetworkConfigurationService {


    private static final Map<NetworkIdentifier, Network> networks = new HashMap<>();
    private final NetworkIdentifier[] supportedNetworks;

    public OfySocialNetworkFactory(NetworkIdentifier[] supportedNetworks) {
        this.supportedNetworks = supportedNetworks;
    }

    @Override
    public Iterable<Network> configureNetworks() {
        if (networks.isEmpty()) {
            configure();
        }
        return networks.values();
    }

    private void configure() {
        for (NetworkIdentifier supportedNetwork : supportedNetworks) {
            OfyNetworkClientConfig networkClientConfig = ObjectifyService.ofy().load().type(OfyNetworkClientConfig.class).id(supportedNetwork.getKey()).now();
            if (networkClientConfig != null) {
                networks.put(supportedNetwork, supportedNetwork.createNetwork(networkClientConfig.getClientConfig()));
            } else {
                log.warn("Skipping network {} because no persistent config was found", supportedNetwork);
            }

        }
    }

}
