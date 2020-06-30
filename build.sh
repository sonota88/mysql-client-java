#!/bin/bash

# MVN_CMD=mvn
MVN_CMD=./mvnw

setup(){
  echo "Install Maven Wrapper"
  mvn -N io.takari:maven:0.7.7:wrapper -Dmaven=3.6.3
}

mvn_run(){
  $MVN_CMD exec:java \
    --quiet \
    "-Dexec.mainClass=Main" \
    "-Dexec.args=$*"
}

mvn_package(){
  $MVN_CMD clean
  $MVN_CMD package -Dmaven.test.skip=true

  echo "----"
  ls -l target/*.jar
  echo "----"
  ls -lh target/*.jar
}

cmd="$1"; shift

case $cmd in
  setup)
    setup
    ;;
  run)
    mvn_run "$@"
    ;;
  package)
    mvn_package
    ;;
  *)
    echo "invalid command"
    ;;
esac
