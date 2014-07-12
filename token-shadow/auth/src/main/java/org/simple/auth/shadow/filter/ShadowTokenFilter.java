package org.simple.auth.shadow.filter;

import com.google.common.base.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.simple.auth.shadow.model.IShadowToken;
import org.simple.auth.shadow.service.AuthService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author Peter Schneider-Manzell
 */
@Slf4j
public class ShadowTokenFilter implements Filter {

    private static final String REQ_ACCOUNT_ID_KEY = "org.simple.auth.shadow.filter.ShadowTokenFilter_account_id";
    private static final String REQ_CLIENT_ID_KEY = "org.simple.auth.shadow.filter.ShadowTokenFilter_client_id";
    AuthService authService = new AuthService();
    private static final String BEARER_PREFIX = "Bearer ";
    private static final int ACCESS_TOKEN_START_INDEX = BEARER_PREFIX.length();


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Optional<String> accessToken = extractAccessToken((HttpServletRequest) request);
        IShadowToken iShadowToken = null;
        if (accessToken.isPresent()) {
            log.info("Found access token {}", accessToken.get());
            iShadowToken = authService.getShadowToken(accessToken.get());
            log.info("Found shadow token {}", iShadowToken);
        }
        if (authService.isShadowTokenValid(iShadowToken)) {
            request.setAttribute(REQ_ACCOUNT_ID_KEY, iShadowToken.getAccountId());
            request.setAttribute(REQ_CLIENT_ID_KEY, iShadowToken.getClientId());
            chain.doFilter(request, response);
        } else {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setStatus(HttpStatus.SC_FORBIDDEN);
            httpServletResponse.getWriter().write("{\"error\":\"access_denied\"}");
            httpServletResponse.getWriter().flush();
            httpServletResponse.getWriter().close();
            return;
        }
    }

    private Optional<String> extractAccessToken(HttpServletRequest request) {
        Optional<String> headerWithBearer = Optional.fromNullable(request.getHeader("Authorization"));
        return extractAccessToken(headerWithBearer);

    }

    private Optional<String> extractAccessToken(Optional<String> headerWithBearer) {
        if (headerWithBearer.isPresent() && headerWithBearer.get().startsWith(BEARER_PREFIX)) {
            return Optional.of(headerWithBearer.get().substring(ACCESS_TOKEN_START_INDEX));
        }
        return Optional.absent();
    }

    @Override
    public void destroy() {

    }

    public static Serializable getAccountId(HttpServletRequest req) {
        return (Serializable) req.getAttribute(REQ_ACCOUNT_ID_KEY);
    }

    public static String getClientId(HttpServletRequest req) {
        return (String) req.getAttribute(REQ_CLIENT_ID_KEY);
    }
}
