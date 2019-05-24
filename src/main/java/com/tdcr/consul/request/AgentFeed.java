package com.tdcr.consul.request;

import java.util.HashMap;
import java.util.Map;

    public class AgentFeed {

        private String imageId;
        private String dockerDeamon;
        private Map<String,Integer> errorMap = new HashMap<>();
        private String containerName;

        public AgentFeed() {
        }

        public AgentFeed(String imageId, String dockerDeamon, Map<String, Integer> errorMap, String containerName) {
            this.imageId = imageId;
            this.dockerDeamon = dockerDeamon;
            this.errorMap = errorMap;
            this.containerName = containerName;
        }

        public AgentFeed(String imageId, String dockerDeamon, String containerName) {
            this.imageId = imageId;
            this.dockerDeamon = dockerDeamon;
            this.containerName = containerName;
        }

        /*public AgentFeed(String imageId, String dockerDeamon, Map<String, Integer> errorMap) {
        super();
        this.imageId = imageId;
        this.dockerDeamon = dockerDeamon;
        this.errorMap = errorMap;
    }*/

        public void putError(String errorType) {
            if(errorMap.containsKey(errorType)){
                errorMap.put(errorType, errorMap.get(errorType)+1);
            }
            else
            {
                errorMap.put(errorType,1);
            }
        }
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

        public Map<String, Integer> getErrorMap() {
            return errorMap;
        }

        public void setErrorMap(Map<String, Integer> errorMap) {
            this.errorMap = errorMap;
        }

        public String getContainerName() {
            return containerName;
        }

        public void setContainerName(String containerName) {
            this.containerName = containerName;
        }
    }

