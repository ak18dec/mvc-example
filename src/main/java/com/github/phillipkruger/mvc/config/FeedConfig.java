package com.github.phillipkruger.mvc.config;

import java.net.URL;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Simple Feed definition
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@Data @AllArgsConstructor @NoArgsConstructor
public class FeedConfig {
    @NotNull
    public URL url;
    public String title;
}
