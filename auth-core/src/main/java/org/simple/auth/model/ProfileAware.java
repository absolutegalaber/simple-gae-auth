package org.simple.auth.model;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public interface ProfileAware<P extends BasicUserProfile> {
    public P loadUserProfile(INetworkToken token) throws OAuthException;

    public String getProfileUrl();
}
