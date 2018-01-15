package com.github.phillipkruger.mvc.dashboard;

import com.github.phillipkruger.config.ApplicationConfig;
import com.github.phillipkruger.mvc.feed.Feed;
import com.github.phillipkruger.mvc.feed.FeedService;
import com.rometools.rome.feed.synd.SyndFeed;
import java.net.URL;
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
        URL dashboardIcon = applicationConfig.getDashboardIcon();
        List<Feed> feeds = feedService.getFeeds(applicationConfig.getFeeds());
        
        dashboard.setName(dashboardName);
        dashboard.setIcon(dashboardIcon);
        dashboard.setFeeds(feeds);
        
        return "dashboard.jsp"; 
    }

    
}
