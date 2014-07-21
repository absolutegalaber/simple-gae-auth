package org.simple.auth.fakeoauth;

import org.simple.auth.model.IClient;
import org.simple.auth.model.networks.ProfileAwareOAuth2Network;

/**
 * @author Peter Schneider-Manzell
 */
public class FakeOauthClientConfig extends ProfileAwareOAuth2Network<FakeProfile> {
    public FakeOauthClientConfig(IClient config) {
        super("fake", config, "http://localhost:8088/fake_auth", "http://localhost:8088/fake_access_token", "http://localhost:8088/fake_provider/profile", FakeProfile.class);
    }
}
