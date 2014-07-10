package org.simple.auth.model.networks;

import com.google.common.base.Optional;
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
    private final Optional<String> profileUrl;

    public ProfileAwareOAuth1Network(String name, OAuth1ClientConfig clientConfig, String requestTokenUrl, String authUrl, String accessTokenUrl, String profileUrl, Class<P> profileClass) {
        super(name, clientConfig, requestTokenUrl, authUrl, accessTokenUrl);
        this.profileClass = profileClass;
        this.profileUrl = Optional.fromNullable(profileUrl);
    }

    @Override
    public String getProfileUrl() {
        return profileUrl.get();
    }

    @Override
    public P loadUserProfile(OAuth1AccessToken token) throws OAuthException {
        return getAs(getProfileUrl(), token, profileClass);
    }
}
