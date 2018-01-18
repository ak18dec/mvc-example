package com.github.phillipkruger.mvc.dashboard;

import com.github.phillipkruger.config.ApplicationConfig;
import com.github.phillipkruger.mvc.feed.Feed;
import com.github.phillipkruger.mvc.feed.FeedService;
import java.net.URL;
import java.util.List;
import javax.inject.Inject;
import javax.mvc.annotation.Controller;
import javax.mvc.annotation.View;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import lombok.extern.java.Log;

/**
 * Main Dashboard controller
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@Log
@Path("/")
@Controller
public class DashboardController {

    @Inject
    private Dashboard dashboard;
  
    @Inject
    private ApplicationConfig applicationConfig;
    
    @Inject 
    private FeedService feedService;
    
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

    @GET @Path("/refresh/{id}")
    @View("dashboard.jsp")
    public void refreshFeed(@PathParam("id") int id) {
        feedService.reload(id);
    }
    
    @GET @Path("/ping")
    @View("dashboard.jsp")
    public void ping() {
        
    }
    
}