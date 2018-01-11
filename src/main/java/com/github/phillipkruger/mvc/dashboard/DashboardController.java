package com.github.phillipkruger.mvc.dashboard;

import com.github.phillipkruger.config.ApplicationConfig;
import com.github.phillipkruger.mvc.feed.FeedService;
import com.sun.syndication.feed.synd.SyndFeed;
import java.util.List;
import javax.inject.Inject;
import javax.mvc.annotation.Controller;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import lombok.extern.java.Log;

/**
 * Main Dashboard controller
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@Log
@Path("/")
public class DashboardController {

    @Inject
    private Dashboard dashboard;
  
    @Inject
    private ApplicationConfig applicationConfig;
    
    @Inject 
    private FeedService feedService;
    
    @Controller
    @GET
    public String list() {
        
        String dashboardName = applicationConfig.getDashboardName();
        List<SyndFeed> feeds = feedService.getFeeds(applicationConfig.getFeeds());
        
        dashboard.setName(dashboardName);
        dashboard.setFeeds(feeds);
        
        return "dashboard.jsp"; // Why do I have to know the extention (jsp) ??
    }

    
}
