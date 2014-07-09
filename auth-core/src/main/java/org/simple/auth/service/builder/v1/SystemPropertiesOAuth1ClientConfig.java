package org.simple.auth.service.builder.v1;

import com.google.common.base.Optional;
import org.simple.auth.model.v1.OAuth1ClientConfig;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public class SystemPropertiesOAuth1ClientConfig implements OAuth1ClientConfig {

    private final String networkName;
    private final Optional<String> systemPropertiesPrefix;

    public SystemPropertiesOAuth1ClientConfig(String networkName, String systemPropertiesPrefix) {
        this.networkName = networkName;
        this.systemPropertiesPrefix = Optional.fromNullable(systemPropertiesPrefix);
    }

    @Override
    public String clientId() {
        return System.getProperty(propName("clientId"));
    }

    @Override
    public String secret() {
        return System.getProperty(propName("secret"));
    }

    @Override
    public String callbackUrl() {
        return System.getProperty(propName("callbackUrl"));
    }

    @Override
    public String state() {
        return System.getProperty(propName("state"));
    }

    private String propName(String prop) {
        String toReturn = networkName + "." + prop;
        if (systemPropertiesPrefix.isPresent()) {
            return systemPropertiesPrefix.get() + "." + toReturn;
        }
        return toReturn;
    }
}
