package org.simple.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.simple.auth.model.AccessToken;
import org.simple.auth.model.Network;
import org.simple.auth.model.OAuthException;
import org.simple.auth.service.builder.NetworkConfigurationService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Slf4j
public class NetworkService {
    private static final Map<String, Network> networks = new HashMap<>();

    public void configureNetworks(Iterable<Network> configuredNetworks) {
        for (Network configuredNetwork : configuredNetworks) {
            if (!networks.containsKey(configuredNetwork.getName())) {
                networks.put(configuredNetwork.getName(), configuredNetwork);
            }
        }
        log.info("Configured Networks: {}", networks);
    }

    public void configureNetworks(NetworkConfigurationService configurationService) {
        configureNetworks(configurationService.configureNetworks());
    }

    public Network fromName(String name) throws OAuthException {
        Network network = networks.get(name);
        if (network == null) {
            throw new OAuthException(name + " is not configured");
        }
        return network;
    }

    public Network fromAccessToken(AccessToken accessToken) throws OAuthException {
        return fromName(accessToken.getNetwork());
    }

    public Network fromRequestParam(HttpServletRequest request) throws OAuthException {
        String networkName = request.getParameter("network");
        return fromName(networkName);
    }

    public void toSession(HttpServletRequest request, Network network) {
        request.getSession().setAttribute("com.simple.oauth.model.Network", network.getName());
    }

    public Network fromSession(HttpServletRequest request) throws OAuthException {
        String networkName = (String) request.getSession().getAttribute("com.simple.oauth.model.Network");
        return fromName(networkName);
    }

}
