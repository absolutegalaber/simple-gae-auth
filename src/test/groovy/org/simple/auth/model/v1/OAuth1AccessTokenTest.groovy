package org.simple.auth.model.v1

import spock.lang.Specification

/**
 * @author Peter Schneider-Manzell
 */
class OAuth1AccessTokenTest extends Specification {

    OAuth1AccessToken accessToken


    def "OAuth1AccessToken requires tokenSecret"() {
        when:
        accessToken = new OAuth1AccessToken("network", "testtoken",null)

        then:
        final NullPointerException exception = thrown()
        exception != null
    }


    def "OAuth1AccessToken can be created with network, token and secret"() {
        when:
        accessToken = new OAuth1AccessToken("network", "token","secret")

        then:
        noExceptionThrown()
    }
}
