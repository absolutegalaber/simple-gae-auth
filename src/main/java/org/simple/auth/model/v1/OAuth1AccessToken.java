package org.simple.auth.model.v1;

import com.google.common.base.Preconditions;
import org.simple.auth.model.AccessToken;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OAuth1AccessToken extends AccessToken {
    @Getter
    protected final String tokenSecret;

    public OAuth1AccessToken(String network, String accessToken, String tokenSecret) {
        super(network, accessToken);
        Preconditions.checkNotNull(tokenSecret);
        this.tokenSecret = tokenSecret;
    }
}
