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

echo "[+] Deploy container to EC2"

ssh ${EC2_USERNAME}@${EC2_HOST} << EOF

  eval $(aws ecr get-login --region $AWS_REGION)

  docker pull ${DOCKER_REGISTRY}/${CIRCLE_PROJECT_REPONAME}:latest

  echo $CIRCLE_PROJECT_REPONAME
  echo ${CIRCLE_PROJECT_REPONAME}
  docker ps -a
  docker ps -a -f name=$CIRCLE_PROJECT_REPONAME
  docker ps -a -f name=${CIRCLE_PROJECT_REPONAME}
  docker ps -a -f name=$CIRCLE_PROJECT_REPONAME -q
  docker ps -a -f name=${CIRCLE_PROJECT_REPONAME} -q
  docker ps -a -f name=scala-akka-boilerplate
  docker ps -a -f name="scala-akka-boilerplate"
  docker ps -a -f name=scala-akka-boilerplate -q
  docker ps -a -f name="scala-akka-boilerplate" -q
  docker ps -a -f name="${CIRCLE_PROJECT_REPONAME}"
  docker ps -a -f name="$CIRCLE_PROJECT_REPONAME" -q

  docker rm -f $(docker ps -a -f name=$CIRCLE_PROJECT_REPONAME -q) &>/dev/null && \
    echo "removed old container" || echo "container not found"

  docker run \
    --detach \
    --network="host" \
    --name ${CIRCLE_PROJECT_REPONAME} \
    ${DOCKER_REGISTRY}/${CIRCLE_PROJECT_REPONAME}:latest
EOF
