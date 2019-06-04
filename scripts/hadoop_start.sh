#!/bin/bash

service ssh start;

$HADOOP_HOME/bin/hdfs namenode -format -force;

tail -f /dev/null