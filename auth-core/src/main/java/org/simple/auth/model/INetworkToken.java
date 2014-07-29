package org.simple.auth.model;

import com.google.api.client.auth.openidconnect.IdToken;

import java.util.Date;

/**
 * @author Peter Schneider-Manzell
 */
public interface INetworkToken {
    String getAccessToken();

    String getTokenSecret();

    String getNetwork();

    String getRefreshToken();

    Date getExpiresAt();

    IdToken getIdToken();
}
