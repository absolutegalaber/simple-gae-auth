package org.simple.auth.shadow.repository;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;
import org.simple.auth.model.INetworkToken;
import org.simple.auth.shadow.model.IPersistentNetworkToken;
import org.simple.auth.shadow.model.OfyPersistentNetworkToken;

import java.io.Serializable;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class OfyPersistentNetworkTokenRepository extends BaseOfyRepository implements IPersistenNetworkTokenRepository {

    public OfyPersistentNetworkTokenRepository(ObjectifyFactory factory) {
        super(factory);
    }

    @Override
    public IPersistentNetworkToken load(String network, String accountId) {
        return ofy().load().type(OfyPersistentNetworkToken.class)
                .filter("network", network)
                .filter("accountId", accountId)
                .first()
                .now();
    }

    @Override
    public IPersistentNetworkToken create(String accountId, String networkUserid, INetworkToken networkToken) {
        OfyPersistentNetworkToken token = new OfyPersistentNetworkToken();
        token.setAccessToken(networkToken.getAccessToken());
        token.setAccountId((String) accountId);
        token.setExpiresAt(networkToken.getExpiresAt());
        token.setNetwork(networkToken.getNetwork());
        token.setRefreshToken(networkToken.getRefreshToken());
        token.setTokenSecret(networkToken.getTokenSecret());
        token.setNetworkUserId((String) networkUserid);
        Key<OfyPersistentNetworkToken> saved = ofy().save().entity(token).now();
        return ofy().load().key(saved).now();
    }
}
