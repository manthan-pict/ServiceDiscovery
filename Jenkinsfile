def containerId=""
pipeline {
    agent none
    stages {
        stage('Build HystrixApp') {
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

        stage('Staging Hystrix-DockerImage') {
            agent any
            steps{
                    script{
                        containerId = sh (
                        script :'docker ps -a -q --filter="name=hystrix"',
                        returnStdout: true
                        ).trim()
                        if("${containerId}"!= ""){
                          sh 'docker rm -f $(docker ps -a -q --filter="name=hystrix")'
                          sh 'docker rmi -f $(docker images --filter=reference=hystrix --format "{{.ID}}")'
                        }
                    }
                    sh 'docker build -t hystrix:1.0 .'
                }
         }
        stage('Containerising HystrixApp') {
            agent any
             steps {
                     sh 'sh dockercompose.sh'
                   }
           }
       stage('Staging Nginx-DockerImage') {
           agent any
            steps {
               script{
               containerId = sh (
                       script :'docker ps -a -q --filter="name=nginx"',
                       returnStdout: true
                       ).trim()
                   if("${containerId}"!= ""){
                       sh 'docker rm -f $(docker ps -a -q --filter="name=nginx")'
                       sh 'docker rmi -f $(docker images --filter=reference=mynginx --format "{{.ID}}")'
                   }
               }
                sh 'docker build -t mynginx:1.0 ./nginx_setup/'
              }
          }
          stage('Containerising Nginx') {
              agent any
               steps {
                       sh 'docker run -p 8082:80 --name nginx -v /var/run/docker.sock:/var/run/docker.sock:ro --link=hystrix1 --link=hystrix2 -d mynginx:1.0'
                     }
             }
    }
 }