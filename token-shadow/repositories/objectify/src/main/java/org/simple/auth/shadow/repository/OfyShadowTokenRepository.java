package org.simple.auth.shadow.repository;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;
import org.simple.auth.model.IClient;
import org.simple.auth.shadow.model.IAccount;
import org.simple.auth.shadow.model.IShadowToken;
import org.simple.auth.shadow.model.OfyShadowToken;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class OfyShadowTokenRepository extends BaseOfyRepository implements IShadowTokenRepository {
    public OfyShadowTokenRepository(ObjectifyFactory factory) {
        super(factory);
    }

    @Override
    public IShadowToken loadByAccessToken(String accessToken) {
        return ofy().load().type(OfyShadowToken.class).filter("accessToken", accessToken).first().now();
    }

    @Override
    public IShadowToken loadByRefreshToken(String refreshToken) {
        return ofy().load().type(OfyShadowToken.class).filter("refreshToken", refreshToken).first().now();
    }

    @Override
    public IShadowToken loadByAccountAndClient(Serializable accountId, String clientId) {
        return ofy().load().type(OfyShadowToken.class)
                .filter("accountId", accountId)
                .filter("clientId", clientId)
                .first()
                .now();
    }

    @Override
    public IShadowToken createShadowToken(IAccount account, IClient client) {
        OfyShadowToken token = new OfyShadowToken();
        token.setAccessToken(UUID.randomUUID().toString().replace("-", ""));
        token.setAccountId(account.getId());
        token.setClientId(client.clientId());
        token.setExpiresAt(newExpiry());
        Key<OfyShadowToken> inserted = ofy().save().entity(token).now();
        return ofy().load().key(inserted).now();
    }

    private Date newExpiry() {
        Calendar expiry = Calendar.getInstance();
        expiry.add(Calendar.HOUR, 3);
        return expiry.getTime();
    }
}
