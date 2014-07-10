package org.simple.auth.model.v2;

import com.google.common.base.Optional;
import org.simple.auth.model.AccessToken;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OAuth2AccessToken extends AccessToken implements IOAuth2NetworkToken{
    protected final Optional<String> refreshTokenInternal;
    protected final Optional<Date> expiresAtInternal;

    public OAuth2AccessToken(String network, String accessToken, String refreshToken, Long expiresInSeconds) {
        super(network, accessToken);
        this.refreshTokenInternal = Optional.fromNullable(refreshToken);
        if (expiresInSeconds != null) {
            Calendar expiryTime = Calendar.getInstance();
            expiryTime.add(Calendar.SECOND, expiresInSeconds.intValue());
            this.expiresAtInternal = Optional.of(expiryTime.getTime());
        } else {
            this.expiresAtInternal = Optional.absent();

        }
    }

    public final String getRefreshToken(){
        return refreshTokenInternal.get();
    }

    public final Date getExpiresAt(){
        return expiresAtInternal.get();
    }
}
