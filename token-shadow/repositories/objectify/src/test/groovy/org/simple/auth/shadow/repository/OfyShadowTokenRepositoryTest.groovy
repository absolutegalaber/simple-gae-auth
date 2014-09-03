package org.simple.auth.shadow.repository

import com.googlecode.objectify.ObjectifyService
import org.simple.auth.model.IClient
import org.simple.auth.shadow.model.OfyClient
import org.simple.auth.shadow.model.OfyNetworkClientConfig
import org.simple.auth.shadow.model.OfyPersistentNetworkToken
import org.simple.auth.shadow.model.OfyShadowToken


/**
 * @author Peter Schneider-Manzell
 */
class OfyShadowTokenRepositoryTest extends BaseRepositoryTest {

    static {
        ObjectifyService.register(OfyNetworkClientConfig.class);
        ObjectifyService.register(OfyClient.class);
        ObjectifyService.register(OfyPersistentNetworkToken.class);
        ObjectifyService.register(OfyShadowToken.class);
    }


    def "LoadByAccessToken"() {
        given:
        OfyShadowTokenRepository repository = new OfyShadowTokenRepository(ObjectifyService.factory())
        String accountId = "12345"
        String clientId = "test_client"
        IClient client = Mock(IClient)
        client.clientId() >> clientId
        def token = repository.createShadowToken(accountId, client)

        when:
        def loadedToken = repository.loadByAccessToken(token.accessToken)

        then:
        loadedToken != null
    }

    def "LoadByRefreshToken"() {
        given:
        OfyShadowTokenRepository repository = new OfyShadowTokenRepository(ObjectifyService.factory())
        String accountId = "12345"
        String clientId = "test_client"
        IClient client = Mock(IClient)
        client.clientId() >> clientId
        def token = repository.createShadowToken(accountId, client)

        when:
        def loadedToken = repository.loadByRefreshToken(token.refreshToken)

        then:
        loadedToken != null
    }

    def "LoadByAccountAndClient"() {
        given:
        OfyShadowTokenRepository repository = new OfyShadowTokenRepository(ObjectifyService.factory())
        String accountId = "12345"
        String clientId = "test_client"
        IClient client = Mock(IClient)
        client.clientId() >> clientId
        repository.createShadowToken(accountId, client)

        when:
        def token = repository.loadByAccountAndClient(accountId, clientId)

        then:
        token != null
    }

    def "CreateShadowToken"() {
        given:
        OfyShadowTokenRepository repository = new OfyShadowTokenRepository(ObjectifyService.factory())
        String accountId = "12345"
        String clientId = "test_client"
        IClient client = Mock(IClient)

        when:
        client.clientId() >> clientId
        def token = repository.createShadowToken(accountId, client)

        then:
        token != null
        token.accessToken != null
        token.refreshToken != null
        token.clientId == clientId
        token.accountId == accountId
        token.expiresAt != null
    }
}
