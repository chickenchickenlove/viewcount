#!/bin/bash

#docker run --name some-mysql -e MYSQL_ROOT_PASSWORD=my-secret-pw -e MYSQL_DATABASE=mydatabase -e MYSQL_USER=myuser -e MYSQL_PASSWORD=mypassword -d mysql:tag
docker stop mysql
docker rm mysql
docker run -d -v C:/dev/viewcount/container/db-init.sql:/db-init.sql --name mysql -p 23306:3306 -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=viewcount-db mysql:8.0.33
#docker run -d -v $(pwd)/db-init.sql:/db-init.sql --name mysql -p 23306:3306 -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=viewcount-db mysql:8.0.33

sleep 10
#docker run -d -v C:/dev/viewcount/container/db-init.sql:/db-init.sql --name mysql -p 23306:3306 -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=viewcount-db mysql:8.0.33
#docker exec -it mysql mysql -u root -p1234 viewcount-db
#source /db-init.sql

docker exec mysql sh -c 'mysql -u root -p1234 viewcount-db < /db-init.sql'
sleep 5

