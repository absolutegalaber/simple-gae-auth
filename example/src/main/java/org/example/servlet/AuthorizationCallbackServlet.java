package org.example.servlet;

import lombok.extern.slf4j.Slf4j;
import org.simple.auth.model.AccessToken;
import org.simple.auth.model.BasicUserProfile;
import org.simple.auth.model.OAuthException;
import org.simple.auth.servlet.AbstractProfileLoadingAuthorizationCallback;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Peter Schneider-Manzell
 */
@Slf4j
public class AuthorizationCallbackServlet extends AbstractProfileLoadingAuthorizationCallback {
    @Override
    public void onError(OAuthException authException, HttpServletRequest req, HttpServletResponse resp) {
        log.error("Error during authorization callback!", authException);
    }

    @Override
    public void onProfileLoaded(AccessToken accessToken, BasicUserProfile userProfile, HttpServletRequest req, HttpServletResponse resp) {
        log.info("AccessToken: {}", accessToken);
        log.info("UserProfile: {}", userProfile);
        printProfile(userProfile, resp);
    }

    private void printProfile(BasicUserProfile userProfile, HttpServletResponse httpResponse) {
        try {
            PrintWriter writer = httpResponse.getWriter();
            writer.println("Profile from: " + userProfile.getNetworkName());
            writer.println("Network ID:   " + userProfile.getNetworkId());
            writer.println("Email:        " + userProfile.getEmail());
            writer.println("First Name:   " + userProfile.getFirstName());
            writer.println("Last Name:    " + userProfile.getLastName());
            writer.println("Full Name:    " + userProfile.getName());
            writer.println("Gender:       " + userProfile.getGender());
            writer.println("Locale:       " + userProfile.getLocale());
            writer.println("Picture Url:  " + userProfile.getPictureUrl());
        } catch (IOException e) {
            log.error("WTF?", e);
        }
    }
}
