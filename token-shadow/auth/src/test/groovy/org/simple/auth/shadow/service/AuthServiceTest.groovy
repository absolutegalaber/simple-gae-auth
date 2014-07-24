package org.simple.auth.shadow.service

import org.simple.auth.model.IClient
import org.simple.auth.model.INetworkToken
import org.simple.auth.shadow.DummyAccount
import org.simple.auth.shadow.DummyClient
import org.simple.auth.shadow.DummyPersistentNetworkToken
import org.simple.auth.shadow.DummyShadowToken
import org.simple.auth.shadow.model.IAccount
import org.simple.auth.shadow.model.IPersistentNetworkToken
import org.simple.auth.shadow.model.IShadowToken
import org.simple.auth.shadow.repository.IAccountRepository
import org.simple.auth.shadow.repository.IPersistenNetworkTokenRepository
import org.simple.auth.shadow.repository.IShadowTokenRepository
import spock.lang.Specification

/**
 * @author Peter Schneider-Manzell
 */
class AuthServiceTest extends Specification {

    AuthService underTest
    IRepositoryService repositoryServiceMock
    IPersistenNetworkTokenRepository persistenNetworkTokenRepositoryMock
    IShadowTokenRepository shadowTokenRepositoryMock
    IAccountRepository accountRepositoryMock

    def setup() {
        underTest = new AuthService()
        repositoryServiceMock = Mock(IRepositoryService)
        persistenNetworkTokenRepositoryMock = Mock(IPersistenNetworkTokenRepository)
        shadowTokenRepositoryMock = Mock(IShadowTokenRepository)
        accountRepositoryMock = Mock(IAccountRepository)
        underTest.setRepositoryService(repositoryServiceMock)

    }

    def "GetShadowTokenByAccessToken"() {
        given:
        String existingShadowToken = "testtoken"
        when:
        repositoryServiceMock.shadowTokenRepository >> shadowTokenRepositoryMock
        IShadowToken shadowToken = new DummyShadowToken()
        shadowTokenRepositoryMock.loadByAccessToken(existingShadowToken) >> shadowToken
        IShadowToken token = underTest.getShadowToken(existingShadowToken)
        then:
        token == shadowToken
    }

    def "GetShadowTokenByAccessTokenNotExistent"() {
        given:
        String notExistingToken = "testtoken"
        when:
        repositoryServiceMock.shadowTokenRepository >> shadowTokenRepositoryMock
        shadowTokenRepositoryMock.loadByAccessToken(notExistingToken) >> null
        IShadowToken token = underTest.getShadowToken(notExistingToken)
        then:
        token == null
    }

    def "GetShadowTokenByClientAndNetworkToken"() {
        given:
        String network = "testnetwork"
        String networkUserId = "network_user_id"
        String accountId = "test_account_id"
        String clientId = "test_client_id"
        IClient client = new DummyClient()
        client.clientId = clientId
        IPersistentNetworkToken dummyPersistentNetworkToken = new DummyPersistentNetworkToken()
        dummyPersistentNetworkToken.accountId = accountId
        dummyPersistentNetworkToken.network = network
        dummyPersistentNetworkToken.networkUserId = networkUserId
        INetworkToken dummyNetworkToken = dummyPersistentNetworkToken
        IAccount dummyAccount = new DummyAccount()
        dummyAccount.id = accountId
        IShadowToken dummyShadowToken = new DummyShadowToken()
        dummyShadowToken.accountId = accountId
        dummyShadowToken.clientId = clientId
        dummyShadowToken.accessToken = "TestAccessToken"

        when:
        repositoryServiceMock.persistenNetworkTokenRepository >> persistenNetworkTokenRepositoryMock
        repositoryServiceMock.accountRepository >> accountRepositoryMock
        repositoryServiceMock.shadowTokenRepository >> shadowTokenRepositoryMock
        persistenNetworkTokenRepositoryMock.load(network, networkUserId) >> dummyPersistentNetworkToken
        accountRepositoryMock.load(accountId) >> dummyAccount
        shadowTokenRepositoryMock.loadByAccountAndClient(accountId,clientId) >> dummyShadowToken
        IShadowToken shadowToken = underTest.getShadowToken(client,dummyNetworkToken,networkUserId)

        then:
        shadowToken != null
    }

    def "CreateShadowTokenByClientAndNetworkTokenNotExistingShadowToken"() {
        given:
        String network = "testnetwork"
        String networkUserId = "network_user_id"
        String accountId = "test_account_id"
        String clientId = "test_client_id"
        IClient dummyClient = new DummyClient()
        dummyClient.clientId = clientId
        IPersistentNetworkToken dummyPersistentNetworkToken = new DummyPersistentNetworkToken()
        dummyPersistentNetworkToken.accountId = accountId
        dummyPersistentNetworkToken.network = network
        dummyPersistentNetworkToken.networkUserId = networkUserId
        INetworkToken dummyNetworkToken = dummyPersistentNetworkToken
        IAccount dummyAccount = new DummyAccount()
        dummyAccount.id = accountId
        IShadowToken dummyShadowToken = new DummyShadowToken()
        dummyShadowToken.accountId = accountId
        dummyShadowToken.clientId = clientId
        dummyShadowToken.accessToken = "TestAccessToken"

        when:
        repositoryServiceMock.persistenNetworkTokenRepository >> persistenNetworkTokenRepositoryMock
        repositoryServiceMock.accountRepository >> accountRepositoryMock
        repositoryServiceMock.shadowTokenRepository >> shadowTokenRepositoryMock
        persistenNetworkTokenRepositoryMock.load(network, networkUserId) >> dummyPersistentNetworkToken
        accountRepositoryMock.load(accountId) >> dummyAccount
        shadowTokenRepositoryMock.loadByAccountAndClient(accountId,clientId) >> null
        shadowTokenRepositoryMock.createShadowToken(dummyAccount,dummyClient) >> dummyShadowToken
        IShadowToken shadowToken = underTest.getShadowToken(dummyClient,dummyNetworkToken,networkUserId)

        then:
        shadowToken == dummyShadowToken
    }

    def "CreateShadowTokenByClientAndNetworkTokenNotExistingPersistentNetworkToken"() {
        given:
        String network = "testnetwork"
        String networkUserId = "network_user_id"
        String accountId = "test_account_id"
        String clientId = "test_client_id"
        IClient dummyClient = new DummyClient()
        dummyClient.clientId = clientId
        IPersistentNetworkToken dummyPersistentNetworkToken = new DummyPersistentNetworkToken()
        dummyPersistentNetworkToken.accountId = accountId
        dummyPersistentNetworkToken.network = network
        dummyPersistentNetworkToken.networkUserId = networkUserId
        INetworkToken dummyNetworkToken = dummyPersistentNetworkToken
        IAccount dummyAccount = new DummyAccount()
        dummyAccount.id = accountId
        IShadowToken dummyShadowToken = new DummyShadowToken()
        dummyShadowToken.accountId = accountId
        dummyShadowToken.clientId = clientId
        dummyShadowToken.accessToken = "TestAccessToken"

        when:
        repositoryServiceMock.persistenNetworkTokenRepository >> persistenNetworkTokenRepositoryMock
        repositoryServiceMock.accountRepository >> accountRepositoryMock
        repositoryServiceMock.shadowTokenRepository >> shadowTokenRepositoryMock
        persistenNetworkTokenRepositoryMock.load(network, networkUserId) >> null
        IAccount transientAccount = new DummyAccount()
        accountRepositoryMock.createTransient() >> transientAccount
        transientAccount.id = accountId
        accountRepositoryMock.save(transientAccount)
        persistenNetworkTokenRepositoryMock.create(accountId,networkUserId,dummyNetworkToken)
        accountRepositoryMock.load(accountId) >> dummyAccount
        shadowTokenRepositoryMock.loadByAccountAndClient(accountId,clientId) >> null
        shadowTokenRepositoryMock.createShadowToken(transientAccount,dummyClient) >> dummyShadowToken
        IShadowToken shadowToken = underTest.getShadowToken(dummyClient,dummyNetworkToken,networkUserId)

        then:
        shadowToken == dummyShadowToken
    }

    def "LoadOrCreateShadowToken"() {

    }

    def "CreatePersistentNetworkToken"() {

    }

    def "IsShadowTokenValid"() {

    }
}
