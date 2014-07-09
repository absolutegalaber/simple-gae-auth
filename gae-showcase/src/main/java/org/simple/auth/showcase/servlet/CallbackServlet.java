package org.simple.auth.showcase.servlet;

import lombok.extern.slf4j.Slf4j;
import org.simple.auth.model.AccessToken;
import org.simple.auth.model.BasicUserProfile;
import org.simple.auth.model.OAuthException;
import org.simple.auth.servlet.AbstractProfileLoadingAuthorizationCallback;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Slf4j
public class CallbackServlet extends AbstractProfileLoadingAuthorizationCallback {

    @Override
    public void onError(OAuthException authException, HttpServletRequest req, HttpServletResponse resp) {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/error.jsp");
        req.setAttribute("authException", authException);
        try {
            dispatcher.forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onProfileLoaded(AccessToken accessToken, BasicUserProfile userProfile, HttpServletRequest req, HttpServletResponse resp) {
        log.info("################################### onProfileLoaded() ###################################");
        log.info("AccessToken:       {}", accessToken);
        log.info("UserProfile:       {}", userProfile);
        log.info("onProfileLoaded()");
        log.info("################################### onProfileLoaded() ###################################");
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/profile.jsp");
        req.setAttribute("userProfile", userProfile);
        try {
            dispatcher.forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }

    }
}
