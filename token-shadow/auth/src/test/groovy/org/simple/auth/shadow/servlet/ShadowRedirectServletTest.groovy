package org.simple.auth.shadow.servlet

import org.apache.http.HttpStatus
import org.simple.auth.model.IClient
import org.simple.auth.model.Network
import org.simple.auth.model.OAuthException
import org.simple.auth.shadow.*
import org.simple.auth.shadow.service.IClientService
import org.simple.auth.shadow.service.IGrantTypeService
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author Peter Schneider-Manzell
 */
class ShadowRedirectServletTest extends Specification {

    ShadowRedirectServlet underTest
    IClientService clientServiceMock
    IGrantTypeService grantTypeServiceMock

    def setup() {
        clientServiceMock = Mock(IClientService)
        grantTypeServiceMock = Mock(IGrantTypeService)
        underTest = new ShadowRedirectServlet();
        underTest.clientService = clientServiceMock
        underTest.grantTypeService = grantTypeServiceMock
    }

    def "checkRequiredParametersWithNoParameters"() {
        given:
        HttpServletRequest reqMock = Mock(HttpServletRequest)

        when:

        underTest.checkRequiredParameters(reqMock)

        then:
        1 * grantTypeServiceMock.fromRequest(reqMock) >> GrantType.IMPLICIT
        OAuthException ex = thrown()

    }

    def "checkRequiredParametersWithAllParameters"() {
        given:
        HttpServletRequest reqMock = Mock(HttpServletRequest)

        when:

        underTest.checkRequiredParameters(reqMock)

        then:
        1 * grantTypeServiceMock.fromRequest(reqMock) >> GrantType.IMPLICIT
        for (OAuthRequestParameter oAuthRequestParameter : GrantType.IMPLICIT.requiredParameters) {
            1 * reqMock.getParameter(oAuthRequestParameter.paramName) >> "dummyparamvalue"
        }

        then:
        noExceptionThrown()
    }

    def "OnError"() {
        given:
        HttpServletRequest reqMock = Mock(HttpServletRequest)
        HttpServletResponse respMock = Mock(HttpServletResponse)
        String message = "Dummy exception"
        OAuthException e = new OAuthException(message)
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);


        when:
        underTest.onError(e, reqMock, respMock)

        then:
        1 * respMock.getWriter() >> pw
        1 * respMock.setStatus(HttpStatus.SC_BAD_REQUEST)
        sw.toString() == "{\"error\":\"invalid_request\",\"error_description\":\"" + message + "\"}"

    }

    def "beforeRedirectNotProfileAwareNetwork"() {
        given:
        HttpServletRequest reqMock = Mock(HttpServletRequest)
        HttpServletResponse respMock = Mock(HttpServletResponse)
        Network networkMock = new DummyNetwork()

        when:
        underTest.beforeRedirect(reqMock, respMock, networkMock)

        then:
        OAuthException ex = thrown()

    }

    def "beforeRedirect"() {
        given:
        HttpServletRequest reqMock = Mock(HttpServletRequest)
        HttpServletResponse respMock = Mock(HttpServletResponse)
        Network dummyNetwork = new DummyProfileAwareNetwork()
        IClient dummyClient = new DummyClient()
        String requestedRedirectURI = "http://localhost/callback"


        when:
        underTest.beforeRedirect(reqMock, respMock, dummyNetwork)

        then:
        1 * grantTypeServiceMock.fromRequest(reqMock) >> GrantType.IMPLICIT
        for (OAuthRequestParameter oAuthRequestParameter : GrantType.IMPLICIT.requiredParameters) {
            1 * reqMock.getParameter(oAuthRequestParameter.paramName) >> "param_value_for_" + oAuthRequestParameter.getParamName()
        }
        1 * clientServiceMock.fromRequest(reqMock) >> dummyClient
        1 * clientServiceMock.toSession(reqMock, dummyClient)
        1 * clientServiceMock.redirectUriFromRequest(reqMock) >> requestedRedirectURI
        1 * clientServiceMock.redirectUriToSession(reqMock, dummyClient, requestedRedirectURI)

    }
}
