package org.simple.auth.shadow

import groovy.transform.ToString
import org.simple.auth.shadow.model.IAccount

/**
 * @author Peter Schneider-Manzell
 */
@ToString
class DummyAccount implements IAccount{
    Serializable id
}
