@echo off

@echo start build sail-activity
cd ../sail-activity
call mvn clean install -Dskiptest=true

@echo start build sail-activity-api
cd ../sail-activity-api
call mvn clean  package spring-boot:repackage -Dmaven.test.skip=true -Drp.build.warname=sail-activity-api

pause