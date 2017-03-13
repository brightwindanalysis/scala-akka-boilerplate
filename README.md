# scala-akka-boilerplate


[![CircleCI][circleci-image]][circleci-url]
[circleci-image]: https://circleci.com/gh/brightwindanalysis/scala-akka-boilerplate.svg?style=svg
[circleci-url]: https://circleci.com/gh/brightwindanalysis/scala-akka-boilerplate

Useful commands
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

Docker
```
# generate Dockerfile in target/docker/
sbt docker:stage

# build image
sbt docker:publishLocal

# run temporary containter
docker run \
  --rm \
  --name scala-akka-boilerplate \
  -p 3000:3000 \
  scala-akka-boilerplate:1.0
```
