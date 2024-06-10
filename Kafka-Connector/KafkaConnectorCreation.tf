terraform {
  required_version = ">= 1.0.0, < 2.0.0"

  required_providers {
    aws = {
      source = "hashicorp/aws"
      version = "~> 4.0"
    }
    null = {
      source = "hashicorp/null"
      version = "3.1.0"
    }
  }
}

variable "security_group_name" {
  description = "The name of the security group"
  type        = string
  default     = "kafka-connector-group"
}

variable "aws_access_key" {}
variable "aws_secret_key" {}
variable "aws_token" {}
variable "aws_keyname" {}


provider "aws" {
  region = "us-east-1"
  access_key = var.aws_access_key
  secret_key = var.aws_secret_key
  token = var.aws_token
}

resource "aws_security_group" "kafka_connector_security_group" {
  name        = var.security_group_name
  description = "Allow inbound traffic on port 22, 9092 and 3306"
  
  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 3306
    to_port     = 3306
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 9092
    to_port     = 9092
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_instance" "kafka_connector_instance" {
  ami             = "ami-090e0fc566929d98b"
  instance_type   = "t2.small"
  key_name        = "vockey"
  security_groups = [aws_security_group.kafka_connector_security_group.name]
  
  tags = {
    Name = "kafka-connector-server"
  }
}


resource "null_resource" "updateConfigs" {
  depends_on = [ aws_instance.kafka_connector_instance ]

  connection {
    type        = "ssh"
    user        = "ec2-user"
    private_key = file("../labsuser.pem")
    host        = aws_instance.kafka_connector_instance.public_dns

  }
  provisioner "file" {
    source      = "${path.module}/config.sh"
    destination = "/tmp/config.sh"
  }

    provisioner "file" {
    source      = "${path.module}/Connector"
    destination = "/tmp/Connector"
  }

  provisioner "file" {
    source      = "../Datawarehouse/bank_addr.tmp"
    destination = "/tmp/bank_addr.tmp"
  }

    provisioner "file" {
    source      = "../Kafka/kafka_hostnames.tmp"
    destination = "/tmp/kafka_hostnames.tmp"
  }

  provisioner "remote-exec" {
    inline = [
      "sudo chmod +x /tmp/config.sh",
      "sudo /tmp/config.sh", 
    ]
  }

  
}

resource "local_file" "kafka_connector_instance_hostname" {
  filename = "${path.module}/kafka_connector_instance.tmp"
  content  = aws_instance.kafka_connector_instance.public_dns
}

