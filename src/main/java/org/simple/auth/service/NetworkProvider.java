package org.simple.auth.service;

import org.simple.auth.model.Network;
import org.simple.auth.model.OAuthException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class NetworkProvider {
    private static final NetworkProvider INSTANCE = new NetworkProvider();
    private final Map<String, Network> networks = new HashMap<>();

    private NetworkProvider() {
    }

    public static NetworkProvider getInstance() {
        return INSTANCE;
    }

    public void addNetwork(Network network) {
        networks.put(network.getName(), network);
    }

    public Network fromName(String name) throws OAuthException {
        Network network = networks.get(name);
        if (network == null) {
            throw new OAuthException(network + " is not configured");
        }

        return network;
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
