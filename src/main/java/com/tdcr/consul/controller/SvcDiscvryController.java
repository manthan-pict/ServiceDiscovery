package com.tdcr.consul.controller;

import com.tdcr.consul.config.AppPropsConfig;
import com.tdcr.consul.request.AgentFeed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.text.Format;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@RestController
@RefreshScope
public class SvcDiscvryController {

    private static Logger LOG = LoggerFactory.getLogger(SvcDiscvryController.class);

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    //RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.cloud.consul.discovery.instanceId}")
    String instanceID;

    @Value("${spring.cloud.consul.discovery.healthCheckUrl:test}")
     String healthCheckUrl;

    @Value("${config.toggle:false}")
    String toggle;

    @Autowired
    AppPropsConfig config;

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    RestTemplate restTemplate;

    private int nullPointerExceptions = 0;
    private int databaseConnectivityErrors = 0 ;
    private int timeoutExceptions;

    AgentFeed agentFeed = new AgentFeed();

    private int cron = 0 ;

    @RequestMapping("/sd")
    public  String getInstanceID(){
        LOG.info(healthCheckUrl);
        return instanceID + healthCheckUrl;
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
        cron = cron + 1;

        //restTemplate.postForObject()


    }

    @GetMapping("/cron")
    public int getCronNumber() {
        return cron;
    }

    @GetMapping("/databank")
    public String invokeService() {
        URI uri = discoveryClient.getInstances("DataBank").stream().map(serviceInstance -> serviceInstance.getUri()).findFirst().map(s -> s.resolve("/updateStatus")).get();
        return restTemplate.postForObject(uri, "hi from service discovery", String.class);

    }

}
