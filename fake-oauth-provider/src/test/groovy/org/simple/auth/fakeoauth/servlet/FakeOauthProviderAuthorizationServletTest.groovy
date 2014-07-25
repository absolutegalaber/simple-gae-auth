package org.simple.auth.fakeoauth.servlet

import spock.lang.Specification

import javax.servlet.ServletConfig
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util.Map.Entry

/**
 * @author Peter Schneider-Manzell
 */
class FakeOauthProviderAuthorizationServletTest extends Specification {

    FakeOauthProviderAuthorizationServlet underTest
    String clientId = "dummy_client_id"
    String clientSecret = "dummy_client_secret"
    String redirectUri = "http://localhost/callback"
    String state = "dummy_state"

    Map requiredParameters = ['client_id': clientId, 'redirect_uri': redirectUri, 'state': state]

    def "setup"() {
        underTest = new FakeOauthProviderAuthorizationServlet()
    }


    def "Init"() {
        given:
        ServletConfig configMock = Mock(ServletConfig)



        when:
        FakeOauthProviderAuthorizationServlet.removeClient(clientId)
        underTest.init(configMock)

        then:
        1 * configMock.getInitParameter("clientId") >> clientId
        1 * configMock.getInitParameter("clientSecret") >> clientSecret
        1 * configMock.getInitParameter("redirectUri") >> redirectUri
        FakeOauthProviderAuthorizationServlet.getRedirectURI(clientId) == redirectUri
        FakeOauthProviderAuthorizationServlet.getSecret(clientId) == clientSecret
    }

    def "AddClient"() {
        when:
        FakeOauthProviderAuthorizationServlet.removeClient(clientId)
        FakeOauthProviderAuthorizationServlet.addClient(clientId, clientSecret, redirectUri)

        then:
        FakeOauthProviderAuthorizationServlet.getRedirectURI(clientId) == redirectUri
        FakeOauthProviderAuthorizationServlet.getSecret(clientId) == clientSecret
    }

    def "DoGet"() {
        given:
        FakeOauthProviderAuthorizationServlet.removeClient(clientId)
        FakeOauthProviderAuthorizationServlet.addClient(clientId, clientSecret, redirectUri)
        HttpServletRequest mockReq = Mock(HttpServletRequest)
        HttpServletResponse mockResp = Mock(HttpServletResponse)

        when:
        underTest.doGet(mockReq, mockResp)

        then:
        for (Entry requiredParameter : requiredParameters.entrySet()) {
            1 * mockReq.getParameter(requiredParameter.key) >> requiredParameter.value
        }

        1 * mockResp.sendRedirect(_)
    }

    def "DoGetMissingRequiredParameter"() {
        given:
        FakeOauthProviderAuthorizationServlet.removeClient(clientId)
        FakeOauthProviderAuthorizationServlet.addClient(clientId, clientSecret, redirectUri)
        HttpServletRequest mockReq = Mock(HttpServletRequest)
        HttpServletResponse mockResp = Mock(HttpServletResponse)
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when:
        underTest.doGet(mockReq, mockResp)

        then:
        for (Entry requiredParameter : requiredParameters.entrySet()) {
            if (requiredParameter.key != missingParameter) {
                1 * mockReq.getParameter(requiredParameter.key) >> requiredParameter.value
            }
        }

        mockResp.getWriter() >> pw
        sw.toString() =="{\"error\":\"invalid_request\",\"error_description\":\"parameter "+missingParameter+" is missing!\"}"

        where:
        missingParameter | _
        'client_id'      | _
        'redirect_uri'   | _
    }


    def "GetRedirectURI"() {
        when:
        FakeOauthProviderAuthorizationServlet.removeClient(clientId)
        FakeOauthProviderAuthorizationServlet.addClient(clientId, clientSecret, redirectUri)

        then:
        FakeOauthProviderAuthorizationServlet.getRedirectURI(clientId) == redirectUri
    }

    def "GetSecret"() {
        when:
        FakeOauthProviderAuthorizationServlet.removeClient(clientId)
        FakeOauthProviderAuthorizationServlet.addClient(clientId, clientSecret, redirectUri)

        then:
        FakeOauthProviderAuthorizationServlet.getSecret(clientId) == clientSecret
    }

    def "AddValidCodeAndIsValidCode"() {
        when:
        String validCode = "12345"
        FakeOauthProviderAuthorizationServlet.addValidCode(validCode)

        then:
        FakeOauthProviderAuthorizationServlet.isValidCode(validCode)
    }


}
