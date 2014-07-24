package org.simple.auth.fakeoauth

import spock.lang.Specification

/**
 * @author Peter Schneider-Manzell
 */
class FakeOauthClientConfigTest extends Specification {

    def "Sets the correct values"(){
        when:
        FakeOauthClientConfig config = new FakeOauthClientConfig();

        then:
        config.name == "fake"
    }
}
