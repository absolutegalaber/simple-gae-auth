package org.simple.auth.model;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@ToString
@EqualsAndHashCode
public class AccessToken implements INetworkToken {
    @Getter
    protected final String network;
    @Getter
    protected final AccessTokenVersion version;
    @Getter
    protected final String accessToken;
    protected Optional<String> tokenSecret = Optional.absent();
    protected Optional<String> refreshToken = Optional.absent();
    protected Optional<Date> expiresAt = Optional.absent();

    private AccessToken(String network, String accessToken, String tokenSecret) {
        Preconditions.checkNotNull(network);
        Preconditions.checkNotNull(accessToken);
        Preconditions.checkNotNull(tokenSecret);
        this.network = network;
        this.accessToken = accessToken;
        this.tokenSecret = Optional.of(tokenSecret);
        this.version = AccessTokenVersion.OAUTH_1;
    }

    private AccessToken(String network, String accessToken, String refreshToken, Long expiresInSeconds) {
        Preconditions.checkNotNull(network);
        Preconditions.checkNotNull(accessToken);
        this.network = network;
        this.accessToken = accessToken;
        this.refreshToken = Optional.fromNullable(refreshToken);
        if (expiresInSeconds != null) {
            Calendar expiryTime = Calendar.getInstance();
            expiryTime.add(Calendar.SECOND, expiresInSeconds.intValue());
            this.expiresAt = Optional.of(expiryTime.getTime());
        }
        this.version = AccessTokenVersion.OAUTH_2;
    }

    public static AccessToken oAuth1Token(String network, String accessToken, String tokenSecret) {
        return new AccessToken(network, accessToken, tokenSecret);
    }

    public static AccessToken oAuth2Token(String network, String accessToken, String refreshToken, Long expiresInSeconds) {
        Preconditions.checkNotNull(network);
        Preconditions.checkNotNull(accessToken);
        return new AccessToken(network, accessToken, refreshToken, expiresInSeconds);
    }


    @Override
    public String getTokenSecret() {
        return tokenSecret.get();
    }

    @Override
    public String getRefreshToken() {
        return refreshToken.orNull();
    }

    @Override
    public Date getExpiresAt() {
        return expiresAt.orNull();
    }
}
