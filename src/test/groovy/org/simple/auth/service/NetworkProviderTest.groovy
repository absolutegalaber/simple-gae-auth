package org.simple.auth.service

import com.google.api.client.http.HttpResponse
import org.simple.auth.model.AccessToken
import org.simple.auth.model.Network
import org.simple.auth.model.OAuthException
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

/**
 * @author Peter Schneider-Manzell
 */
class NetworkProviderTest extends Specification {

    NetworkProvider underTest
    String networkName = "testnetwork"
    Network dummyNetwork

    void setup() {
        underTest = NetworkProvider.getInstance();
        dummyNetwork = new Network(networkName,null){


            @Override
            String authorizationRedirect(HttpServletRequest request) throws OAuthException {
                return null
            }

            @Override
            AccessToken accessToken(HttpServletRequest callbackRequest) throws OAuthException {
                return null
            }

            @Override
            AccessToken refreshToken(AccessToken token) throws OAuthException {
                return null
            }

            @Override
            HttpResponse post(String url, AccessToken token) throws OAuthException {
                return null
            }

            @Override
            String getProfileUrl() {
                return null
            }

            @Override
            protected HttpResponse executeGet(String url, AccessToken token, boolean withJsonParser) throws OAuthException {
                return null
            }
        }
    }

    def "Add network and get from name"() {
       given:
       underTest.addNetwork(dummyNetwork)

       expect:
       underTest.fromName(networkName) != null


    }


    def "Add network and FromRequestParam"() {
      setup:
      HttpServletRequest mockRequest = Mock(HttpServletRequest)
      mockRequest.getParameter("network") >> networkName

      expect:
      underTest.fromRequestParam(mockRequest) != null
    }

    def "FromRequestParam with NULL param"() {
        setup:
        HttpServletRequest mockRequest = Mock(HttpServletRequest)
        mockRequest.getParameter("network") >> null

        when:
        underTest.fromRequestParam(mockRequest)

        then:
        OAuthException e = thrown()
        e != null
    }

    def "ToSession"() {
       setup:
       HttpServletRequest mockRequest = Mock(HttpServletRequest)
       HttpSession mockSession = Mock(HttpSession)
       mockRequest.getSession() >> mockSession

       when:
       underTest.toSession(mockRequest,dummyNetwork)

       then:
       1* mockSession.setAttribute("com.simple.oauth.model.Network",networkName)
    }

    def "Add network and FromSession"() {
        setup:
        HttpServletRequest mockRequest = Mock(HttpServletRequest)
        HttpSession mockSession = Mock(HttpSession)
        mockRequest.getSession() >> mockSession
        mockSession.getAttribute("com.simple.oauth.model.Network") >> networkName

        when:
        Network network = underTest.fromSession(mockRequest)

        then:
        network != null
    }
    def "FromSession with NULL attribute"() {
        setup:
        HttpServletRequest mockRequest = Mock(HttpServletRequest)
        HttpSession mockSession = Mock(HttpSession)
        mockRequest.getSession() >> mockSession
        mockSession.getAttribute("com.simple.oauth.model.Network") >> null

        when:
        underTest.fromSession(mockRequest)

        then:
        OAuthException e = thrown()
        e != null
    }
}
