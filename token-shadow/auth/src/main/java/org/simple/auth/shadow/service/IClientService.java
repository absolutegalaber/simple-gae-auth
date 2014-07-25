package org.simple.auth.shadow.service;

import org.simple.auth.model.IClient;
import org.simple.auth.model.OAuthException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Peter Schneider-Manzell
 */
public interface IClientService {

    IClient fromRequest(HttpServletRequest req) throws OAuthException;

    String redirectUriFromRequest(HttpServletRequest req) throws OAuthException;

    void toSession(HttpServletRequest req, IClient client);

    IClient fromSession(HttpServletRequest req) throws OAuthException;

    void redirectUriToSession(HttpServletRequest req, IClient client, String redirectUri);

    String redirectUriFromSession(IClient client, HttpServletRequest req);
}
