package org.simple.auth.shadow.model;

import com.google.common.base.Splitter;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import lombok.*;
import org.simple.auth.model.ClientConfig;

import java.util.Collection;

/**
 * @author Peter Schneider-Manzell
 */
@Entity
@Cache
@NoArgsConstructor
@EqualsAndHashCode(of = {"name", "clientId", "clientSecret"}, callSuper = false)
@ToString(of = {"name"}, callSuper = false)
public class OfyNetworkClientConfig extends BaseOfyEntity {

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

    @Getter
    @Setter
    String callback;

    public ClientConfig getClientConfig() {
        return new MyClientConfig(this);
    }

    private static class MyClientConfig extends ClientConfig {
        private OfyNetworkClientConfig socialNetwork;

        private MyClientConfig(OfyNetworkClientConfig socialNetwork) {
            this.socialNetwork = socialNetwork;
        }

        @Override
        public Collection<String> scope() {
            String scope = socialNetwork.getScope();
            if (scope != null) {
                return Splitter.on(" ").trimResults().splitToList(scope);
            }
            return null;
        }

        @Override
        public String clientId() {
            return socialNetwork.getClientId();
        }

        @Override
        public String secret() {
            return socialNetwork.getClientSecret();
        }

        @Override
        public String callbackUrl() {
            if (socialNetwork.getCallback() != null) {
                return socialNetwork.getCallback();
            }
            return "http://localhost:8080/callback";
        }

        @Override
        public String state() {
            return null;
        }
    }
}
