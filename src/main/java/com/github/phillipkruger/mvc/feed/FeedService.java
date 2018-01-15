package com.github.phillipkruger.mvc.feed;

import com.github.phillipkruger.config.FeedConfig;
import com.rometools.modules.mediarss.MediaEntryModule;
import com.rometools.modules.mediarss.MediaModule;
import com.rometools.modules.mediarss.types.MediaContent;
import com.rometools.modules.mediarss.types.Metadata;
import com.rometools.modules.mediarss.types.Reference;
import com.rometools.modules.mediarss.types.Thumbnail;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import java.net.MalformedURLException;
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
    
    public List<Feed> getFeeds(List<FeedConfig> feedConfigurations){
        
        List<Future<SyndFeed>> futureContentList = new ArrayList<>();
        
        feedConfigurations.forEach((feedConfig) -> {
            futureContentList.add(feedFetcher.fetchAsync(feedConfig));
        });
        
        List<Feed> feeds = new ArrayList<>();
        
        futureContentList.forEach((futureContent) -> {
            try {
                SyndFeed syndFeed = futureContent.get(5, TimeUnit.SECONDS);
                if(syndFeed!=null){
                    feeds.add(toFeed(syndFeed));
                }
            } catch (InterruptedException | ExecutionException | TimeoutException ex) {
                log.log(Level.SEVERE, null, ex);
            }
        });
        
        return feeds;
    }

    public SyndFeed getFeed(FeedConfig feedConfiguration){
        return feedFetcher.fetch(feedConfiguration);
    }
    
    public Feed getFeed(int id){
        return toFeed(feedFetcher.fetch(id));
    }
    
    public void reload(int id) {
        Feed feed = getFeed(id);
        if(feed!=null){
            FeedConfig config = new FeedConfig(feed.getUri(),feed.getTitle());
            feedFetcher.fetch(config, true);
        }
    }
    
    private Feed toFeed(SyndFeed f) {
        Feed feed = new Feed();
        int hash = f.getUri().hashCode();
        feed.setId(hash);
        feed.setTitle(f.getTitle());
        feed.setAuthor(f.getAuthor());
        feed.setCopyright(f.getCopyright());
        feed.setDescription(f.getDescription());
        feed.setFeedType(f.getFeedType());
        if(f.getImage()!=null)feed.setImage(toURL(f.getImage().getUrl()));
        feed.setLink(toURL(f.getLink()));
        feed.setPublishedDate(f.getPublishedDate());
        feed.setUri(toURL(f.getUri()));
        feed.setLanguage(f.getLanguage());
        
        List<Entry> entries = new ArrayList<>();
        
        if(!f.getEntries().isEmpty()){
            f.getEntries().forEach((e) -> {
                entries.add(toEntry(e));
            });
        }
        feed.setEntries(entries);
        
        return feed;
    }
    
    private Entry toEntry(SyndEntry e){
        Entry entry = new Entry();
        int hash = e.getUri().hashCode();
        entry.setId(hash);
        entry.setTitle(e.getTitle());
        entry.setLink(toURL(e.getUri()));
        entry.setUri(toURL(e.getLink()));
        entry.setPublishedDate(e.getPublishedDate());
        entry.setUpdatedDate(e.getUpdatedDate());
        
        SyndContent content = e.getDescription();
        if(content!=null){
            entry.setType(content.getType());
            entry.setContent(content.getValue());
        }
        
        MediaEntryModule mediaEntryModule = (MediaEntryModule)e.getModule( MediaModule.URI );
        if(mediaEntryModule!=null){
            MediaContent[] mediaContents = mediaEntryModule.getMediaContents();
            if(mediaContents!=null && mediaContents.length>0){
                MediaContent mediaContent = mediaContents[0];
                if(mediaContent!=null && mediaContent.getMetadata()!=null && mediaContent.getMetadata().getThumbnail()!=null && mediaContent.getMetadata().getThumbnail().length>0){
                    Thumbnail thumbnail = mediaContent.getMetadata().getThumbnail()[0];
                    if(thumbnail.getUrl()!=null){
                        try {
                            entry.setImageLink(thumbnail.getUrl().toURL());
                        } catch (MalformedURLException ex) {
                            log.log(Level.SEVERE, null, ex);
                        }
                    }
                }
                // Try from another location
                if(mediaContent!=null && entry.getImageLink()==null){
                    Reference reference = mediaContent.getReference();
                    if(reference!=null && !reference.toString().isEmpty()){
                        entry.setImageLink(toURL(reference.toString()));   
                    }
                }
            }    
        }
        // Try from yet another location
        if(mediaEntryModule!=null && entry.getImageLink()==null){
            Metadata metadata = mediaEntryModule.getMetadata();
            if(metadata!=null && metadata.getThumbnail()!=null && metadata.getThumbnail().length>0){
                Thumbnail thumbnail = metadata.getThumbnail()[0];
                if(thumbnail!=null && thumbnail.getUrl()!=null){
                    try {
                        entry.setImageLink(thumbnail.getUrl().toURL());
                    } catch (MalformedURLException ex) {
                        log.log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return entry;

    }
    
    
    private URL toURL(String url){
        if(url==null || url.isEmpty())return null;
        try {
            return new URL(url);
        } catch (MalformedURLException ex) {
            log.severe(ex.getMessage());
            return null;
        }
        
    }

    
}
