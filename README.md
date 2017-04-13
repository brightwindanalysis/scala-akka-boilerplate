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

# run temporary container
docker run \
  --rm \
  -e HTTP_PORT="8080" \
  -p 80:8080 \
  --name scala-akka-boilerplate \
  scala-akka-boilerplate:latest

# request status (with HTTPie)
http :80/status

# access container
docker exec -it scala-akka-boilerplate bash

# access container as root
docker exec -it --user root scala-akka-boilerplate bash
```

### CI/CD and management tools

Config CircleCI [Slack](https://slack.com/apps/A0F7VRE7N-circleci)'s Webhook

[Setup](http://docs.aws.amazon.com/AmazonECR/latest/userguide/ECR_GetStarted.html) Amazon ECR

Create a unique IAM user and configure AWS permissions on CircleCI:
* `AmazonEC2ContainerRegistryPowerUser` to push on the registry

Config SSH permissions on CircleCI to deploy on EC2

Config the following CircleCI Environment Variables:
* AWS_REGION
* AWS_ACCOUNT_ID (registry)
* EC2_USERNAME
* EC2_HOST
* SLACK_WEBHOOK_URL
* LOGENTRIES_TOKEN
