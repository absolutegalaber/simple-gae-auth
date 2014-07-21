package org.example.servlet;

import org.simple.auth.model.BasicUserProfile;
import org.simple.auth.model.INetworkToken;
import org.simple.auth.shadow.model.IPersistentNetworkToken;
import org.simple.auth.shadow.service.AuthService;
import org.simple.auth.shadow.servlet.AbstractShadowCallbackServlet;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class ShadowCallbackServlet extends AbstractShadowCallbackServlet {

    /**
     * Simple implementation checking for existing persistent network tokens of the same network.
     * Might (for example) also do a  lookup into your User repository based ont the network email or look for data within the request or session...
     */
    @Override
    protected Serializable connectWithAccount(INetworkToken accessToken, BasicUserProfile userProfile, HttpServletRequest request) {
        IPersistentNetworkToken existingToken = AuthService.getRepositoryService().getPersistenNetworkTokenRepository().load(accessToken.getNetwork(), userProfile.getNetworkId());
        if (existingToken != null) {
            return existingToken.getAccountId();
        } else {
            return UUID.randomUUID().toString();
        }
    }
}
