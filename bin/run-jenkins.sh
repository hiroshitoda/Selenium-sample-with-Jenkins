#!/bin/sh

export JENKINS_HOME=`dirname ${0}`/jenkins
java -jar ${JENKINS_HOME}/jenkins.war --webroot=${JENKINS_HOME}/jenkins