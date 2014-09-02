package org.simple.auth.listener;

import org.simple.auth.service.NetworkService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Peter Schneider-Manzell
 */
public abstract class AbstractNetworkServiceInitializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        NetworkService networkService = NetworkService.getNetworkSerice();
        configureNetworks(networkService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }


    public abstract void configureNetworks(NetworkService networkService);
}
