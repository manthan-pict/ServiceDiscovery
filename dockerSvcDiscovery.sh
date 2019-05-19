docker run -p 8085:8085 -e CONSUL=consul -v /var/run/docker.sock:/var/run/docker.sock:ro --link=consul --name mysd1 -d my-sd-svc:1

docker network connect bunit mysd1

docker run -p 8086:8086 -e CONSUL=consul -v /var/run/docker.sock:/var/run/docker.sock:ro --link=consul --name mysd2 -d my-sd-svc:1

docker network connect bunit mysd2