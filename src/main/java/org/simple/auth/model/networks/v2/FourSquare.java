package org.simple.auth.model.networks.v2;

import com.google.api.client.auth.oauth2.Credential;
import org.simple.auth.model.networks.DefaultOAuth2Network;
import org.simple.auth.model.v2.ConfigurableQueryParameterAccessMethod;
import org.simple.auth.model.v2.OAuth2ClientConfig;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class FourSquare extends DefaultOAuth2Network {
    public FourSquare(OAuth2ClientConfig config) {
        super(
                "foursquare",
                config,
                "https://foursquare.com/oauth2/authorize",
                "https://foursquare.com/oauth2/access_token",
                "https://api.foursquare.com/v2/users/self"
        );
        defaultQueryParams.put("v", "20140701");
    }

    @Override
    protected Credential.AccessMethod myAccessMethod() {
        return new ConfigurableQueryParameterAccessMethod("oauth_token");
    }
}
