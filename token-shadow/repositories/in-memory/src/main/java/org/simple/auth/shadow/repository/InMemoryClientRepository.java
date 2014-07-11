package org.simple.auth.shadow.repository;

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
        InMemoryClient localClient = new InMemoryClient("local_test", "local_test_pwd", "http://localhost/example/callback");
        clients.put(localClient.clientId(), localClient);
    }
}
