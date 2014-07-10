package org.simple.auth.model.v2;

import org.simple.auth.model.INetworkToken;

import java.util.Date;

/**
 * @author Peter Schneider-Manzell
 */
public interface IOAuth2NetworkToken extends INetworkToken {

    String getRefreshToken();

    Date getExpiresAt();

}
