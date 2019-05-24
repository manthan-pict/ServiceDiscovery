package com.tdcr.consul.controller;

import com.tdcr.consul.config.AppPropsConfig;
import com.tdcr.consul.request.AgentFeed;
import com.tdcr.consul.web.PostAgentFeed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
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


    @Value("${spring.cloud.consul.discovery.instanceId}")
    String instanceID;

    @Value("${spring.cloud.consul.discovery.healthCheckUrl:test}")
     String healthCheckUrl;

    @Value("${config.toggle:false}")
    String toggle;

    @Value("${config.URL:localhost}")
    String URL;

    @Value("${config.imageId:ServiceApp}")
    String imageId;

    @Value("${config.dockerDeamon:defaultDeamon}")
    String dockerDeamon;

    @Value("${spring.cloud.consul.discovery.containerName:containerNameRandom}")
    String containerNameRandom;

    @Value("${config.ContainerName:ContainerNameConsule}")
    String ContainerNameConsule;

    @Autowired
    AppPropsConfig config;

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    RestTemplate restTemplate;

    private AgentFeed agentFeed = new AgentFeed(imageId, dockerDeamon, containerNameRandom);

    private double cron = 0 ;

    @RequestMapping("/sd")
    public  String getInstanceID(){
        LOG.info(instanceID);
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
        agentFeed.putError("NullPointer");
        LOG.info("new null pointer exception is detected exception count :{}",agentFeed.getErrorMap().get("NullPointer") );
        //throw new RuntimeException(String.valueOf(agentFeed.getErrorMap().get("NullPointer")));
    }

    @PostMapping("/databaseConnectivityError")
    public int addDatabaseConnectivityError() {
        agentFeed.putError("databaseConnectivityError");
        LOG.info("new database connectivity issue is detected total connectivity issues are:{}", agentFeed.getErrorMap().get("databaseConnectivityError"));
        return agentFeed.getErrorMap().get("databaseConnectivityError");
    }

    @PostMapping("/timeOutException")
    public int timeoutExceptions() {
        agentFeed.putError("timeOutException");
        LOG.info("new timeout exception is detected total exception issues are:{}", agentFeed.getErrorMap().get("timeOutException"));
        return agentFeed.getErrorMap().get("timeOutException");
    }

    @Scheduled(cron = "0,30 * * * * *")
    public void scheduleTaskWithCronExpression() {
        LOG.info("Cron Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        cron = cron + 1;

        //TODO : databank method need to be copied here.
        //URI uri = discoveryClient.getInstances("MONITORAPP").stream().map(ServiceInstance::getUri).findFirst().map(s -> s.resolve("/feed")).get();
        //restTemplate.postForObject(uri, agentFeed, null);

        //restTemplate.postForObject(config.getURL(), agentFeed, null);


        //PostAgentFeed.postAgentFeedToMonitor(agentFeed);

    }

    @GetMapping("/cron")
    public double getCronNumber() {
        return cron;
    }

    @GetMapping("/getURL")
    public String invokeService() {
        //URI uri = discoveryClient.getInstances("DataBank").stream().map(ServiceInstance::getUri).findFirst().map(s -> s.resolve("/updateStatus")).get();
        //return restTemplate.postForObject(uri, "hi from service discovery", String.class);
        return config.getURL();
    }

}
