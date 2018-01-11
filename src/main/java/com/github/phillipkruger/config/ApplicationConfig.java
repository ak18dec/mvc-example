package com.github.phillipkruger.config;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import lombok.Getter;
import lombok.extern.java.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * Getting the feeds to display
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@Log
@ApplicationScoped
public class ApplicationConfig {
   
    @Getter
    @Inject
    @ConfigProperty(name = "dashboardName",defaultValue = "Demo")
    private String dashboardName;
    
    @Inject
    //@ConfigProperty(name = "feeds",defaultValue = "[{\"url\":\"http://feeds.news24.com/articles/news24/TopStories/rss\",\"interval\":3600},{\"url\":\"http://feeds.news24.com/articles/news24/SouthAfrica/rss\",\"interval\":3600}]")
    @ConfigProperty(name = "feeds",defaultValue = "[{\"url\":\"https://www.infoworld.com/blog/open-sources/index.rss\",\"interval\":3600},{\"url\":\"https://www.javaworld.com/index.rss\",\"interval\":3600}]")
    private JsonArray feeds;
    
    public List<FeedConfig> getFeeds(){
        if(feeds==null || feeds.isEmpty())return Collections.EMPTY_LIST;
        
        List<FeedConfig> feedList = new ArrayList<>();
        
        Iterator<JsonValue> feedIterator = feeds.iterator();
        
        while(feedIterator.hasNext()){
            JsonObject feed = (JsonObject)feedIterator.next();
            int interval = feed.getInt("interval");
            String surl = feed.getString("url");
            URL url = toURL(surl);
            
            if(url!=null)feedList.add(new FeedConfig(url, interval));
        }
        
        return feedList;
    }
    
    private URL toURL(String url){
        try {
            return new URL(url);
        } catch (MalformedURLException ex) {
            log.log(Level.SEVERE, "Ignoring Malformed URL [{0}]", url);
            return null;
        }
    }
    
    
}
