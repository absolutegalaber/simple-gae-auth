package org.simple.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.simple.auth.model.INetworkToken;
import org.simple.auth.model.Network;
import org.simple.auth.model.OAuthException;
import org.simple.auth.service.builder.NetworkConfigurationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Slf4j
public class NetworkService {
    protected static final Map<String, Network> networks = new HashMap<>();

    public boolean hasNetworksConfigured() {
        return !networks.isEmpty();
    }

    public static void configureNetworks(Iterable<Network> configuredNetworks) {
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
        if (name == null) {
            throw new OAuthException("Cannot load network for name NULL!");
        }

        Network network = networks.get(name);
        if (network == null) {
            throw new OAuthException(name + " is not configured");
        }
        return network;
    }

    public Network fromAccessToken(INetworkToken accessToken) throws OAuthException {
        return fromName(accessToken.getNetwork());
    }

    public Network fromRequestParam(HttpServletRequest request) throws OAuthException {
        String networkName = request.getParameter("network");
        log.info("Detected network name {} in request under parameter name ", networkName, "network");
        return fromName(networkName);
    }

    public void toSession(HttpServletRequest request, Network network, String csrfToken) {
        log.info("Storing network name {} in session under key {}", network.getName(), "com.simple.oauth.model.Network");
        HttpSession session = request.getSession();
        session.setAttribute("com.simple.oauth.model.Network", network.getName());
        session.setAttribute("com.simple.oauth.model.CSRF", csrfToken);
    }

    public Network fromSession(HttpServletRequest request) throws OAuthException {
        String networkName = (String) request.getSession().getAttribute("com.simple.oauth.model.Network");
        log.info("Detected network name {} in session under key {}", networkName, "com.simple.oauth.model.Network");
        String csrfToken = request.getParameter("state");
        if (csrfToken != null) {
            String csrfInSession = (String) request.getSession().getAttribute("com.simple.oauth.model.CSRF");
            if (!csrfInSession.startsWith(csrfToken)) {
                throw new OAuthException("Invalid CSRF Token");
            }
        }
        return fromName(networkName);
    }

}
