#!/bin/bash

# unofficial bash strict mode
set -euo pipefail
IFS=$'\n\t'

title() {
cat<<EOF

     __         _       __    __           _           __
    / /_  _____(_)___ _/ /_  / /__      __(_)___  ____/ /
   / __ \/ ___/ / __\`/ __ \/ __/ | /| / / / __ \/ __  /
  / /_/ / /  / / /_/ / / / / /_ | |/ |/ / / / / / /_/ /
 /_.___/_/  /_/\__, /_/ /_/\__/ |__/|__/_/_/ /_/\__,_/
             /____/

EOF
}
title

DOCKER_REGISTRY=${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com
CONTAINER_PORT=3000
HOST_PORT=${HTTP_PORT}
# mounted volume
LOG_PATH="/vol/log/${CIRCLE_PROJECT_REPONAME}"

########## CUSTOM ENVIRONMENT VARIABLES ##########
# example MY_ENV_VAR=VALUE

echo "[+] Deploy container to EC2"

ssh ${EC2_USERNAME}@${EC2_HOST} << EOF

  # setup logs
  sudo mkdir -p ${LOG_PATH}
  sudo chmod 777 ${LOG_PATH}

  eval $(aws ecr get-login --region $AWS_REGION)
  docker pull ${DOCKER_REGISTRY}/${CIRCLE_PROJECT_REPONAME}:latest

  # remove old container by name
  docker ps -a -q -f name=${CIRCLE_PROJECT_REPONAME} | xargs --no-run-if-empty docker rm -f

  ########## CUSTOM COMMANDS ##########

  # run container in background
  docker run \
    --detach \
    -e SLACK_WEBHOOK_URL="${SLACK_WEBHOOK_URL}" \
    -e LOGENTRIES_TOKEN="${LOGENTRIES_TOKEN}" \
    -p ${HOST_PORT}:${CONTAINER_PORT} \
    -v ${LOG_PATH}:/opt/docker/log \
    --log-driver none \
    --name ${CIRCLE_PROJECT_REPONAME} \
    ${DOCKER_REGISTRY}/${CIRCLE_PROJECT_REPONAME}:latest
  
  ########## END CUSTOM COMMANDS ##########

  # delete dangling images <none>
  docker images -q -f dangling=true | xargs --no-run-if-empty docker rmi
  # delete dangling volumes
  docker volume ls -q -f dangling=true | xargs --no-run-if-empty docker volume rm

EOF
