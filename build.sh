#!/bin/bash

MVN_CMD=mvn

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
