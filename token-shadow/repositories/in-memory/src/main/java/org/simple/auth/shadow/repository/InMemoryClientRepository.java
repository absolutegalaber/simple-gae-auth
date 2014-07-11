package org.simple.auth.shadow.repository;

import org.simple.auth.service.builder.ClientBuilder;
import org.simple.auth.shadow.model.InMemoryClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Peter Schneider-Manzell
 */
public class InMemoryClientRepository implements IClientRepository<InMemoryClient> {

    Map<String, InMemoryClient> clients = new ConcurrentHashMap<>();

    @Override
    public InMemoryClient load(String clientId) {
        return clients.get(clientId);
    }

    @Override
    public void save(InMemoryClient client) {
        clients.put(client.clientId(), client);
    }

    public void registerClients() {
        createLocalTestClient();
    }

    private void createLocalTestClient() {
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.clientId("local_test").secret("local_test_pwd").callbackUrl("http://localhost/example/callback");
        InMemoryClient localClient = clientBuilder.build(new InMemoryClient());
        save(localClient);
    }
}
