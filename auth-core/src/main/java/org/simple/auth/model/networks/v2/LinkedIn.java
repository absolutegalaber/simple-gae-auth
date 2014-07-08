package org.simple.auth.model.networks.v2;

import com.google.api.client.auth.oauth2.Credential;
import org.simple.auth.model.networks.DefaultOAuth2Network;
import org.simple.auth.model.v2.ConfigurableQueryParameterAccessMethod;
import org.simple.auth.model.v2.OAuth2ClientConfig;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class LinkedIn extends DefaultOAuth2Network {

    public LinkedIn(OAuth2ClientConfig config) {
        super(
                "linkedin", config,
                "https://www.linkedin.com/uas/oauth2/authorization",
                "https://www.linkedin.com/uas/oauth2/accessToken",
                "https://api.linkedin.com/v1/people/~:(id,first-name,last-name,picture-url,email-address)"
        );
        defaultHeaders.put("x-li-format", "json");
    }

    @Override
    protected Credential.AccessMethod myAccessMethod() {
        return new ConfigurableQueryParameterAccessMethod("oauth2_access_token");
    }
}
