package com.tdcr.consul.config;

/*
 * To load runtime distributed configuration from Consul
 */


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "config")
public class AppPropsConfig {

    private String toggle;

    public String getToggle() {
        return toggle;
    }

    public void setToggle(String toggle) {
        this.toggle = toggle;
    }

}
