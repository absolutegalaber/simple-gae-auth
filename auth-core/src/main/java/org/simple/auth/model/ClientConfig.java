package org.simple.auth.model;

import com.google.common.base.Optional;
import lombok.Setter;

import java.util.Collection;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class ClientConfig implements IClient {
    @Setter
    private String clientId;
    @Setter
    private String secret;
    @Setter
    private String callbackUrl;
    @Setter
    private Optional<String> state;
    @Setter
    private Optional<Collection<String>> scope;

    public String clientId() {
        return clientId;
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
