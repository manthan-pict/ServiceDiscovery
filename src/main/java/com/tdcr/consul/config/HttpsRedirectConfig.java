package com.tdcr.consul.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class HttpsRedirectConfig {

    @Value("${http.port:9090}")
    private int httpPort;

    @Value("${server.port:8443}")
    private int httpsPort;

    @Autowired
    ServerProperties serverConfig;


    private static Logger LOG = LoggerFactory.getLogger(HttpsRedirectConfig.class);

//    @Bean
    public TomcatServletWebServerFactory servletContainer() throws Exception {

        TomcatServletWebServerFactory  tomcat = new TomcatServletWebServerFactory(){
            @Override
            protected void postProcessContext(Context context) {
                context.addConstraint(getSecurityConstaint());
            }
        };
        tomcat.addAdditionalTomcatConnectors(createConnection());
        tomcat.setSsl(serverConfig.getSsl());
        return tomcat;
    }

    private SecurityConstraint getSecurityConstaint() {
        SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
        return securityConstraint;
    }

    private Connector createConnection() throws Exception {
        verifyPorts();
        final String protocol = "org.apache.coyote.http11.Http11NioProtocol";
        final Connector connector = new Connector(protocol);
        connector.setScheme("http");
        connector.setPort(httpPort);
        connector.setRedirectPort(httpsPort);
        return connector;
    }

    private void verifyPorts() throws Exception {
        if (StringUtils.isEmpty(httpPort) || StringUtils.isEmpty(httpsPort))
            throw new Exception("Inavlid port");
        else
            LOG.info("http.port: {}, https.port: {}",httpPort,httpsPort);
    }
}
