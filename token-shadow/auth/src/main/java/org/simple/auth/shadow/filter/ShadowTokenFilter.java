package org.simple.auth.shadow.filter;

import org.apache.http.HttpStatus;
import org.simple.auth.shadow.service.AuthService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Peter Schneider-Manzell
 */
public class ShadowTokenFilter implements Filter {

    AuthService authService = new AuthService();
    private static final int ACCESS_TOKEN_START_INDEX = 8;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String accessToken = extractAccessToken((HttpServletRequest) request);
        if (authService.isShadowTokenValid(accessToken)) {
            chain.doFilter(request, response);
        } else {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.sendError(HttpStatus.SC_FORBIDDEN);
            httpServletResponse.getWriter().write("{'error':'access_denied'}");
            httpServletResponse.getWriter().flush();
            httpServletResponse.getWriter().close();
            return;
        }
    }

    private String extractAccessToken(HttpServletRequest request) {
        String headerWithBearer = request.getHeader("Authorization");
        return extractAccessToken(headerWithBearer);
    }

    private String extractAccessToken(String headerWithBearer) {
        return headerWithBearer.substring(ACCESS_TOKEN_START_INDEX);
    }

    @Override
    public void destroy() {

    }
}
