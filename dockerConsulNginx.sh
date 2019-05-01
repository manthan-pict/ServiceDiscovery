#!/usr/bin/env bash
docker run  -d --name=consul -v /home/ec2-user/consul/data:/consul/data -v /var/run/docker.sock:/var/run/docker.sock:ro -p 8400:8400 -p 8500:8500 -p 8600:53/udp -h node1 progrium/consul -server -bootstrap docker-ui-dir /ui -config-file /etc/consul.d/config.json

docker network connect bunit consul

docker run -p 8083:80 --name ngx -v /var/run/docker.sock:/var/run/docker.sock:ro --link=consul -d ngx-consulsd:1

docker network connect bunit ngx