package org.simple.auth.model.networks;

import org.simple.auth.model.ClientConfig;
import org.simple.auth.model.Network;
import org.simple.auth.model.networks.openid.GoogleOIC;
import org.simple.auth.model.networks.v1.Twitter;
import org.simple.auth.model.networks.v2.*;

/**
 * @author Peter Schneider-Manzell
 */
public enum NetworkIdentifier {
    GOOGLE("google") {
        @Override
        public Network createNetwork(ClientConfig clientConfig) {
            return new Google(clientConfig);
        }
    },GOOGLE_OIC("google_oic") {
        @Override
        public Network createNetwork(ClientConfig clientConfig) {
            return new GoogleOIC(clientConfig);
        }
    }, FACEBOOK("facebook") {
        @Override
        public Network createNetwork(ClientConfig clientConfig) {
            return new Facebook(clientConfig);
        }
    }, TWITTER("twitter") {
        @Override
        public Network createNetwork(ClientConfig clientConfig) {
            return new Twitter(clientConfig);
        }
    }, FOURSQUARE("foursquare") {
        @Override
        public Network createNetwork(ClientConfig clientConfig) {
            return new FourSquare(clientConfig);
        }
    }, GITHUB("github") {
        @Override
        public Network createNetwork(ClientConfig clientConfig) {
            return new Github(clientConfig);
        }
    }, LINKEDIN("linkedin") {
        @Override
        public Network createNetwork(ClientConfig clientConfig) {
            return new LinkedIn(clientConfig);
        }
    };

    private final String key;

    private NetworkIdentifier(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public abstract Network createNetwork(ClientConfig clientConfig);

    public static NetworkIdentifier byKey(String key) {
        for (NetworkIdentifier networkIdentifier : values()) {
            if(networkIdentifier.getKey().equals(key)){
                return networkIdentifier;
            }
        }
        return null;
    }
}
