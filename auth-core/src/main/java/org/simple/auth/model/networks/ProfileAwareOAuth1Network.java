package org.simple.auth.model.networks;

import org.simple.auth.model.BasicUserProfile;
import org.simple.auth.model.OAuthException;
import org.simple.auth.model.ProfileAware;
import org.simple.auth.model.v1.OAuth1AccessToken;
import org.simple.auth.model.v1.OAuth1ClientConfig;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class ProfileAwareOAuth1Network<P extends BasicUserProfile> extends DefaultOAuth1Network implements ProfileAware<P, OAuth1AccessToken> {
    private Class<P> profileClass;

    public ProfileAwareOAuth1Network(String name, OAuth1ClientConfig clientConfig, String requestTokenUrl, String authUrl, String accessTokenUrl, String profileUrl, Class<P> profileClass) {
        super(name, clientConfig, requestTokenUrl, authUrl, accessTokenUrl, profileUrl);
        this.profileClass = profileClass;
    }

    @Override
    public P loadUserProfile(OAuth1AccessToken token) throws OAuthException {
        return getAs(getProfileUrl(), token, profileClass);
    }
}
