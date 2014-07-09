package org.simple.auth.servlet;

import lombok.extern.slf4j.Slf4j;
import org.simple.auth.model.AccessToken;
import org.simple.auth.model.BasicUserProfile;
import org.simple.auth.model.OAuthException;
import org.simple.auth.service.UserProfileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Slf4j
public abstract class AbstractProfileLoadingAuthorizationCallback extends AbstractAuthorizationCallback {
    private final UserProfileService userProfileService = new UserProfileService();

    @Override
    public final void onAuthorizationSuccess(AccessToken accessToken, HttpServletRequest req, HttpServletResponse resp) {
        try {
            BasicUserProfile userProfile = userProfileService.userProfile(accessToken);
            onProfileLoaded(accessToken, userProfile, req, resp);
        } catch (OAuthException e) {
            e.printStackTrace();
        }
    }

    public abstract void onProfileLoaded(AccessToken accessToken, BasicUserProfile userProfile, HttpServletRequest req, HttpServletResponse resp);
}
