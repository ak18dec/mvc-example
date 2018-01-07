package com.github.phillipkruger.mvc.dashboard;

import com.github.phillipkruger.config.FeedService;
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
  
    @Inject
    private FeedService feedService;
    
    @Controller
    @GET
    public String list() {
        //String urls = feedService.getUrls();
        
        dashboard.setName("Hello World ");
        
        return "dashboard.jsp";
    }

    
}
