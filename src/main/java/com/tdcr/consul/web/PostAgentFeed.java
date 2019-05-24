package com.tdcr.consul.web;

import com.tdcr.consul.request.AgentFeed;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

public class PostAgentFeed {

//    @Value("${spring.cloud.consul.discovery.healthCheckUrl:test}")
//    String healthCheckUrl;

    @Value("${config.URL:localhost}")
    String URL;

    public static void postAgentFeedToMonitor(AgentFeed agentFeed)
    {
        //final String uri = "http://localhost:8081/feed";

        //EmployeeVO newEmployee = new EmployeeVO(-1, "Adam", "Gilly", "test@email.com");

        RestTemplate restTemplate = new RestTemplate();
       // EmployeeVO result = restTemplate.postForObject( uri, agentFeed, EmployeeVO.class);

       // System.out.println(result);



        //URI uri = discoveryClient.getInstances("MONITORAPP").stream().map(ServiceInstance::getUri).findFirst().map(s -> s.resolve("/feed")).get();
        //restTemplate.postForObject(uri, agentFeed, null);

        //restTemplate.postForObject(config.getURL(), agentFeed, null);
    }
}
