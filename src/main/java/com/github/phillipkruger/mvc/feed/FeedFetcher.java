package com.github.phillipkruger.mvc.feed;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.logging.Level;
import javax.enterprise.context.SessionScoped;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.eclipse.microprofile.faulttolerance.Asynchronous;
import org.eclipse.microprofile.faulttolerance.Retry;


/**
 * Goes and fetch a feed (maybe asynchronous)
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@Log
@SessionScoped
public class FeedFetcher implements Serializable{
    
    private final Map<Integer,SyndFeed> sessionCached = new HashMap<>();
    
    public SyndFeed fetch(@NonNull URL feedUrl){
        return fetch(feedUrl, false);
    }
    
    public SyndFeed fetch(@NonNull URL feedUrl, boolean noCache){
        int hash = feedUrl.toString().hashCode();
        
        if(!noCache && sessionCached.containsKey(hash)){
            log.log(Level.FINE, "Loading {0} from cache", feedUrl.toString());
            return sessionCached.get(hash);
        }else{
            log.log(Level.FINE, "Loading {0} from source", feedUrl.toString());
            try {
                
                //Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.0.0.1", 8080));
                
                //HttpURLConnection httpcon = (HttpURLConnection)feedUrl.openConnection(Proxy.NO_PROXY);
                
                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed = input.build(new XmlReader(feedUrl));

                feed.setUri(feedUrl.toString());

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
    public Future<SyndFeed> fetchAsync(@NonNull URL feedUrl) {
        return CompletableFuture.completedFuture(fetch(feedUrl));
    }
}
