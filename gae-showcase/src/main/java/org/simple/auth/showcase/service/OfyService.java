package org.simple.auth.showcase.service;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import org.simple.auth.showcase.model.SocialNetwork;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class OfyService {
    static {
        factory().register(SocialNetwork.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }

}
