package com.github.phillipkruger.mvc.feed;

import com.github.phillipkruger.config.ProxyInfo;
import com.github.phillipkruger.config.FeedConfig;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.logging.Level;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.eclipse.microprofile.faulttolerance.Asynchronous;
import org.eclipse.microprofile.faulttolerance.Retry;


/**
 * Goes and fetch a feed (maybe asynchronous)
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@Log
@RequestScoped
public class FeedFetcher {
    
    @Inject
    private ProxyInfo proxyInfo;
    
    @Retry(maxRetries = 3)
    public SyndFeed fetch(@NonNull FeedConfig feedConfig){
        URL feedUrl = feedConfig.getUrl();
        
        try {
            URLConnection uc = feedUrl.openConnection ();
            if(proxyInfo.hasProxyAuthorization())uc.setRequestProperty("Proxy-Authorization", "Basic " + proxyInfo.getProxyAuthorization());

            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(uc));

            feed.setUri(feedUrl.toString());

            if(feedConfig.getTitle()!=null && !feedConfig.getTitle().isEmpty())feed.setTitle(feedConfig.getTitle());

            return feed;

        } catch (IOException | IllegalArgumentException | FeedException ex) {
            log.log(Level.SEVERE, "Could not read feed [{0}] - {1}", new Object[]{feedUrl, ex.getMessage()});
            // TODO: Cahce a null ?
            return null;
        }
        
    }
    
    @Asynchronous
    @Retry(maxRetries = 3)
    public Future<SyndFeed> fetchAsync(@NonNull FeedConfig feedConfig) {
        return CompletableFuture.completedFuture(fetch(feedConfig));
    }
    
}
