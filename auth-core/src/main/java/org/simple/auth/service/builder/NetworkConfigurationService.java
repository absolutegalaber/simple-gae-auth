package org.simple.auth.service.builder;

import org.simple.auth.model.Network;
import org.simple.auth.model.networks.NetworkIdentifier;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public interface NetworkConfigurationService {
    public Iterable<Network> loadNetworks();

    public Network getNetwork(NetworkIdentifier networkIdentifier);

}
