package org.simple.auth.shadow.repository;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.simple.auth.model.IClient;
import org.simple.auth.shadow.model.IShadowToken;
import org.simple.auth.shadow.model.OfyShadowToken;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Slf4j
public class OfyShadowTokenRepository extends BaseOfyRepository implements IShadowTokenRepository {
    private SecureRandom random = new SecureRandom();

    public OfyShadowTokenRepository(ObjectifyFactory factory) {
        super(factory);
    }

    @Override
    public IShadowToken loadByAccessToken(String accessToken) {
        return ofy().load().type(OfyShadowToken.class).id(accessToken).now();
    }

    @Override
    public IShadowToken loadByRefreshToken(String refreshToken) {
        return ofy().load().type(OfyShadowToken.class).filter("refreshToken", refreshToken).first().now();
    }

    @Override
    public IShadowToken loadByAccountAndClient(String accountId, String clientId) {
        return ofy().load().type(OfyShadowToken.class)
                .filter("accountId", accountId)
                .filter("clientId", clientId)
                .first()
                .now();
    }

    @Override
    public IShadowToken createShadowToken(String accountId, IClient client) {
        OfyShadowToken token = new OfyShadowToken();
        token.setAccessToken(createToken(accountId, client, "access"));
        token.setRefreshToken(createToken(accountId, client, "refresh"));
        token.setAccountId(accountId);
        token.setClientId(client.clientId());
        token.setExpiresAt(newExpiry());
        Key<OfyShadowToken> inserted = ofy().save().entity(token).now();
        return ofy().load().key(inserted).now();
    }

    private String createToken(String accountId, IClient client, String tokenType) {
        return generateTokenPrefix(accountId, client, tokenType) + generateToken();
    }

    private String generateTokenPrefix(String accountId, IClient client, String tokenType) {
        String toHash = client.clientId() + accountId + tokenType;
        return DigestUtils.md5Hex(toHash);
    }

    private String generateToken() {
        return new BigInteger(130, random).toString(32);
    }

    private Date newExpiry() {
        Calendar expiry = Calendar.getInstance();
        expiry.add(Calendar.HOUR, 3);
        return expiry.getTime();
    }
}
