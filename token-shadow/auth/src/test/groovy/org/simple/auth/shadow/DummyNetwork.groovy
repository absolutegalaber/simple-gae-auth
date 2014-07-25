package org.simple.auth.shadow

import com.google.api.client.http.HttpResponse
import org.simple.auth.model.INetworkToken
import org.simple.auth.model.Network
import org.simple.auth.model.OAuthException

import javax.servlet.http.HttpServletRequest

/**
 * @author Peter Schneider-Manzell
 */
class DummyNetwork extends Network{

    public DummyNetwork() {
        super("dummy", null)
    }

    @Override
    String authorizationRedirect(HttpServletRequest request) throws OAuthException {
        throw new IllegalStateException("Not implemented")
    }

    @Override
    INetworkToken accessToken(HttpServletRequest callbackRequest) throws OAuthException {
        throw new IllegalStateException("Not implemented")
    }

    @Override
    INetworkToken refreshToken(INetworkToken token) throws OAuthException {
        throw new IllegalStateException("Not implemented")
    }

    @Override
    HttpResponse post(String url, INetworkToken token) throws OAuthException {
        throw new IllegalStateException("Not implemented")
    }

    @Override
    protected HttpResponse executeGet(String url, INetworkToken token, boolean withJsonParser) throws OAuthException {
        throw new IllegalStateException("Not implemented")
    }
}
