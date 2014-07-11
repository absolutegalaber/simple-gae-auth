package org.simple.auth.service

import org.simple.auth.model.ClientConfig
import org.simple.auth.model.networks.DefaultOAuth1Network
import org.simple.auth.model.networks.DefaultOAuth2Network
import org.simple.auth.service.builder.NetworkBuilder
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Unroll
class NetworkBuilderTest extends Specification {
    NetworkBuilder builder = new NetworkBuilder();
    @Shared
    ClientConfig clientConfig = new ClientConfig();

    def "predefined network factory methods do not throw exceptions"() {
        expect:
        builder.google(clientConfig) != null
        builder.facebook(clientConfig) != null
        builder.linkedIn(clientConfig) != null
        builder.github(clientConfig) != null
        builder.fourSquare(clientConfig) != null
        builder.twitter(clientConfig) != null
    }

    def "manual creation of oauth2 network with all data"() {
        when:
        DefaultOAuth2Network network = builder.name("myNewNetwork")
                .authorizeUrl("authorizeUrl")
                .accessTokenUrl("accessTokenUrl")
                .profileUrl("profileUrl")
                .addDefaultHeader("some", "header")
                .addDefaultQueryParam("some", "param")
                .isAccessTokenResponseJson(false)
                .buildOauth2Network(clientConfig)
        then:
        network.name == 'myNewNetwork'
        network.clientConfig == clientConfig
    }

    def "manual creation of oauth2 network with missing data"() {
        when:
        builder.name(name)
                .authorizeUrl(authUrl)
                .accessTokenUrl(accessTokenUrl)
                .profileUrl("profileUrl")
                .buildOauth2Network(config)
        then:
        NullPointerException e = thrown(NullPointerException)
        e != null
        e.message != null

        where:
        name   | authUrl   | accessTokenUrl   | config
        null   | 'authUrl' | 'accesstokenUrl' | clientConfig
        'name' | null      | 'accesstokenUrl' | clientConfig
        'name' | 'authUrl' | null             | clientConfig
        'name' | 'authUrl' | 'accesstokenUrl' | null
    }

    def "manual creation of oauth1 network with all data"() {
        when:
        DefaultOAuth1Network network = builder.name("myNewNetwork")
                .requestTokenUrl("requestTokenUrl")
                .authorizeUrl("authorizeUrl")
                .accessTokenUrl("accessTokenUrl")
                .profileUrl("profileUrl")
                .addDefaultHeader("some", "header")
                .addDefaultQueryParam("some", "param")
                .isAccessTokenResponseJson(true)
                .buildOauth1Network(clientConfig)
        then:
        network.name == 'myNewNetwork'
        network.clientConfig == clientConfig
    }

    def "manual creation of oauth1 network with missing data"() {
        when:
        builder.name(name)
                .authorizeUrl(authUrl)
                .accessTokenUrl(accessTokenUrl)
                .accessTokenUrl(requestTokenUrl)
                .profileUrl("profileUrl")
                .buildOauth1Network(config)
        then:
        NullPointerException e = thrown(NullPointerException)
        e != null
        e.message != null

        where:
        name   | authUrl   | accessTokenUrl   | requestTokenUrl   | config
        null   | 'authUrl' | 'accesstokenUrl' | 'requestTokenUrl' | clientConfig
        'name' | null      | 'accesstokenUrl' | 'requestTokenUrl' | clientConfig
        'name' | 'authUrl' | 'accesstokenUrl' | null              | clientConfig
        'name' | 'authUrl' | 'accesstokenUrl' | 'requestTokenUrl' | null
    }

}
