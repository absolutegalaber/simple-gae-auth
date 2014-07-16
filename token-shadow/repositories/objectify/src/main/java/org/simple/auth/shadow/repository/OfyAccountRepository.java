package org.simple.auth.shadow.repository;

import com.googlecode.objectify.ObjectifyFactory;
import org.simple.auth.shadow.model.OfyAccount;

import java.io.Serializable;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class OfyAccountRepository extends BaseOfyRepository implements IAccountRepository<OfyAccount> {

    public OfyAccountRepository(ObjectifyFactory factory) {
        super(factory);
    }

    @Override
    public OfyAccount load(Serializable id) {
        return ofy().load().type(OfyAccount.class).id((String) id).now();
    }

    @Override
    public void save(OfyAccount account) {
        ofy().save().entity(account).now();
    }

    @Override
    public OfyAccount createTransient() {
        return new OfyAccount();
    }
}
