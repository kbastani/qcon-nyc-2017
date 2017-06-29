#!/usr/bin/env bash

rm -rf ./target
mkdir target

spring jar --classpath application.yml target/eureka.jar eureka.groovy

cf push

SERVICE_URI=$(cf curl /v2/apps/$(cf app discovery-service --guid)/env | jq -r '.application_env_json.VCAP_APPLICATION.uris[0]')

cf cups discovery-service -p "{\"uri\":\"http://$SERVICE_URI\"}"