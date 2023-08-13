#!/bin/bash

SVC=$1
PORT=$2

echo "this script run."

mysql --host "$SVC" --port "$PORT" -u root -p1234 viewcount-db < /db-init.sql
sleep 2