#!/bin/bash

sudo apt update
sudo apt-get install -y postgresql-client
docker run --name postgres-container -d -e POSTGRES_PASSWORD=admin -p 5432:5432 postgres

echo "############################################# Waiting for Docker Image to start ############################################################"
sleep 30
echo "############################################# Initiailising Chinook Database ###############################################################"
export PGPASSWORD="admin"
psql -U postgres -h 127.0.0.1 -f ${CODESPACE_VSCODE_FOLDER}/.setupScripts/Chinook_PostgreSql.sql;