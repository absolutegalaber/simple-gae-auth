package org.simple.auth.shadow.model;

import org.simple.auth.model.INetworkToken;

import java.io.Serializable;

/**
 * @author Peter Schneider-Manzell
 */
public interface IPersistentNetworkToken extends INetworkToken {

    String getNetworkUserId();
    String getAccountId();
}