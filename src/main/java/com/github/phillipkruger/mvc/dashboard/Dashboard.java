package com.github.phillipkruger.mvc.dashboard;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dashboard definition
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@Named("dashboard")
@RequestScoped
@Data
@NoArgsConstructor @AllArgsConstructor
public class Dashboard {
    private String name;
}
