package org.simple.auth.shadow

import com.google.api.client.auth.openidconnect.IdToken
import groovy.transform.ToString
import org.simple.auth.shadow.model.IPersistentNetworkToken

/**
 * @author Peter Schneider-Manzell
 */
@ToString
class DummyPersistentNetworkToken implements  IPersistentNetworkToken{
    String network;
    Serializable accountId
    Serializable networkUserId
    String accessToken
    String tokenSecret
    String refreshToken
    Date expiresAt
    IdToken idToken
}
