package org.simple.auth.servlet;

import org.simple.auth.model.Network;
import org.simple.auth.model.OAuthException;
import org.simple.auth.service.NetworkProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public abstract class AbstractAuthorizationRedirect extends HttpServlet {
    private final NetworkProvider networkProvider = NetworkProvider.getInstance();

    @Override
    public void init() throws ServletException {
        super.init();
        configureNetworks(networkProvider);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Network network = networkProvider.fromRequestParam(req);
            networkProvider.toSession(req, network);
            String authorizationRedirect = network.authorizationRedirect(req);
            resp.sendRedirect(authorizationRedirect);
        } catch (OAuthException e) {
            onError(e, req, resp);
        }
    }

    public abstract void configureNetworks(NetworkProvider provider);

    public abstract void onError(OAuthException authException, HttpServletRequest req, HttpServletResponse resp);
}
