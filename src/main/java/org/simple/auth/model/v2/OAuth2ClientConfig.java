package org.simple.auth.model.v2;

import org.simple.auth.model.ClientConfig;

import java.util.Collection;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public interface OAuth2ClientConfig extends ClientConfig {
    public Collection<String> scope();
}
