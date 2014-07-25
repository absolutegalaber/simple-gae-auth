package org.simple.auth.model.networks

import com.google.api.client.http.LowLevelHttpRequest
import com.google.api.client.http.LowLevelHttpResponse
import com.google.api.client.testing.http.MockHttpTransport
import com.google.api.client.testing.http.MockLowLevelHttpRequest
import com.google.api.client.testing.http.MockLowLevelHttpResponse
import org.simple.auth.model.ClientConfig
import org.simple.auth.model.IClient
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

/**
 * @author Peter Schneider-Manzell
 */
class OAuth1NetworkTest extends Specification {

    OAuth1Network underTest
    String networkName
    String requestTokenUrl
    String authUrl
    String accessTokenUrl
    IClient clientConfig

    def setup() {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG");
        networkName = "dummy"
        clientConfig = new ClientConfig();
        requestTokenUrl = "http://localhost/oauth1/request_token"
        authUrl = "http://localhost/oauth1/auth"
        accessTokenUrl = "http://localhost/oauth1/access_token"
        underTest = new OAuth1Network(networkName, clientConfig, requestTokenUrl, authUrl, accessTokenUrl)
    }

    def "AuthorizationRedirect"() {
        given:

        String oauthToken = "dummy_oauth_token"
        String oauthTokenSecret = "dummy_oauth_token_secret"
        HttpServletRequest req = Mock(HttpServletRequest)
        HttpSession mockSession = Mock(HttpSession)
        underTest.httpTransport = new MockHttpTransport(){
            @Override
            public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
                return new MockLowLevelHttpRequest() {
                    @Override
                    public LowLevelHttpResponse execute() throws IOException {
                        println("Executing" +url)
                        MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
                        response.setStatusCode(200);
                        response.setContent("oauth_token="+oauthToken+"&oauth_token_secret="+oauthTokenSecret+"&oauth");
                        return response;
                    }
                };
            }
        }

        when:
        String result = underTest.authorizationRedirect(req)

        then:
        req.getSession() >> mockSession
        1*mockSession.setAttribute(networkName+"_req_token",oauthToken)
        1*mockSession.setAttribute(networkName+"_req_token_secret",oauthTokenSecret)
        result == "http://localhost/oauth1/auth?oauth_token="+oauthToken
    }

    def "AccessToken"() {

    }

    def "RefreshToken"() {

    }

    def "ExecuteGet"() {

    }
}
