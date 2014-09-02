package org.simple.auth.service;

import org.simple.auth.model.Network;
import org.simple.auth.model.OAuthException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Peter Schneider-Manzell
 */
public interface INetworkService {
    Network fromSession(HttpServletRequest req) throws OAuthException;
}
