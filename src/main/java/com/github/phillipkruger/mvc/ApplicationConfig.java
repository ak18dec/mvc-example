package com.github.phillipkruger.mvc;

import java.util.Map;
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

    @Override
    public Map<String, Object> getProperties() {
       return super.getProperties();
    }   
}