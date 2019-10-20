@echo off
@echo start pull sail-activity
cd ../sail-activity
call git pull

@echo start pull sail-activity-api
cd ../sail-activity-api
call git pull
pause