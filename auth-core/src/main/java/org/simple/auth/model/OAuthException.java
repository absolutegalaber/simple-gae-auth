package org.simple.auth.model;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class OAuthException extends Exception {
    public OAuthException() {
    }

    public OAuthException(String message) {
        super(message);
    }
}
