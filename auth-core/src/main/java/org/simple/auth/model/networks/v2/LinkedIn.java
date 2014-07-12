package org.simple.auth.model.networks.v2;

import com.google.api.client.auth.oauth2.Credential;
import org.simple.auth.model.ClientConfig;
import org.simple.auth.model.networks.OAuth2Network;
import org.simple.auth.model.v2.ConfigurableQueryParameterAccessMethod;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class LinkedIn extends OAuth2Network {
    public static final String NAME = "linkedin";
    private static final String AUTH_URL = "https://www.linkedin.com/uas/oauth2/authorization";
    private static final String ACCESS_TOKEN_URL = "https://www.linkedin.com/uas/oauth2/accessToken";
    private static final String PROFILE_URL = "https://api.linkedin.com/v1/people/~:(id,first-name,last-name,picture-url,email-address)";

    public LinkedIn(ClientConfig config) {
        super(
                NAME, config,
                AUTH_URL,
                ACCESS_TOKEN_URL
        );
        defaultHeaders.put("x-li-format", "json");
    }

    @Override
    protected Credential.AccessMethod myAccessMethod() {
        return new ConfigurableQueryParameterAccessMethod("oauth2_access_token");
    }
}
