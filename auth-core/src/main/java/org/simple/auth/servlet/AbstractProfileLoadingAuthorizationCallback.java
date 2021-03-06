package org.simple.auth.servlet;

import lombok.extern.slf4j.Slf4j;
import org.simple.auth.model.BasicUserProfile;
import org.simple.auth.model.INetworkToken;
import org.simple.auth.model.OAuthException;
import org.simple.auth.service.UserProfileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Slf4j
public abstract class AbstractProfileLoadingAuthorizationCallback extends AbstractAuthorizationCallback {
    protected UserProfileService userProfileService = new UserProfileService();

    @Override
    public final void onAuthorizationSuccess(INetworkToken accessToken, HttpServletRequest req, HttpServletResponse resp) {
        try {
            log.info("Trying to load user profile....");
            BasicUserProfile userProfile = userProfileService.userProfile(accessToken);
            log.info("Finished loading user profile....");
            onProfileLoaded(accessToken, userProfile, req, resp);
        } catch (Exception e) {
            onError(e, req, resp);
        }
    }

    public abstract void onProfileLoaded(INetworkToken accessToken, BasicUserProfile userProfile, HttpServletRequest req, HttpServletResponse resp) throws OAuthException, IOException;
}
