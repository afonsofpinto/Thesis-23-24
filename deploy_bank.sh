#!/bin/bash


########################################################### AWS PEM KEY #############################################################

icacls labsuser.pem /inheritance:r /grant:r "afons:(RX)"
key_file="labsuser.pem"

########################################################### BANK DATABASE #############################################################

cd Datawarehouse

echo 'Deploying BANK DATAWAREHOUSE...'
bash deploy_rds.sh 
cd ..
wait 

HOST=$(<"Datawarehouse/bank_addr.tmp")
USER="root"
PASSWORD="password"
DATABASE="bank_dw"

SQL_SCRIPT="Datawarehouse/bank_dw.sql"

mysql -h"$HOST" -u"$USER" -p"$PASSWORD" -D"$DATABASE" < $SQL_SCRIPT


python Datawarehouse/populate_dw.py "$(cat Datawarehouse/bank_addr.tmp)"

########################################################### KAFKA CLUSTER #############################################################

cd Kafka

echo 'Deploying Kafka...'
bash deploy_kafka.sh $key_file 
wait

cp kafka_hostnames.tmp "../Blockchain/DLT-Thesis/blockchainAPI/src/main/java/pt/tecnico/blockchain/contracts/tes"

cd ..

########################################################### LAUNCH KAFKA CONNECTOR #########################################################

cd Kafka-Connector

echo 'Deploying KAFKA CONNECTOR...'
bash deploy_connector.sh 
wait 

cd ..


########################################################### LAUNCH FLASK API #########################################################

cd Flask-API

echo 'Deploying FLASK API...'
bash deploy_flask.sh 
wait 

cp flask_instance.tmp ../BankOperator-Frontend/public

cd ..

########################################################### LAUNCH BANK LEGACY SYSTEM ####################################################

mkdir output

cd Cobol

cobc -x testecob.cob 

cd ..


########################################################### LAUNCH COBOL CONNECTOR #########################################################

cd Cobol-Connector

start python server.py "$(cat ../Datawarehouse/bank_addr.tmp)"

cd ..








