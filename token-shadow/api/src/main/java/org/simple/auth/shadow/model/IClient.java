package org.simple.auth.shadow.model;

/**
 * @author Peter Schneider-Manzell
 */
public interface IClient {

    String getClientId();

    String getClientSecret();

    String getCallbackUrl();

}
