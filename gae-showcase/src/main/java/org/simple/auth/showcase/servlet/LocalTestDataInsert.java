package org.simple.auth.showcase.servlet;

import com.googlecode.objectify.ObjectifyFilter;
import lombok.extern.slf4j.Slf4j;
import org.simple.auth.showcase.model.SocialNetwork;
import org.simple.auth.showcase.service.OfyService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Slf4j
public class LocalTestDataInsert implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream("/networkData.properties");
            if (resourceAsStream != null) {
                Properties properties = new Properties();
                properties.load(resourceAsStream);

                SocialNetwork google = new SocialNetwork();
                google.setName("google");
                google.setClientId(properties.getProperty("google.clientId"));
                google.setClientSecret(properties.getProperty("google.clientSecret"));
                google.setScope(properties.getProperty("google.scope"));

                SocialNetwork facebook = new SocialNetwork();
                facebook.setName("facebook");
                facebook.setClientId(properties.getProperty("facebook.clientId"));
                facebook.setClientSecret(properties.getProperty("facebook.clientSecret"));
                facebook.setScope(properties.getProperty("facebook.scope"));

                SocialNetwork twitter = new SocialNetwork();
                twitter.setName("twitter");
                twitter.setClientId(properties.getProperty("twitter.clientId"));
                twitter.setClientSecret(properties.getProperty("twitter.clientSecret"));
                twitter.setCallback("http://127.0.0.1:8080/callback");

                OfyService.ofy().save().entities(google, facebook, twitter).now();
                ObjectifyFilter.complete();
                log.info("Saved SocialNetwork entities");
            } else {
                log.info("networkData.properties not found...");
            }
        } catch (IOException e) {
            log.info("networkData.properties could not be loaded...");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
