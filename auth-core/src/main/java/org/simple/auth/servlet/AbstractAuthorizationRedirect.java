package org.simple.auth.servlet;

import org.simple.auth.model.Network;
import org.simple.auth.model.OAuthException;
import org.simple.auth.service.NetworkService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public abstract class AbstractAuthorizationRedirect extends HttpServlet {
    private NetworkService networkService = new NetworkService();

    @Override
    public void init() throws ServletException {
        super.init();
        configureNetworks(networkService);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Network network = networkService.fromRequestParam(req);
            networkService.toSession(req, network);
            String authorizationRedirect = network.authorizationRedirect(req);
            beforeRedirect(req,resp,network);
            resp.sendRedirect(authorizationRedirect);
        } catch (Exception e) {
            onError(e, req, resp);
        }
    }


    public void beforeRedirect(HttpServletRequest req, HttpServletResponse resp, Network network) throws OAuthException {
    }


    public abstract void configureNetworks(NetworkService networkService);

    public abstract void onError(Exception authException, HttpServletRequest req, HttpServletResponse resp);
}
