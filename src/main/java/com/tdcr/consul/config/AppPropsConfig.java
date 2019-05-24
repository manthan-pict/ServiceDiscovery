package com.tdcr.consul.config;

/*
 * To load runtime distributed configuration from Consul
 */


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "config")
public class AppPropsConfig {

    private String toggle;
    private String URL;
    private String imageId;
    private String dockerDeamon;

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getDockerDeamon() {
        return dockerDeamon;
    }

    public void setDockerDeamon(String dockerDeamon) {
        this.dockerDeamon = dockerDeamon;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getToggle() {
        return toggle;
    }

    public void setToggle(String toggle) {
        this.toggle = toggle;
    }

}
