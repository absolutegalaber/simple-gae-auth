package org.simple.auth.shadow

import org.simple.auth.model.BasicUserProfile

/**
 * @author Peter Schneider-Manzell
 */
class DummyUserProfile implements BasicUserProfile{

    String networkName
    String networkId
    String email
    String name
    String firstName
    String lastName
    String gender
    String locale
    String pictureUrl

}
