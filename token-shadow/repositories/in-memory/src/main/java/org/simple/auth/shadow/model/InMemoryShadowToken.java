package org.simple.auth.shadow.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Getter
    @Setter
    Set<String> scopes = new HashSet<>();


}
