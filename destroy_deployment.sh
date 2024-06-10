#!/bin/bash


rm BankOperator-Frontend/public/flask_instance.tmp
rm Blockchain/DLT-Thesis/blockchainAPI/src/main/java/pt/tecnico/blockchain/contracts/tes/kafka_hostnames.tmp 
rm -rf "output"


echo "Destroying Kafka..."
cd Kafka
terraform destroy -var-file="../aws-session.tf" -auto-approve &
cd ..

echo 'Destroyng RDS...'
cd Datawarehouse
terraform destroy -var-file="../aws-session.tf" -auto-approve &
cd ..

echo "Destroying Flask..."
cd Flask-API
terraform destroy -var-file="../aws-session.tf" -auto-approve &
cd ..

echo "Destroying Kafka Connector..."
cd Kafka-Connector
terraform destroy -var-file="../aws-session.tf" -auto-approve &
cd ..


wait %1 %2 %3 %4
echo "All resources were destroyed."