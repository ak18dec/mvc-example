package com.github.phillipkruger.mvc;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Activate JAX-RS. 
 * 
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 * 
 * NOTE: We can not use the root as the application path, as we want to use webjars to deliver the static content.
 */
@ApplicationPath("/view")
public class ApplicationConfig extends Application {

    
    // NOTE: Not doing this, i.e let the server discover this with scanning, cause the building of URI in a View to break (see 2.3.1. Building URIs in a View)
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(com.github.phillipkruger.mvc.MyController.class);
        classes.add(com.github.phillipkruger.mvc.dashboard.DashboardController.class);
        classes.add(com.github.phillipkruger.mvc.feed.FeedController.class);
        return classes;
    }
    
}