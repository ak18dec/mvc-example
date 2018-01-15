package com.github.phillipkruger.mvc.feed;

import com.github.phillipkruger.config.FeedConfig;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Asynchronous;
import org.eclipse.microprofile.faulttolerance.Retry;


/**
 * Goes and fetch a feed (maybe asynchronous)
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@Log
@SessionScoped
public class FeedFetcher implements Serializable{
    
    @Inject
    @ConfigProperty(name = "proxyHost", defaultValue = "")
    private String proxyHost;
    
    @Inject
    @ConfigProperty(name = "proxyPort", defaultValue = "")
    private int proxyPort;
    
    @Inject
    @ConfigProperty(name = "proxyUser", defaultValue = "")
    private String proxyUser;
    
    @Inject
    @ConfigProperty(name = "proxyPassword", defaultValue = "")
    private String proxyPassword;
    
    private String proxyAuthorization = null;
    
    private final Map<Integer,SyndFeed> sessionCached = new HashMap<>();
    
    @PostConstruct
    public void init(){
        if(proxyHost!=null && !proxyHost.isEmpty())System.setProperty("http.proxyHost", proxyHost); // eg. webproxy.mmih.biz
        if(proxyPort>-1)System.setProperty("http.proxyPort", String.valueOf(proxyPort)); // eg. 8080
        if(proxyUser!=null && !proxyUser.isEmpty() && proxyPassword!=null && !proxyPassword.isEmpty()){
            String up = proxyUser + ":" + proxyPassword;
            this.proxyAuthorization = new String(Base64.getEncoder().encode(up.getBytes()));
        }
    }
    
    public SyndFeed fetch(@NonNull FeedConfig feedConfig){
        return fetch(feedConfig, false);
    }
    
    public SyndFeed fetch(@NonNull FeedConfig feedConfig, boolean noCache){
        URL feedUrl = feedConfig.getUrl();
        
        int hash = feedUrl.toString().hashCode();
        
        if(!noCache && sessionCached.containsKey(hash)){
            log.log(Level.FINE, "Loading {0} from cache", feedUrl.toString());
            return sessionCached.get(hash);
        }else{
            log.log(Level.FINE, "Loading {0} from source", feedUrl.toString());
            try {
                URLConnection uc = feedUrl.openConnection ();
                if(proxyAuthorization!=null && !proxyAuthorization.isEmpty())uc.setRequestProperty("Proxy-Authorization", "Basic " + proxyAuthorization);
                
                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed = input.build(new XmlReader(uc));

                feed.setUri(feedUrl.toString());

                if(feedConfig.getTitle()!=null && !feedConfig.getTitle().isEmpty())feed.setTitle(feedConfig.getTitle());
                
                sessionCached.put(hash, feed);
                return feed;

            } catch (IOException | IllegalArgumentException | FeedException ex) {
                log.log(Level.SEVERE, "Could not read feed [{0}] - {1}", new Object[]{feedUrl, ex.getMessage()});
                // TODO: Cahce a null ?
                return null;
            }
        }
    }
    
    public SyndFeed fetch(int hash){
        if(sessionCached.containsKey(hash)){
            SyndFeed cachedFeed = sessionCached.get(hash);
            log.log(Level.FINE, "Loading {0} from cache", cachedFeed.getUri());
            return sessionCached.get(hash);
        }
        return null; // TODO: Throw exception ?    
    }
    
    public void invalidate(@NonNull URL feedUrl){
        int hash = feedUrl.toString().hashCode();
        
        if(sessionCached.containsKey(hash)){
            sessionCached.remove(hash);
        }
    }
    
    @Asynchronous
    @Retry(maxRetries = 3)
    public Future<SyndFeed> fetchAsync(@NonNull FeedConfig feedConfig) {
        return CompletableFuture.completedFuture(fetch(feedConfig));
    }
}
