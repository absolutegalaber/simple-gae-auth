package org.simple.auth.model;

import com.google.common.base.Optional;

import java.util.Collection;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class ClientConfig {

    private String clientId;
    private String secret;
    private String callbackUrl;
    private Optional<String> state;
    private Optional<Collection<String>> scope;

    public String clientId() {
        return clientId();
    }

    public String secret() {
        return secret;
    }

    public String callbackUrl() {
        return callbackUrl;
    }

    public String state() {
        return state.orNull();
    }

    public Collection<String> scope() {
        return scope.orNull();
    }
}
