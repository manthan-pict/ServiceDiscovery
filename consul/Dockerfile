#getting base image
FROM progrium/consul

ADD /consul/config/consul_watches_config.json /etc/consul.d/config.json
ADD /consul/config/scripts/ /tmp
RUN sh /tmp/installJQ.sh
RUN chmod +x /tmp/script.sh