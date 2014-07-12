package org.simple.auth.model

import spock.lang.Specification

/**
 * Created by Josip.Mihelko @ Gmail
 */
class AccessTokenTest extends Specification {
    def "OAuth1Token with exception"() {
        when:
        AccessToken.oAuth1Token(name, accesstoken, tokenSecret)
        then:
        thrown(NullPointerException)
        where:
        name   | accesstoken   | tokenSecret
        null   | 'accessToken' | 'tokenSecret'
        'name' | null          | 'tokenSecret'
        'name' | 'AccessToken' | null
    }

    def "OAuth1Token successfull"() {
        when:
        AccessToken token = AccessToken.oAuth1Token('naem', 'accessToken', 'tokenSecret')
        then:
        token != null
        token.version == AccessTokenVersion.OAUTH_1

    }

    def "OAuth2Token with exception"() {
        when:
        AccessToken.oAuth2Token(name, accesstoken, refreshToken, expiresIn)
        then:
        thrown(NullPointerException)
        where:
        name   | accesstoken   | refreshToken  | expiresIn
        null   | 'accessToken' | 'tokenSecret' | null
        'name' | null          | 'tokenSecret' | null
    }

    def "OAuth2Token with optionals null"() {
        when:
        AccessToken token = AccessToken.oAuth2Token('name', 'accesstoken', null, null)
        then:
        token.version == AccessTokenVersion.OAUTH_2
        !token.getRefreshToken()
        !token.getExpiresAt()
    }

    def "OAuth2Token with optionals not null"() {
        when:
        AccessToken token = AccessToken.oAuth2Token('name', 'accesstoken', 'refreshToken', 3600)
        then:
        token.version == AccessTokenVersion.OAUTH_2
        token.getRefreshToken()
        token.getExpiresAt()
        token.getExpiresAt().after(new Date())
    }
}
