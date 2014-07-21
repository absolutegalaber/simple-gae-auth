package org.example.servlet;

import org.simple.auth.model.BasicUserProfile;
import org.simple.auth.model.INetworkToken;
import org.simple.auth.shadow.servlet.AbstractShadowCallbackServlet;

import java.io.Serializable;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class ShadowCallbackServlet extends AbstractShadowCallbackServlet {

    @Override
    protected Serializable connectWithAccount(INetworkToken accessToken, BasicUserProfile userProfile) {
        return null;
    }
}
