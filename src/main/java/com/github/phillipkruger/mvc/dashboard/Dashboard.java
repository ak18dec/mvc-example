package com.github.phillipkruger.mvc.dashboard;

import com.github.phillipkruger.mvc.feed.Feed;
import java.io.Serializable;
import java.net.URL;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dashboard definition
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@Named("dashboard")
@SessionScoped
@Data
@NoArgsConstructor @AllArgsConstructor
public class Dashboard implements Serializable {
    private String name;
    private URL icon;
    private List<Feed> feeds;
}
