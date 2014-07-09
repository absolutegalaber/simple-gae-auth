package org.simple.auth.showcase.service.factory;

import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.simple.auth.model.Network;
import org.simple.auth.model.networks.v2.Facebook;
import org.simple.auth.model.networks.v2.Google;
import org.simple.auth.model.v1.OAuth1ClientConfig;
import org.simple.auth.model.v2.OAuth2ClientConfig;
import org.simple.auth.service.builder.NetworkConfigurationService;
import org.simple.auth.showcase.model.SocialNetwork;
import org.simple.auth.showcase.service.OfyService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Slf4j
public class SocialNetworkFactory implements NetworkConfigurationService {
    private static final List<Network> networks = new ArrayList<>();

    @Override
    public Iterable<Network> configureNetworks() {
        if (networks.isEmpty()) {
            configure();
        }
        return networks;
    }

    private static void configure() {
        SocialNetwork google = OfyService.ofy().load().type(SocialNetwork.class).id("google").now();
        SocialNetwork facebook = OfyService.ofy().load().type(SocialNetwork.class).id("facebook").now();
//        SocialNetwork twitter = OfyService.ofy().load().type(SocialNetwork.class).id("twitter").now();
//        SocialNetwork foursquare = OfyService.ofy().load().type(SocialNetwork.class).id("foursquare").now();
//        SocialNetwork github = OfyService.ofy().load().type(SocialNetwork.class).id("github").now();
//        List<Network> networks = new ArrayList<>();
        networks.add(new Google(new MyClientConfig(google)));
        networks.add(new Facebook(new MyClientConfig(facebook)));
//        networks.add(new FourSquare(new MyClientConfig(foursquare)));
//        networks.add(new Twitter(new MyClientConfig(twitter)));
//        networks.add(new Github(new MyClientConfig(github)));
    }

    private static class MyClientConfig implements OAuth2ClientConfig, OAuth1ClientConfig {
        private SocialNetwork socialNetwork;

        private MyClientConfig(SocialNetwork socialNetwork) {
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
            return "http://localhost:8080/callback";
        }

        @Override
        public String state() {
            return null;
        }
    }
}
