#!/bin/bash

mvn_run(){
  mvn exec:java \
    --quiet \
    "-Dexec.mainClass=sample.Main" \
    "-Dexec.args=$*"
}

mvn_package(){
  mvn clean
  mvn package -Dmaven.test.skip=true

  echo "----"
  ls -l target/*.jar
  echo "----"
  ls -lh target/*.jar
}

cmd="$1"; shift

case $cmd in
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
