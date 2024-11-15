#!/bin/bash

DEPLOY_PATH="/home/ubuntu/final/deploy/Team14_BE/"
BUILD_PATH=$(ls /home/ubuntu/final/build/Team14_BE/build/libs/*.jar)
JAR_NAME=$(basename $BUILD_PATH)
CURRENT_PID=$(pgrep -f $JAR_NAME)

# 현재 실행 중인 애플리케이션 종료
if [ -n "$CURRENT_PID" ]; then
  kill -15 $CURRENT_PID
  sleep 5
fi

# 배포 디렉토리에 새 빌드 파일 복사
cp $BUILD_PATH $DEPLOY_PATH
cd $DEPLOY_PATH

# 새 빌드 파일 실행
DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
nohup java -jar $DEPLOY_JAR > nohup.out 2>&1 &
echo "Deployment completed: $DEPLOY_JAR"
