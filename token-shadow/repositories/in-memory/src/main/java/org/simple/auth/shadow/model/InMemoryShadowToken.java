package org.simple.auth.shadow.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Peter Schneider-Manzell
 */
public class InMemoryShadowToken implements IShadowToken {

    @Getter
    @Setter
    String accountId;

    @Getter
    @Setter
    String accessToken;

    @Getter
    @Setter
    String refreshToken;

    @Getter
    @Setter
    String tokenSecret;

    @Getter
    @Setter
    Date expiresAt;

    @Getter
    @Setter
    String clientId;

    @Getter
    String network;


}
