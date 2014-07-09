package org.simple.auth.model;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public interface ProfileAware<P extends BasicUserProfile, T extends AccessToken> {
    public P loadUserProfile(T token) throws OAuthException;
}
