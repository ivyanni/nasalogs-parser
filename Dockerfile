FROM gettyimages/spark:2.4.1-hadoop-3.0

ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/jre

RUN apt-get -y update
RUN apt-get -y install ssh
RUN ssh-keygen -t rsa -f ~/.ssh/id_rsa -P '' && \
cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
COPY /conf/ssh_conf /usr/local/ssh_conf
RUN mv /usr/local/ssh_conf $HOME/.ssh/config

COPY /conf/hadoop/* $HADOOP_CONF_DIR/

ENTRYPOINT ["/bin/bash", "-c", "sh /scripts/hadoop_start.sh"]