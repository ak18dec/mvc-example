package com.github.phillipkruger.mvc.feed;

import java.io.Serializable;
import java.net.URL;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class Entry implements Serializable {
    private int id;
    private String title;
    private URL link;
    private URL uri;
    private Date publishedDate;
    private Date updatedDate;
    private String type;
    private String content;
    private URL imageLink;
}