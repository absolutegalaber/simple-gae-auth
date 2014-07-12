package org.simple.auth.shadow.servlet;

import com.google.common.base.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.simple.auth.model.IClient;
import org.simple.auth.model.Network;
import org.simple.auth.model.OAuthException;
import org.simple.auth.servlet.AbstractAuthorizationRedirect;
import org.simple.auth.shadow.GrantType;
import org.simple.auth.shadow.OAuthRequestParameter;
import org.simple.auth.shadow.service.ClientService;
import org.simple.auth.shadow.service.GrantTypeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Peter Schneider-Manzell
 */
@Slf4j
public class ShadowRedirectServlet extends AbstractAuthorizationRedirect {


    ClientService clientService = new ClientService();
    GrantTypeService grantTypeService = new GrantTypeService();

    @Override
    public void beforeRedirect(HttpServletRequest req, HttpServletResponse resp, Network network) throws OAuthException {
        super.beforeRedirect(req, resp, network);
        if (!network.isProfileAware()) {
            throw new OAuthException(network.getName() + " is not configured to load profiles");
        }
        checkRequiredParameters(req);
        checkAndStoreClientInformation(req);
    }

    private void checkRequiredParameters(HttpServletRequest req) throws OAuthException {
        GrantType grantType = grantTypeService.fromRequest(req);
        for (OAuthRequestParameter oAuthRequestParameter : grantType.getRequiredParameters()) {
            Optional<String> paramValue = oAuthRequestParameter.getValue(req);
            if (!paramValue.isPresent()) {
                throw new OAuthException(String.format("Missing parameter value for parameter [%s]", oAuthRequestParameter.getParamName()));
            }
        }
    }

    @Override
    public void onError(Exception authException, HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.setStatus(HttpStatus.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"invalid_request\",\"error_description\":\"" + authException.getMessage() + "\"}");
            resp.getWriter().flush();
            resp.getWriter().close();

        } catch (IOException e) {
            log.error("Could not write error message to stream!", e);
        }
    }

    private void checkAndStoreClientInformation(HttpServletRequest req) throws OAuthException {
        IClient client = clientService.fromRequest(req);
        clientService.toSession(req, client);
        String redirectUri = clientService.redirectUriFromRequest(req);
        clientService.redirectUriToSession(req, client, redirectUri);
    }


}
