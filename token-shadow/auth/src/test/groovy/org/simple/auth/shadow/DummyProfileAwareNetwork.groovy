package org.simple.auth.shadow

import org.simple.auth.model.BasicUserProfile
import org.simple.auth.model.INetworkToken
import org.simple.auth.model.OAuthException
import org.simple.auth.model.ProfileAware

/**
 * @author Peter Schneider-Manzell
 */
class DummyProfileAwareNetwork extends DummyNetwork implements ProfileAware {



    @Override
    BasicUserProfile loadUserProfile(INetworkToken token) throws OAuthException {
        throw new IllegalStateException("Not implemented")
    }

    @Override
    String getProfileUrl() {
        throw new IllegalStateException("Not implemented")
    }
}
