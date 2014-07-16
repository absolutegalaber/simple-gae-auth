package org.simple.auth.shadow.model;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import lombok.*;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Entity
@Cache
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@ToString(of = {"id"}, callSuper = false)
public class OfyAccount extends BaseOfyEntity implements IAccount {

    @Getter
    @Setter
    String id;
}
