package org.simple.auth.shadow.model;

import com.google.common.base.Optional;
import org.simple.auth.model.ClientConfig;
import org.simple.auth.model.IClientWritable;

import java.util.Collection;

/**
 * @author Peter Schneider-Manzell
 */
public class InMemoryClient extends ClientConfig implements IClientWritable {


    @Override
    public void setState(String state) {
        super.setState(Optional.fromNullable(state));
    }

    @Override
    public void setScope(Collection<String> scope) {
        super.setScope(Optional.fromNullable(scope));
    }
}
