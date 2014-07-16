package org.simple.auth.shadow.repository;

import com.googlecode.objectify.ObjectifyFactory;
import org.simple.auth.model.IClient;
import org.simple.auth.shadow.model.OfyClient;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class OfyClientRepository extends BaseOfyRepository implements IClientRepository {

    public OfyClientRepository(ObjectifyFactory factory) {
        super(factory);
    }

    @Override
    public IClient load(String clientId) {
        return ofy().load().type(OfyClient.class).id(clientId).now();
    }

    @Override
    public void save(IClient client) {
        ofy().save().entity(client).now();
    }
}
