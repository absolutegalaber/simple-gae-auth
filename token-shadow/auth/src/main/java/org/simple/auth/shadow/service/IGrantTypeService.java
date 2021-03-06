package org.simple.auth.shadow.service;

import org.simple.auth.model.OAuthException;
import org.simple.auth.shadow.GrantType;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Peter Schneider-Manzell
 */
public interface IGrantTypeService {

    GrantType fromRequest(HttpServletRequest request) throws OAuthException;
}
