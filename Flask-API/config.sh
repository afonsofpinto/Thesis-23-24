#!/bin/bash

sudo yum update -y
sudo yum install -y python3
sudo yum install -y python3-pip
pip3 install Flask
pip3 install mysql-connector-python
pip3 install flask-cors
nohup python3 /tmp/flask_api.py "$(cat /tmp/bank_addr.tmp)" > /tmp/flask_api.log 2>&1 &
sleep 20
