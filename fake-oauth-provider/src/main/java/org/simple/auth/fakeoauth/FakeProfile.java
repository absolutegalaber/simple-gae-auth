package org.simple.auth.fakeoauth;

import lombok.Data;
import org.simple.auth.model.BasicUserProfile;

/**
 * @author Peter Schneider-Manzell
 */
@Data
public class FakeProfile implements BasicUserProfile {
    private String networkName;
    private String networkId;
    private String email;
    private String name;
    private String firstName;
    private String lastName;
    private String gender;
    private String locale;
    private String pictureUrl;

}
