package org.simple.auth.shadow.repository;

import org.simple.auth.shadow.model.IAccount;
import org.simple.auth.shadow.model.IClient;
import org.simple.auth.shadow.model.InMemoryShadowToken;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Peter Schneider-Manzell
 */
public class InMemoryShadowTokenRepository implements IShadowTokenRepository<InMemoryShadowToken> {

    Map<String, InMemoryShadowToken> tokensByAccessToken = new ConcurrentHashMap<>();
    Map<String, InMemoryShadowToken> tokensByRefreshToken = new ConcurrentHashMap<>();
    Map<String, InMemoryShadowToken> tokensByClientAndAccount = new ConcurrentHashMap<>();


    @Override
    public InMemoryShadowToken loadByAccessToken(String accessToken) {
        return tokensByAccessToken.get(accessToken);
    }

    @Override
    public InMemoryShadowToken loadByRefreshToken(String refreshToken) {
        return tokensByRefreshToken.get(refreshToken);
    }

    @Override
    public InMemoryShadowToken loadByAccountAndClient(Serializable accountId, String clientId) {
        return tokensByClientAndAccount.get(generateAccountClientIdentifier(accountId, clientId));
    }


    @Override
    public InMemoryShadowToken createShadowToken(IAccount account, IClient client) {
        InMemoryShadowToken shadowToken = new InMemoryShadowToken();
        String accessToken = generateToken();
        String refreshToken = generateToken();
        while (tokensByAccessToken.containsKey(accessToken) || tokensByRefreshToken.containsKey(refreshToken)) {
            accessToken = generateToken();
            refreshToken = generateToken();
        }
        shadowToken.setAccessToken(accessToken);
        shadowToken.setRefreshToken(refreshToken);
        shadowToken.setClientId(client.getClientId());
        shadowToken.setAccountId(account.getId());
        shadowToken.setExpiresAt(calculateNextExpirationDate());
        tokensByAccessToken.put(accessToken, shadowToken);
        tokensByRefreshToken.put(refreshToken, shadowToken);
        tokensByClientAndAccount.put(generateAccountClientIdentifier(account.getId(), client.getClientId()), shadowToken);
        return shadowToken;
    }

    private String generateToken() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    private Date calculateNextExpirationDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, 3);
        return cal.getTime();
    }

    private String generateAccountClientIdentifier(Serializable accountId, String clientId) {
        StringBuilder sb = new StringBuilder("{");
        sb.append(accountId);
        sb.append("}-{");
        sb.append(clientId);
        sb.append("}");
        return sb.toString();

    }
}
