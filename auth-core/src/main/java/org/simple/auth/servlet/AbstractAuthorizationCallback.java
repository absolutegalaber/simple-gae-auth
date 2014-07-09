package org.simple.auth.servlet;

import lombok.extern.slf4j.Slf4j;
import org.simple.auth.model.AccessToken;
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
@Slf4j
public abstract class AbstractAuthorizationCallback extends HttpServlet {
    protected final NetworkService networkService = new NetworkService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Network network = networkService.fromSession(req);
            AccessToken accessToken = network.accessToken(req);
            onAuthorizationSuccess(accessToken, req, resp);
        } catch (OAuthException e) {
            onError(e, req, resp);
        }
    }

    public abstract void onError(OAuthException authException, HttpServletRequest req, HttpServletResponse resp);

    public abstract void onAuthorizationSuccess(AccessToken accessToken, HttpServletRequest req, HttpServletResponse resp);

}
