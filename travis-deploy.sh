#!/bin/bash
set -e

SOURCE_BRANCH="master"
TARGET_BRANCH="master"

git clone https://github.com/JupiterDevelopmentTeam/Jupiter.git
mkdir -p artifact
cp -R ./target/nukkit-1.0-SNAPSHOT.jar artifact
git checkout $TARGET_BRANCH || git checkout --orphan $TARGET_BRANCH

cd artifact
git config --global user.name "Travis CI"
git config --global user.email "noreply@travis-ci.org"

if [ -z 'git diff --exit-code' ]; then
    echo "No changes to the spec on this push; exiting."
    exit 0
fi

git add nukkit-1.0-SNAPSHOT.jar
git commit -m "# $TRAVIS_BUILD_NUMBER での成果物の更新。 [skip ci]"
git push --quiet "https://${GH_TOKEN}@github.com/JupiterDevelopmentTeam/Jupiter.git" $SOURCE_BRANCH:$TARGET_BRANCH
