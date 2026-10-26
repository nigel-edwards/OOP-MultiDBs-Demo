#!/bin/bash

sudo apt update
sudo apt install mysql-client -y
docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=rootpassword -e MYSQL_DATABASE=mydb -p 3306:3306 -d mysql:latest

echo "############################################# Waiting for Docker Image to start ############################################################"
sleep 30
echo "############################################# Initiailising Chinook Database ###############################################################"
mysql -h 127.0.0.1 -P 3306 -u root -prootpassword < ${CODESPACE_VSCODE_FOLDER}/.setupScripts/Chinook_MySql.sql
mysql --version