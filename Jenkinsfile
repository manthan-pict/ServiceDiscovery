def containerId=""
pipeline {
    agent none
    stages {
        stage('Build SvcDiscoveryApp') {
            agent {
                    docker {
                        image 'maven:3-alpine'
                        args '-v /root/.m2:/root/.m2'
                    }
                  }
            steps {
                    sh 'mvn -X clean install -DskipTests'
                  }
            }
        stage('Staging SvcDiscovery-DockerImage') {
            agent any
            steps{
                    script{
                        containerId = sh (
                        script :'docker ps -a -q --filter="name=mysd*"',
                        returnStdout: true
                        ).trim()
                        if("${containerId}"!= ""){
                          sh 'docker rm -f $(docker ps -a -q --filter="name=mysd*")'
                          sh 'docker rmi -f $(docker images --filter=reference=my-sd-svc --format "{{.ID}}")'
                        }
                    }
                    sh 'docker build -t my-sd-svc:1 .'
                }
         }
        stage('Staging Nginx-DockerImage') {
             agent any
             steps{
                     script{
                         containerId = sh (
                         script :'docker ps -a -q --filter="name=ngx"',
                         returnStdout: true
                         ).trim()
                         if("${containerId}"!= ""){
                           sh 'docker rm -f $(docker ps -a -q --filter="name=ngx")'
                           sh 'docker rmi -f $(docker images --filter=reference=ngx-consulsd --format "{{.ID}}")'
                         }
                     }
                     sh 'docker build -t ngx-consulsd:1 ./nginx_setup'
                 }
        }
         stage('Containerising Consul-Nginx') {
             agent any
              steps {
                      sh 'sh dockerConsulNginx.sh'
                    }
        }
        stage('Containerising SvcDiscoveryApp') {
            agent any
             steps {
                     sh 'sh dockerSvcDiscovery.sh'
                   }
       }
    }
 }