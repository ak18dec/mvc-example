package com.github.phillipkruger.mvc.feed;

import java.net.URL;
import javax.inject.Inject;
import javax.mvc.Models;
import javax.mvc.annotation.Controller;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import lombok.extern.java.Log;

/**
 * Main Feed controller
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@Log
@Path("/feed")
public class FeedController {

    @Inject 
    private FeedService feedService;
    
    @Inject
    private Models models;
    
    @Controller
    @GET
    @Path("/{id}")
    public String getFeed(@PathParam("id") int id) {
        models.put("feed", feedService.getFeed(id));
        return "feed.jsp";
    }
}
