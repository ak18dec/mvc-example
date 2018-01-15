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
    @ConfigProperty(name = "dashboardName",defaultValue = "MVC Example")
    private String dashboardName;
    
    @Getter
    @Inject
    @ConfigProperty(name = "dashboardIcon",defaultValue = "https://www.mvc-spec.org/img/Logo_MVC_www_rgb.png")
    private URL dashboardIcon;
    
    @Inject
    @ConfigProperty(name = "feeds",defaultValue = "["
            + "{\"url\":\"http://feeds.bbci.co.uk/news/world/rss.xml?edition=uk\",\"title\":\"World News\"},"
            + "{\"url\":\"http://feeds.bbci.co.uk/news/rss.xml?edition=uk\",\"title\":\"News\"},"
            + "{\"url\":\"http://feeds.bbci.co.uk/news/technology/rss.xml?edition=uk\",\"title\":\"Technology\"},"
            + "{\"url\":\"http://feeds.bbci.co.uk/news/entertainment_and_arts/rss.xml?edition=uk\",\"title\":\"Entertainment\"}]")
    private JsonArray feeds;
    
    public List<FeedConfig> getFeeds(){
        if(feeds==null || feeds.isEmpty())return Collections.EMPTY_LIST;
        
        List<FeedConfig> feedList = new ArrayList<>();
        
        Iterator<JsonValue> feedIterator = feeds.iterator();
        
        while(feedIterator.hasNext()){
            JsonObject feed = (JsonObject)feedIterator.next();
            String title = feed.getString("title");
            String surl = feed.getString("url");
            URL url = toURL(surl);
            
            if(url!=null)feedList.add(new FeedConfig(url, title));
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
