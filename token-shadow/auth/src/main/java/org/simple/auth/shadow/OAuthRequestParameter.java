package org.simple.auth.shadow;

import com.google.common.base.Optional;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Peter Schneider-Manzell
 */
public enum OAuthRequestParameter {

    GRANT_TYPE("grant_type"), REDIRECT_URI("redirect_uri"), SCOPE("scope"), STATE("state"),CLIENT_ID("client_id"),CLIENT_SECRET("client_secret"),REFRESH_TOKEN("refresh_token");
    private final String paramName;

    private OAuthRequestParameter(String paramName) {
        this.paramName = paramName;
    }

    public String getParamName() {
        return paramName;
    }

    public Optional<String> getValue(HttpServletRequest req) {
        return Optional.fromNullable(req.getParameter(getParamName()));
    }
}
