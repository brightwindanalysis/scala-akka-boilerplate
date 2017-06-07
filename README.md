# scala-akka-boilerplate

[![CircleCI][circleci-image]][circleci-url]

[circleci-image]: https://circleci.com/gh/brightwindanalysis/scala-akka-boilerplate.svg?style=svg
[circleci-url]: https://circleci.com/gh/brightwindanalysis/scala-akka-boilerplate

### Useful commands
```
# run the project
sbt run

# enable hot reload
sbt ~re-start

# check style
sbt scalastyle

# show project dependencies
sbt dependencyTree

# run tests
sbt test

# run coverage and generate report
sbt clean coverage test coverageReport

# view report in browser (mac|linux)
open ./target/scala-2.12/scoverage-report/index.html
xdg-open ./target/scala-2.12/scoverage-report/index.html
```

### Docker
```
# generate Dockerfile in target/docker/
sbt docker:stage

# build image
sbt docker:publishLocal

# start temporary container
docker run \
  --rm \
  -e HTTP_PORT="8080" \
  -p 80:8080 \
  --name scala-akka-boilerplate \
  scala-akka-boilerplate:latest

# start container
docker-compose up -d

# request status (with HTTPie)
http :80/status

# access container
docker exec -it scala-akka-boilerplate bash

# access container as root
docker exec -it --user root scala-akka-boilerplate bash
```

### CI/CD and management tools

Requirements for deployment
* EC2 instance available e.g. `ec2-000-000-000-000.AWS_REGION.compute.amazonaws.com`
* EC2 Container Registry configured e.g. `AWS_ACCOUNT_ID.dkr.ecr.AWS_REGION.amazonaws.com/scala-akka-boilerplate`
* Credentials (Access Key ID and Secret Access Key) of a unique IAM user with permission `AmazonEC2ContainerRegistryPowerUser` to push on the registry
* [Logentries](https://docs.logentries.com/docs/logback) Java Log Set for Logback

CircleCI build setup
* add the repository to [CircleCI](https://circleci.com/)
* *AWS CodeDeploy*: set the AWS keypair (Access Key ID and Secret Access Key)
* *SSH Permissions*: add a private key (PEM) to deploy to the EC2 machine
* *Chat Notifications*: set a Webhook URL for [Slack](https://slack.com/apps/A0F7VRE7N-circleci) to get a notification on each build

Configure the following CircleCI *Environment Variables*:
* AWS_REGION e.g. `eu-west-1` (without a/b/c)
* AWS_ACCOUNT_ID (registry)
* EC2_USERNAME e.g. `ubuntu`
* EC2_HOST e.g. `ec2-000-000-000-000.AWS_REGION.compute.amazonaws.com`
* HTTP_PORT e.g. `8080`
* SLACK_WEBHOOK_URL
* LOGENTRIES_TOKEN

View coverage report on [CircleCI UI](https://circleci.com/docs/1.0/code-coverage/#seeing-the-results-in-the-circleci-ui) in the *Artifacts* tab of any build

Logs available in `log` (local) or `/vol/log/scala-akka-boilerplate` (aws)