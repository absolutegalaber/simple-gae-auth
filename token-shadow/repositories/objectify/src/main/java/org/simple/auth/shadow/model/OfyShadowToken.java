package org.simple.auth.shadow.model;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Entity
@Cache
@NoArgsConstructor
@EqualsAndHashCode(of = {"accessToken", "accountId","clientId", "network"}, callSuper = false)
@ToString(of = {"accountId","clientId", "network","scopes","expiresAt"}, callSuper = false)
public class OfyShadowToken extends BaseOfyEntity implements IShadowToken {


    @Getter
    @Setter
    @Index
    String accountId;

    @Getter
    @Setter
    @Id
    String accessToken;

    @Getter
    @Setter
    @Index
    String refreshToken;

    @Getter
    @Setter
    String tokenSecret;

    @Getter
    @Setter
    Set<String> scopes = new HashSet<>();

    @Getter
    @Setter
    Date expiresAt;

    @Getter
    @Setter
    @Index
    String clientId;

    @Getter
    String network;
}
