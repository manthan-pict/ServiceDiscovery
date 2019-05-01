#getting base image
FROM progrium/consul

COPY ${PWD}/config/consul_watches_config.json /etc/consul.d/config.json
COPY ${PWD}/config/scripts/ /tmp
RUN sh /tmp/installJQ.sh
RUN chmod +x /tmp/script.sh