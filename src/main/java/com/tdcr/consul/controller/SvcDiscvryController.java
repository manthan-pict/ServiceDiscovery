package com.tdcr.consul.controller;

import com.tdcr.consul.config.AppPropsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RefreshScope
public class SvcDiscvryController {

    private static Logger LOG = LoggerFactory.getLogger(SvcDiscvryController.class);


    @Value("${spring.cloud.consul.discovery.instanceId}")
    String instanceID;

    @Value("${spring.cloud.consul.discovery.healthCheckUrl:test}")
     String healthCheckUrl;

    @Value("${config.toggle:false}")
    String toggle;

    @Autowired
    AppPropsConfig config;


    @RequestMapping("/sd")
    public  String getInstanceID(){
        LOG.info(healthCheckUrl);
        return instanceID;
    }

    @GetMapping(value = "/status")
    public ResponseEntity<String> status(){
        String message =  "{\"status\":\""+(config.getToggle().equals("false")?"DOWN":"UP")+"\"}";
        LOG.info(message);
        return new ResponseEntity<>(message,config.getToggle().equals("false")?HttpStatus.FORBIDDEN:HttpStatus.OK);
    }

}
