package org.simple.auth.showcase.model;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import lombok.*;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Entity
@Cache
@NoArgsConstructor
@EqualsAndHashCode(of = {"name", "clientId", "clientSecret"}, callSuper = false)
@ToString(of = {"name"}, callSuper = false)
public class SocialNetwork {
    @Id
    @Getter
    @Setter
    String name;

    @Getter
    @Setter
    String clientId;

    @Getter
    @Setter
    String clientSecret;

    @Getter
    @Setter
    String scope;

    @Getter
    @Setter
    String state;
}
