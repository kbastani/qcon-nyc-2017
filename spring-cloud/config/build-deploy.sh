#!/usr/bin/env bash

rm -rf ./target
mkdir target

spring jar --classpath application.yml target/configserver.jar configserver.groovy

cf push

SERVICE_URI=$(cf curl /v2/apps/$(cf app config-server --guid)/env | jq -r '.application_env_json.VCAP_APPLICATION.uris[0]')

cf cups config-server -p "{\"uri\":\"http://$SERVICE_URI\"}"