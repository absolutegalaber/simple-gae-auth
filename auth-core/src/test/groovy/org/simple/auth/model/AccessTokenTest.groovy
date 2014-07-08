package org.simple.auth.model

import spock.lang.Specification

/**
 * @author Peter Schneider-Manzell
 */
class AccessTokenTest extends Specification {

    AccessToken accessToken

    def "AccessToken requires network"() {
        when:
        accessToken = new AccessToken(null, "testtoken")

        then:
        final NullPointerException exception = thrown()
        exception != null
    }

    def "AccessToken requires token"() {
        when:
        accessToken = new AccessToken("network", null)

        then:
        final NullPointerException exception = thrown()
        exception != null
    }

    def "AccessToken can be created with network and token"() {
        when:
        String networkname = "network"
        String token = "token"
        accessToken = new AccessToken(networkname, token)

        then:
        noExceptionThrown()
        accessToken.getAccessToken() == token
        accessToken.getNetwork() == networkname
    }
}
