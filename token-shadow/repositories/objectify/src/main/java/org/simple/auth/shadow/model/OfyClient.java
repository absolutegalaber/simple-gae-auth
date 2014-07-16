package org.simple.auth.shadow.model;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import lombok.*;
import org.simple.auth.model.IClient;
import org.simple.auth.model.IClientWritable;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Entity
@Cache
@NoArgsConstructor
@EqualsAndHashCode(of = {"clientId", "secret"}, callSuper = false)
@ToString(of = {"clientId"}, callSuper = false)
public class OfyClient extends BaseOfyEntity implements IClient, IClientWritable {

    @Id
    @Getter
    @Setter
    String clientId;

    @Getter
    @Setter
    String secret;

    @Getter
    @Setter
    Collection<String> scope = new ArrayList<>();

    @Getter
    @Setter
    String callbackUrl;

    @Getter
    @Setter
    String state;

    @Override
    public String clientId() {
        return clientId;
    }

    @Override
    public String secret() {
        return secret;
    }

    @Override
    public String callbackUrl() {
        return callbackUrl;
    }

    @Override
    public String state() {
        return state;
    }

    @Override
    public Collection<String> scope() {
        return scope;
    }
}
