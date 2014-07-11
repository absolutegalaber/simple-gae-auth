package org.simple.auth.shadow.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Peter Schneider-Manzell
 */
public class InMemoryAccount implements IAccount {
    @Setter
    @Getter
    String id;
}
