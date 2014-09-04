package org.simple.auth.shadow.filter;

import com.google.common.base.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.simple.auth.shadow.model.IShadowToken;
import org.simple.auth.shadow.service.AuthService;
import org.simple.auth.shadow.service.IAuthService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * @author Peter Schneider-Manzell
 */
@Slf4j
public class ShadowTokenFilter implements Filter {

    protected static final String REQ_ACCOUNT_ID_KEY = "org.simple.auth.shadow.filter.ShadowTokenFilter_account_id";
    protected static final String REQ_CLIENT_ID_KEY = "org.simple.auth.shadow.filter.ShadowTokenFilter_client_id";
    protected static final String REQ_SCOPES_KEY = "org.simple.auth.shadow.filter.ShadowTokenFilter_scopes";
    private IAuthService authService = new AuthService();
    private static final String BEARER_PREFIX = "Bearer ";
    private static final int ACCESS_TOKEN_START_INDEX = BEARER_PREFIX.length();


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Optional<String> authorizationHeader = extractAuthorizationHeader(((HttpServletRequest) request));
        Optional<String> accessToken = extractAccessToken(authorizationHeader);
        IShadowToken iShadowToken = null;
        if (accessToken.isPresent()) {
            log.info("Found access token {}", accessToken.get());
            iShadowToken = authService.getShadowToken(accessToken.get());
            log.info("Found shadow token {}", iShadowToken);
        }
        if (authService.isShadowTokenValid(iShadowToken)) {
            setClientId(request, iShadowToken.getClientId());
            setAccountId(request, iShadowToken.getAccountId());
            setScopes(request, iShadowToken.getScopes());
            chain.doFilter(request, response);
        } else {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setStatus(HttpStatus.SC_FORBIDDEN);
            PrintWriter printWriter = httpServletResponse.getWriter();
            printWriter.write("{\"error\":\"access_denied\"}");
            printWriter.flush();
            printWriter.close();
            return;
        }
    }


    protected Optional<String> extractAuthorizationHeader(HttpServletRequest request) {
        return Optional.fromNullable(request.getHeader("Authorization"));
    }


    protected Optional<String> extractAccessToken(Optional<String> headerWithBearer) {
        if (headerWithBearer.isPresent() && headerWithBearer.get().startsWith(BEARER_PREFIX)) {
            return Optional.of(headerWithBearer.get().substring(ACCESS_TOKEN_START_INDEX));
        }
        return Optional.absent();
    }

    @Override
    public void destroy() {

    }

    public static String getAccountId(HttpServletRequest req) {
        return (String) req.getAttribute(REQ_ACCOUNT_ID_KEY);
    }

    public static String getClientId(HttpServletRequest req) {
        return (String) req.getAttribute(REQ_CLIENT_ID_KEY);
    }

    protected void setClientId(ServletRequest req, String clientId) {
        req.setAttribute(REQ_CLIENT_ID_KEY, clientId);
    }

    protected void setAccountId(ServletRequest req, String accountId) {
        req.setAttribute(REQ_ACCOUNT_ID_KEY, accountId);
    }

    public static Collection<String> getScopes(HttpServletRequest req) {
        return (Collection<String>) req.getAttribute(REQ_SCOPES_KEY);
    }

    private void setScopes(ServletRequest req, Collection<String> scopes) {
        req.setAttribute(REQ_SCOPES_KEY, scopes);
    }

    public void setAuthService(IAuthService authService) {
        this.authService = authService;
    }
}
