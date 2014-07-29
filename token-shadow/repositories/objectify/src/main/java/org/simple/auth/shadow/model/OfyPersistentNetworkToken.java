package org.simple.auth.shadow.model;

import com.google.api.client.auth.openidconnect.IdToken;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import lombok.*;

import java.util.Date;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Entity
@Cache
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "network", "accessToken", "accountId"}, callSuper = false)
@ToString(of = {"id", "network", "accountId"}, callSuper = false)
public class OfyPersistentNetworkToken extends BaseOfyEntity implements IPersistentNetworkToken {

    @Id
    @Getter
    @Setter
    String id;

    @Getter
    @Setter
    String network;

    @Getter
    @Setter
    String networkUserId;

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

    @Override
    public IdToken getIdToken() {
        return null;
    }
}
