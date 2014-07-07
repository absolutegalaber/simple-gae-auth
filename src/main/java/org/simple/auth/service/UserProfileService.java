package org.simple.auth.service;

import com.google.api.client.json.GenericJson;
import lombok.extern.slf4j.Slf4j;
import org.simple.auth.model.AccessToken;
import org.simple.auth.model.Network;
import org.simple.auth.model.OAuthException;
import org.simple.auth.model.networks.v2.GoogleUserProfile;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Slf4j
public class UserProfileService {

    public void userProfile(Network network, AccessToken accessToken) {
        try {
            GenericJson asJson = network.getAsGenericJson(network.getProfileUrl(), accessToken);
            GoogleUserProfile profile = (GoogleUserProfile) network.getAs(network.getProfileUrl(), accessToken, GoogleUserProfile.class);
            log.info("_______________________________________________________");
            log.info("Json Returned:");
            log.info("{}", asJson);
            log.info("profile: {}", profile);
            log.info("_______________________________________________________");
        } catch (OAuthException e) {
            log.error("OAuthException");
        }
    }
}
