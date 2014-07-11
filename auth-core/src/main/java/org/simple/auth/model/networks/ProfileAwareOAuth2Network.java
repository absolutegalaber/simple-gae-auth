package org.simple.auth.model.networks;

import com.google.common.base.Optional;
import org.simple.auth.model.*;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class ProfileAwareOAuth2Network<P extends BasicUserProfile> extends DefaultOAuth2Network implements ProfileAware<P> {
    private Class<P> profileClass;
    private final Optional<String> profileUrl;

    public ProfileAwareOAuth2Network(String name, ClientConfig config, String authUrl, String accessTokenUrl, String profileUrl, Class<P> profileClass) {
        super(name, config, authUrl, accessTokenUrl);
        this.profileUrl = Optional.of(profileUrl);
        this.profileClass = profileClass;
    }

    @Override
    public String getProfileUrl() {
        return profileUrl.get();
    }

    @Override
    public P loadUserProfile(INetworkToken token) throws OAuthException {
        return getAs(getProfileUrl(), token, profileClass);
    }
}
