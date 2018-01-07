package com.github.phillipkruger.config;

import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Level;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.servlet.ServletContext;
import lombok.extern.java.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * Getting the feeds to display
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@Log
@ApplicationScoped
public class FeedService {
   
    
    
    @Inject
    @ConfigProperty(name = "feeds",defaultValue = "[{'url':'https://www.infoworld.com/blog/open-sources/index.rss','interval':3600},{'url':'http://feeds.dzone.com/java','interval':3600}]")
    private JsonArray feeds;
    
    public void init(@Observes @Initialized(ApplicationScoped.class) ServletContext context) {
        log.severe("==== Loading feeds ====");
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        
        JsonObjectBuilder objectBuilder1 = Json.createObjectBuilder();
        objectBuilder1.add("url", "https://www.infoworld.com/blog/open-sources/index.rss");
        objectBuilder1.add("interval", "3600");
        
        JsonObjectBuilder objectBuilder2 = Json.createObjectBuilder();
        objectBuilder2.add("url", "http://feeds.dzone.com/java");
        objectBuilder2.add("interval", "3600");
        
        arrayBuilder.add(objectBuilder1);
        arrayBuilder.add(objectBuilder2);
        
        JsonArray array = arrayBuilder.build();
        
        String jsonText  = null;
        try(StringWriter sw = new StringWriter();
            JsonWriter jw = Json.createWriter(sw)){
            jw.writeArray(array);
            jsonText = sw.toString();
        } catch (IOException ex) {
            log.log(Level.SEVERE, null, ex);
        }
        
        log.severe(jsonText);
    }
    
    
    
}
