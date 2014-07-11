package org.simple.auth.model;

import java.util.Collection;

/**
 * @author Peter Schneider-Manzell
 */
public interface IClient {

    String clientId();

    String secret();

    String callbackUrl();

    String state();

    Collection<String> scope();
}
