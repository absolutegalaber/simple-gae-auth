package org.simple.auth.model;

import com.google.common.base.Preconditions;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@ToString
@EqualsAndHashCode
public class AccessToken {
    @Getter
    protected final String network;
    @Getter
    protected final String accessToken;

    public AccessToken(String network, String accessToken) {
        Preconditions.checkNotNull(network);
        Preconditions.checkNotNull(accessToken);
        this.network = network;
        this.accessToken = accessToken;
    }
}
