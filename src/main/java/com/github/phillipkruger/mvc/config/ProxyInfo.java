package com.github.phillipkruger.mvc.config;

import java.util.Base64;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * Some config for if you are running behind a proxy
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@ApplicationScoped
public class ProxyInfo {
    @Inject
    @ConfigProperty(name = "proxyHost", defaultValue = "")
    private String proxyHost;
    
    @Inject
    @ConfigProperty(name = "proxyPort", defaultValue = "-1")
    private int proxyPort;
    
    @Inject
    @ConfigProperty(name = "proxyUser", defaultValue = "")
    private String proxyUser;
    
    @Inject
    @ConfigProperty(name = "proxyPassword", defaultValue = "")
    private String proxyPassword;
    
    @Getter
    private String proxyAuthorization = null;
    
    @PostConstruct
    public void init(){
        if(proxyHost!=null && !proxyHost.isEmpty())System.setProperty("http.proxyHost", proxyHost); 
        if(proxyPort>-1)System.setProperty("http.proxyPort", String.valueOf(proxyPort));
        if(proxyUser!=null && !proxyUser.isEmpty() && proxyPassword!=null && !proxyPassword.isEmpty()){
            String up = proxyUser + ":" + proxyPassword;
            this.proxyAuthorization = new String(Base64.getEncoder().encode(up.getBytes()));
        }
    }
    
    public boolean hasProxyAuthorization(){
        return this.proxyAuthorization!=null && !this.proxyAuthorization.isEmpty();
    }
}
