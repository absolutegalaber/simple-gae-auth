package org.simple.auth.fakeoauth.servlet

import spock.lang.Specification
import spock.lang.Unroll

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util.Map.Entry

/**
 * @author Peter Schneider-Manzell
 */
class FakeOAuthProviderAccessTokenServletTest extends Specification {

    FakeOAuthProviderAccessTokenServlet underTest
    HttpServletRequest reqMock
    HttpServletResponse respMock
    def requiredParameters = ['client_id': 'DummyClientId', 'client_secret': "dummyClientSecret", 'redirect_uri': "http://localhost:8080", 'code': "123566", 'grant_type': 'authorization_code']

    def setup() {
        underTest = new FakeOAuthProviderAccessTokenServlet()
        reqMock = Mock(HttpServletRequest)
        respMock = Mock(HttpServletResponse)
    }

    def "Do valid post"() {
        given:
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        FakeOauthProviderAuthorizationServlet.addClient(requiredParameters.client_id, requiredParameters.client_secret, requiredParameters.redirect_uri)
        FakeOauthProviderAuthorizationServlet.addValidCode(requiredParameters.code)

        when:
        underTest.doPost(reqMock, respMock)


        then:
        for (Entry parameter : requiredParameters.entrySet()) {
            1 * reqMock.getParameter(parameter.getKey()) >> parameter.getValue()
        }
        1 * respMock.getWriter() >> pw
        sw.toString().contains("\"access_token\":")
    }

    @Unroll
    def "Do post With missing parameter #paramToSkip"() {
        given:
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        FakeOauthProviderAuthorizationServlet.addClient(requiredParameters.client_id, requiredParameters.client_secret, requiredParameters.redirect_uri)
        FakeOauthProviderAuthorizationServlet.addValidCode(requiredParameters.code)

        when:
        underTest.doPost(reqMock, respMock)


        then:
        for (Entry parameter : requiredParameters.entrySet()) {
            if(parameter.getKey().equals(paramToSkip)){
                1 * reqMock.getParameter(parameter.getKey()) >> null
            }
            else {
                1 * reqMock.getParameter(parameter.getKey()) >> parameter.getValue()
            }

        }
        1 * respMock.getWriter() >> pw
        sw.toString().contains("\"error\":\"invalid_request\"")

        where:
        paramToSkip|  _
        'client_id'|  _
        'client_secret'|  _
        'redirect_uri'|  _
        'code'|  _
        'grant_type'|  _
    }

    def "Do post with wrong grant type"() {
        given:
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        FakeOauthProviderAuthorizationServlet.addClient(requiredParameters.client_id, requiredParameters.client_secret, requiredParameters.redirect_uri)
        FakeOauthProviderAuthorizationServlet.addValidCode(requiredParameters.code)

        when:
        underTest.doPost(reqMock, respMock)


        then:
        for (Entry parameter : requiredParameters.entrySet()) {
            if(parameter.getKey().equals("grant_type")){
                1 * reqMock.getParameter(parameter.getKey()) >>"wrong_grant"
            }
            else {
                1 * reqMock.getParameter(parameter.getKey()) >> parameter.getValue()
            }

        }
        1 * respMock.getWriter() >> pw
        sw.toString().contains("\"error\":\"invalid_request\"")
    }
}
