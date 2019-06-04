#!/bin/bash

$HADOOP_HOME/sbin/start-dfs.sh

hadoop fs -mkdir /logs
hadoop fs -put /logs/access_log_Jul95 /logs/log

if $(hadoop fs -test -d /output1) ; then hadoop fs -rm -r /output1; fi
if $(hadoop fs -test -d /output2) ; then hadoop fs -rm -r /output2; fi
if $(hadoop fs -test -d /output3) ; then hadoop fs -rm -r /output3; fi

spark-submit --class com.github.ivyanni.nasalogs_parser.Application /java/nasalogs-parser-1.0.jar logs/log