package com.github.phillipkruger.mvc.feed;

import com.github.phillipkruger.config.FeedConfig;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import lombok.extern.java.Log;

/**
 * Gets Feed content
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@Log
@RequestScoped
public class FeedService {
    
    @Inject
    private FeedFetcher feedFetcher;
    
    public List<SyndFeed> getFeeds(List<FeedConfig> feedConfigurations){
        
        List<Future<SyndFeed>> futureContentList = new ArrayList<>();
        
        for(FeedConfig feedConfig:feedConfigurations){
            URL url = feedConfig.getUrl();
            
            futureContentList.add(feedFetcher.fetchAsync(url));
        }
        
        List<SyndFeed> feeds = new ArrayList<>();
        
        for(Future<SyndFeed> futureContent:futureContentList){
            try {
                SyndFeed syndFeed = futureContent.get(5, TimeUnit.SECONDS);
                if(syndFeed!=null){
                    printFeed(syndFeed);
                    feeds.add(syndFeed);
                }
            } catch (InterruptedException | ExecutionException | TimeoutException ex) {
                log.log(Level.SEVERE, null, ex);
            }
        }
        
        return feeds;
    }

    public SyndFeed getFeed(FeedConfig feedConfiguration){
        return feedFetcher.fetch(feedConfiguration.getUrl());
    }
    
    public SyndFeed getFeed(int id){
        return feedFetcher.fetch(id);
    }
    
    private void printFeed(SyndFeed f) {
        log.fine("============== " + f.getTitle() + " ==============");
        log.fine("author: " + f.getAuthor());
        log.fine("copyright: " + f.getCopyright());
        log.fine("description: " + f.getDescription());
        log.fine("feedType: " + f.getFeedType());
        log.fine("image: " + f.getImage());
        log.fine("link: " + f.getLink());
        log.fine("published date: " + f.getPublishedDate());
        log.fine("uri: " + f.getUri());
        log.fine("language: " + f.getLanguage());
        log.fine("entries: " + f.getEntries().size());
        
        if(!f.getEntries().isEmpty()){
            SyndEntry entry = (SyndEntry)f.getEntries().get(0);
            log.fine("    >>>>> " + entry.getTitle());
            log.fine("          > author: " + entry.getAuthor());
            log.fine("          > link: " + entry.getLink());
            log.fine("          > uri: " + entry.getUri());
            log.fine("          > contributors: " + entry.getContributors());
            log.fine("          > enclosures: " + entry.getEnclosures());
            log.fine("          > links: " + entry.getLinks());
            log.fine("          > modules: " + entry.getModules());
            log.fine("          > published date: " + entry.getPublishedDate());
            log.fine("          > updated date: " + entry.getUpdatedDate());
            log.fine("          > wire entry: " + entry.getWireEntry());
            log.fine("          > contents: " + entry.getContents());
            
            SyndContent content = entry.getDescription();
            log.fine("          >> mode: " + content.getMode());
            log.fine("          >> type: " + content.getType());
            log.fine("          >> value: " + content.getValue());
            
            log.fine("          >>>>>>>> classname: " + entry.getDescription().getClass().getName());
            
        }
        
    }
    
}
