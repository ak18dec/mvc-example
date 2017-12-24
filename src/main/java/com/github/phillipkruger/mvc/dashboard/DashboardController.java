package com.github.phillipkruger.mvc.dashboard;

import javax.inject.Inject;
import javax.mvc.annotation.Controller;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Main Dashboard controller
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@Path("/")
public class DashboardController {

    @Inject
    private Dashboard dashboard;
  
    @Controller
    @GET
    public String list() {
        dashboard.setName("Hello World");
        
        return "dashboard.jsp";
    }

    
}
