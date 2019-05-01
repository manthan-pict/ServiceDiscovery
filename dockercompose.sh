docker run -p 9443:8443 -d -v /home/ec2-user/logs/H1:/logs --name hystrix1 hystrix:1.0
docker run -p 9444:8443 -d -v /home/ec2-user/logs/H2:/logs --name hystrix2 hystrix:1.0
