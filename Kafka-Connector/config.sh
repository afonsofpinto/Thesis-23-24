#!/bin/bash

# Update package lists
sudo yum update -y

# Install Java 17
wget https://download.oracle.com/java/17/latest/jdk-17_linux-x64_bin.rpm

sudo rpm -Uvh jdk-17_linux-x64_bin.rpm

cat <<EOF | sudo tee /etc/profile.d/jdk.sh
export JAVA_HOME=/usr/java/default
export PATH=$PATH:$JAVA_HOME/bin
EOF

source /etc/profile.d/jdk.sh

# Download Maven 3.6.3 binary tar.gz
wget https://archive.apache.org/dist/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz

# Extract Maven archive
tar -zxvf apache-maven-3.6.3-bin.tar.gz

# Move extracted directory to /opt
sudo mv apache-maven-3.6.3 /opt/

# Create a symbolic link to Maven binary
sudo ln -s /opt/apache-maven-3.6.3/bin/mvn /usr/bin/mvn

# Check Maven version
mvn -version

# Navigate to the directory containing the Maven project
cd /tmp/Connector

# Build and run the Maven project
mvn install

# Run the Java application
nohup mvn exec:java -Dexec.mainClass=tecnico.com.Main -Dexec.args="/tmp/kafka_hostnames.tmp /tmp/bank_addr.tmp" > /tmp/connector.log 2>&1 &

sleep 20

