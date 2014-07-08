package org.simple.auth.model;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public interface ClientConfig {
    public String clientId();

    public String secret();

    public String callbackUrl();

    public String state();
}
