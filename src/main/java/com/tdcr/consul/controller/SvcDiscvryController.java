package com.tdcr.consul.controller;

import com.tdcr.consul.config.AppPropsConfig;
import com.tdcr.consul.request.AgentFeed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.Format;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@RestController
@RefreshScope
public class SvcDiscvryController {

    private static Logger LOG = LoggerFactory.getLogger(SvcDiscvryController.class);

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");


    @Value("${spring.cloud.consul.discovery.instanceId}")
    String instanceID;

    @Value("${spring.cloud.consul.discovery.healthCheckUrl:test}")
     String healthCheckUrl;

    @Value("${config.toggle:false}")
    String toggle;

    @Autowired
    AppPropsConfig config;

    private int nullPointerExceptions;
    private int databaseConnectivityErrors;
    private int timeoutExceptions;

    AgentFeed agentFeed;

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

    @PostMapping("/NullPointer")
    public void addNulPointerException() throws RuntimeException {
        nullPointerExceptions += 1;
        agentFeed.putError("NullPointer");
        LOG.info("new null pointer exception is detected exception count :{}",nullPointerExceptions );
        throw new RuntimeException(String.valueOf(nullPointerExceptions));
    }

    @PostMapping("/databaseConnectivityError")
    public int AddDatabaseConnectivityError() {
        databaseConnectivityErrors += 1;
        agentFeed.putError("databaseConnectivityError");
        LOG.info("new database connectivity issue is detected total connectivity issues are:{}", databaseConnectivityErrors);
        return databaseConnectivityErrors;
    }

    @Scheduled(cron = "0,30 * * * * *")
    public void scheduleTaskWithCronExpression() {
        LOG.info("Cron Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));


    }

}
