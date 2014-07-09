package org.simple.auth.service.builder;

import org.simple.auth.model.Network;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public interface NetworkConfigurationService {
    public Iterable<Network> configureNetworks();
}
