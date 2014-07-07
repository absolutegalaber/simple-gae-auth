package org.simple.auth.service

import org.simple.auth.model.networks.DefaultOAuth1Network
import org.simple.auth.model.networks.DefaultOAuth2Network
import org.simple.auth.model.v1.OAuth1ClientConfig
import org.simple.auth.model.v2.OAuth2ClientConfig
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
    OAuth1ClientConfig oAuth1ClientConfig = new OAuth1ClientConfig() {
        @Override
        String clientId() {
            return "clientID"
        }

        @Override
        String secret() {
            return "secret"
        }

        @Override
        String callbackUrl() {
            return "callback"
        }

        @Override
        String state() {
            return "state"
        }
    }
    @Shared
    OAuth2ClientConfig oAuth2ClientConfig = new OAuth2ClientConfig() {
        @Override
        Collection<String> scope() {
            return Arrays.asList("scope1", "scope2")
        }

        @Override
        String clientId() {
            return "clientId"
        }

        @Override
        String secret() {
            return "secret"
        }

        @Override
        String callbackUrl() {
            return "callbackUrl"
        }

        @Override
        String state() {
            return "state"
        }
    }

    def "predefined network factory methods do not throw exceptions"() {
        expect:
        builder.google(oAuth2ClientConfig) != null
        builder.facebook(oAuth2ClientConfig) != null
        builder.linkedIn(oAuth2ClientConfig) != null
        builder.github(oAuth2ClientConfig) != null
        builder.fourSquare(oAuth2ClientConfig) != null
        builder.twitter(oAuth1ClientConfig) != null
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
                .buildOauth2Network(oAuth2ClientConfig)
        then:
        network.name == 'myNewNetwork'
        network.clientConfig == oAuth2ClientConfig
        network.profileUrl == 'profileUrl'
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
        null   | 'authUrl' | 'accesstokenUrl' | oAuth2ClientConfig
        'name' | null      | 'accesstokenUrl' | oAuth2ClientConfig
        'name' | 'authUrl' | null             | oAuth2ClientConfig
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
                .buildOauth1Network(oAuth1ClientConfig)
        then:
        network.name == 'myNewNetwork'
        network.clientConfig == oAuth1ClientConfig
        network.profileUrl == 'profileUrl'
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
        null   | 'authUrl' | 'accesstokenUrl' | 'requestTokenUrl' | oAuth1ClientConfig
        'name' | null      | 'accesstokenUrl' | 'requestTokenUrl' | oAuth1ClientConfig
        'name' | 'authUrl' | 'accesstokenUrl' | null              | oAuth1ClientConfig
        'name' | 'authUrl' | 'accesstokenUrl' | 'requestTokenUrl' | null
    }

}
