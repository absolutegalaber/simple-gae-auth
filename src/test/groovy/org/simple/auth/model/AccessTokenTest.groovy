package org.simple.auth.model

import spock.lang.Specification

/**
 * @author Peter Schneider-Manzell
 */
class AccessTokenTest extends Specification {

    AccessToken token

    def "AccessToken requires network"() {
        when:
        token = new AccessToken(null, "testtoken")

        then:
        final NullPointerException exception = thrown()
        exception != null
    }

    def "AccessToken requires token"() {
        when:
        token = new AccessToken("network", null)

        then:
        final NullPointerException exception = thrown()
        exception != null
    }

    def "AccessToken can be created with network and token"() {
        when:
        token = new AccessToken("network", "token")

        then:
        noExceptionThrown()
    }
}
