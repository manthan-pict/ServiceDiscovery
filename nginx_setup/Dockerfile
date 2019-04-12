#getting base image
FROM nginx
EXPOSE 80
# Forward request logs to Docker log collector
RUN ln -sf /dev/stdout /var/log/nginx/access.log && ln -sf /dev/stderr /var/log/nginx/error.log
STOPSIGNAL SIGTERM
RUN apt-get update && apt-get install -y -q wget curl unzip lsb-release runit
ENV CT_URL https://releases.hashicorp.com/consul-template/0.19.5/consul-template_0.19.5_linux_amd64.zip
RUN curl -O $CT_URL
RUN unzip consul-template_0.19.5_linux_amd64.zip -d /usr/local/bin
#ADD ./consulTmplt/ /usr/local/bin
ADD nginx.service /etc/service/nginx/run
ADD consul-template.service /etc/service/consul-template/run
RUN chmod +x /etc/service/nginx/run
RUN chmod +x /etc/service/consul-template/run
RUN rm -v /etc/nginx/conf.d/*
ADD nginx.conf /etc/consul-templates/nginx.conf
ADD index.html /etc/consul-templates/index.html
#CMD ["/etc/service/consul-template/run"]
CMD ["/usr/bin/runsvdir", "/etc/service"]