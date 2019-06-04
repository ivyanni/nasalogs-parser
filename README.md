# NASA Logs Parser Application
NASA Logs Parser based on Apache Spark

## Description
Repository contains Dockerfile and docker-compose.yml for fast deployment on any environment.
Spark application was written in Java with Maven package manager. Repository also contains scripts which are necessary for instant
HDFS and YARN start and Spark application submit.

## Installation guide
1. `git clone` current repo
2. Create `/logs` directory and put appropriate log file
3. Start services in a background mode: `sudo docker-compose up -d`
4. Wait until master and worker instances are ready
5. Execute `sudo docker exec -it nasalogs-parser_master_1 /bin/bash`
6. Go to `/scripts` directory then execute `standalone_start.sh` for Standalone mode or `yarn_start.sh` for Over YARN mode
7. After application finished use commands below to print the result:  
    7.1. `hadoop fs -cat /output1/part-00000` for first task result  
    7.2. `hadoop fs -cat /output2/part-00000` for second task result  
    7.3. `hadoop fs -cat /output3/part-00000` for third task result  
8. Execute `sudo docker-compose down` command to stop master/worker services

## Build guide
After changes in Spark application sources use `mvn clean install` command to compile app and 
create up-to-date jar package in `/java` directory then re-deploy application
