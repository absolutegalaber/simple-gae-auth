package org.simple.auth.shadow.repository;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class BaseOfyRepository {
    private final ObjectifyFactory factory;

    public BaseOfyRepository(ObjectifyFactory factory) {
        this.factory = factory;
    }

    protected Objectify ofy() {
        return factory.begin();
    }
}
