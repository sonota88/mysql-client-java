#!/bin/bash

print_project_dir() {
  (
    cd "$(dirname "$0")"
    pwd
  )
}

PROJECT_DIR="$(print_project_dir)"

java -jar \
  ${PROJECT_DIR}/target/sample2-0.0.1-SNAPSHOT-jar-with-dependencies.jar \
  "$@"
