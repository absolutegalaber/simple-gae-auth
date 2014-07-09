package org.simple.auth.model.networks;

import org.simple.auth.model.BasicUserProfile;
import org.simple.auth.model.OAuthException;
import org.simple.auth.model.ProfileAware;
import org.simple.auth.model.v2.OAuth2AccessToken;
import org.simple.auth.model.v2.OAuth2ClientConfig;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class ProfileAwareOAuth2Network<P extends BasicUserProfile> extends DefaultOAuth2Network implements ProfileAware<P, OAuth2AccessToken> {
    private Class<P> profileClass;

    public ProfileAwareOAuth2Network(String name, OAuth2ClientConfig config, String authUrl, String accessTokenUrl, String profileUrl, Class<P> profileClass) {
        super(name, config, authUrl, accessTokenUrl, profileUrl);
        this.profileClass = profileClass;
    }

    @Override
    public P loadUserProfile(OAuth2AccessToken token) throws OAuthException {
        return getAs(getProfileUrl(), token, profileClass);
    }
}
