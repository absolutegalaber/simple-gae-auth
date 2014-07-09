package org.simple.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.simple.auth.model.*;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Slf4j
public class UserProfileService {
    private NetworkService networkService = new NetworkService();

    public BasicUserProfile userProfile(AccessToken accessToken) throws OAuthException {
        Network network = networkService.fromAccessToken(accessToken);
        if (!network.isProfileAware()) {
            throw new OAuthException(network.getName() + " is not configured to load profiles");
        }
        ProfileAware profileAware = (ProfileAware) network;
        BasicUserProfile userProfile = profileAware.loadUserProfile(accessToken);
        userProfile.setNetworkName(network.getName());
        return userProfile;
    }
}
