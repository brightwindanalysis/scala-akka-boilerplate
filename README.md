# scala-akka-boilerplate


[![CircleCI][circleci-image]][circleci-url]
[circleci-image]: https://circleci.com/gh/brightwindanalysis/scala-akka-boilerplate.svg?style=svg
[circleci-url]: https://circleci.com/gh/brightwindanalysis/scala-akka-boilerplate

### Useful commands
```
# start the project
sbt run

# hot reload
sbt ~re-start

# check style
sbt scalastyle

# run test
sbt test
```

### Docker
```
# generate Dockerfile in target/docker/
sbt docker:stage

# build image
sbt docker:publishLocal

# run temporary containter
docker run \
  --rm \
  --network="host" \
  -e HTTP_PORT="8080" \
  --name scala-akka-boilerplate \
  scala-akka-boilerplate:latest
```

### CI/CD

[Setup](http://docs.aws.amazon.com/AmazonECR/latest/userguide/ECR_GetStarted.html) Amazon ECR

Setup the following CircleCI Environment Variables:
* AWS_REGION
* AWS_ACCOUNT_ID
