package org.simple.auth.model;

/**
 * Created by Josip.Mihelko @ Gmail
 */
public interface BasicUserProfile {

    public String getNetworkName();

    public String getNetworkId();

    public String getEmail();

    public String getName();

    public String getFirstName();

    public String getLastName();

    public String getGender();

    public String getLocale();

    public String getPictureUrl();
}
