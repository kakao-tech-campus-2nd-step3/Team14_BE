#!/bin/bash

REPO_URL="https://github.com/kakao-tech-campus-2nd-step3/Team14_BE.git"
BRANCH="feat/95/spot-additional-test"
LOCAL_REPO_PATH="/home/ubuntu/final/build/Team14_BE"

# 기존 디렉토리 삭제 후 클론
if [ -d "$LOCAL_REPO_PATH" ]; then
  rm -rf $LOCAL_REPO_PATH
fi

git clone -b $BRANCH --single-branch $REPO_URL $LOCAL_REPO_PATH

LOCAL_REPO_PATH="/home/ubuntu/final/build/Team14_BE"

# 클론된 디렉토리로 이동 후 빌드 수행 (예: Gradle 사용 시)
cd $LOCAL_REPO_PATH
./gradlew bootJar # 테스트 포함 빌드

# 빌드 파일 이름 추출
BUILD_PATH=$(ls $LOCAL_REPO_PATH/build/libs/*.jar)
JAR_NAME=$(basename $BUILD_PATH)
echo "Build completed: $JAR_NAME"
