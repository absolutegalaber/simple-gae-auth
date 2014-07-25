package org.simple.auth.shadow

import groovy.transform.ToString
import org.simple.auth.shadow.model.IShadowToken

/**
 * @author Peter Schneider-Manzell
 */
@ToString
class DummyShadowToken implements IShadowToken {
    Serializable accountId;
    String clientId
    String accessToken
    String refreshToken
    String tokenSecret
    String network
    Date expiresAt


}
