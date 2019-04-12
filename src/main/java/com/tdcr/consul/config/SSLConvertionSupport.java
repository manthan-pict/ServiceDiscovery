package com.tdcr.consul.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.web.server.Ssl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SSLConvertionSupport {

    private Ssl sslConfig;
    private ServerProperties serverProperties;

    private static Logger LOG = LoggerFactory.getLogger(SSLConvertionSupport.class);

    @Bean
    public Ssl getSsl(){
        return new Ssl();
    }

    @ConfigurationPropertiesBinding
    @Bean
    @ConditionalOnBean(Ssl.class)
    public Converter<String, ServerProperties> locatedConfigConverter(ApplicationContext applicationContext){
        return new Converter<String, ServerProperties>() {
            @Override
            public ServerProperties convert(String jsonString) {
                Map<String, String> map = new HashMap<String, String>();
                initFields(applicationContext);
                try {
                ObjectMapper mapper = new ObjectMapper();
                // convert JSON string to Map
                    map = mapper.readValue(jsonString, new TypeReference<Map<String, String>>(){});
//                    sslConfig.setKeyStoreType(map.get("key-store-type"));
//                    sslConfig.setKeyStore(map.get("key-store"));
//                    sslConfig.setKeyStorePassword(map.get("key-store-password"));
//                    sslConfig.setKeyPassword(map.get("key-password"));
//                    sslConfig.setEnabled("true".equalsIgnoreCase(map.get("enabled"))?true:false);
                    LOG.info("SSLConfig:{}",map.toString());
                } catch (IOException e) {
                    LOG.error(e.getMessage());
                }
//                serverProperties.setSsl(sslConfig);
                serverProperties.setPort(Integer.valueOf(map.get("port")));//server.port
                return serverProperties;
            }

            // This is necessary to avoid conflicts in bean dependencies
            private void initFields(ApplicationContext applicationContext) {
                sslConfig = applicationContext.getBean(Ssl.class);
                serverProperties = applicationContext.getBean(ServerProperties.class);
            }
        };
    }

}
