#!/bin/bash

SVC=$1
PORT=$2


echo "SVC=$SVC, PORT=$PORT"
echo "this script run."

mysql --host "$SVC" --port "$PORT" -u root -p1234 viewcount-db < /db-init.sql
sleep 2


# docker buildx build --platform linux/arm64 --push -t ojt90902/viewcount-test:latest .