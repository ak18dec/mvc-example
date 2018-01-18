package com.github.phillipkruger.mvc;

import com.github.phillipkruger.mvc.dashboard.Dashboard;
import javax.inject.Inject;
import javax.mvc.annotation.Controller;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import lombok.extern.java.Log;

/**
 * Testing URL Building
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@Log
@Path("/test")
@Controller
public class MyController {
    
    @Inject
    private Dashboard dashboard;
    
    @GET
    public String init() {
        return "test.jsp";
    }
    
    @GET @Path("{foo}/{id}")
    public String myMethod(@PathParam("foo") String foo, @PathParam("id") int id) {
        log.severe("foo = " + foo);
        log.severe("id = " + id);
        return "test.jsp";
    }
    
    // ${mvc.uri('MyController#myMethod' {'foo': 'bar', 'id': 42})}
    
}
