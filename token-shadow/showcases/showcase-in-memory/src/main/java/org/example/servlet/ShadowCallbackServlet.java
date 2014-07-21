package org.example.servlet;

import org.simple.auth.model.BasicUserProfile;
import org.simple.auth.model.INetworkToken;
import org.simple.auth.shadow.servlet.AbstractShadowCallbackServlet;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class ShadowCallbackServlet extends AbstractShadowCallbackServlet {

    @Override
    protected Serializable connectWithAccount(INetworkToken accessToken, BasicUserProfile userProfile, HttpServletRequest request) {
        return UUID.randomUUID().toString();
    }
}
