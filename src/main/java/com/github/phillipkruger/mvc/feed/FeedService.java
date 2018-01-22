package com.github.phillipkruger.mvc.feed;

import com.github.phillipkruger.mvc.config.FeedConfig;
import com.rometools.rome.feed.synd.SyndFeed;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import lombok.NonNull;
import lombok.extern.java.Log;


/**
 * Gets a feed(s) (maybe cached in session)
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@Log
@SessionScoped
public class FeedService implements Serializable{
    
    @Inject
    private FeedFetcher feedFetcher;
    @Inject 
    private FeedMapper feedMapper;
    
    private final Map<Integer,Feed> sessionCached = new HashMap<>();
    
    public Feed getFeed(int hash){
        if(sessionCached.containsKey(hash)){
            Feed cachedFeed = sessionCached.get(hash);
            log.log(Level.FINE, "Loading {0} from cache", cachedFeed.getUri());
            return sessionCached.get(hash);
        }
        return null; // TODO: Throw exception ?    
    }
    
    public List<Feed> getFeeds(List<FeedConfig> feedConfigurations){
        List<Future<SyndFeed>> futureContentList = new ArrayList<>();
        List<Feed> feeds = new ArrayList<>();
        
        feedConfigurations.forEach((feedConfig) -> {
            String feedUrl = feedConfig.getUrl().toString();
            int hash = feedUrl.hashCode();
            if(sessionCached.containsKey(hash)){
                log.log(Level.FINE, "Loading {0} from cache", feedUrl);
                Feed syndFeed = sessionCached.get(hash);
                feeds.add(syndFeed);
            }else{
                log.log(Level.FINE, "Loading {0} from source", feedUrl);
                futureContentList.add(feedFetcher.fetchAsync(feedConfig));
            }
        });
        
        futureContentList.forEach((futureContent) -> {
            try {
                SyndFeed syndFeed = futureContent.get(5, TimeUnit.SECONDS);
                if(syndFeed!=null){
                    Feed freshFeed = feedMapper.toFeed(syndFeed);
                    sessionCached.put(freshFeed.getId(),freshFeed);
                    feeds.add(freshFeed);
                }
            } catch (InterruptedException | ExecutionException | TimeoutException ex) {
                log.log(Level.SEVERE, null, ex);
            }
        });
        
        return feeds;
    }
    
    public void invalidate(@NonNull URL feedUrl){
        int hash = feedUrl.toString().hashCode();
        
        if(sessionCached.containsKey(hash)){
            sessionCached.remove(hash);
        }
    }
    
    public void reload(int id) {
        Feed feed = getFeed(id);
        if(feed!=null){
            FeedConfig config = new FeedConfig(feed.getUri(),feed.getTitle());
            SyndFeed syndfeed = feedFetcher.fetch(config);
            if(syndfeed!=null)sessionCached.put(id, feedMapper.toFeed(syndfeed));
        }
    }   
    
}
