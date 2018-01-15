package com.github.phillipkruger.mvc.feed;

import java.io.Serializable;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class Feed implements Serializable {
    private int id;
    private String title;
    private String author;
    private String copyright;
    private String description;
    private String feedType;
    private URL image;
    private URL link;
    private Date publishedDate;
    private URL uri;
    private String language;
    
    private List<Entry> entries = new LinkedList<>();
    
}