#!/bin/bash
set -e

BUILD=$(git rev-parse --short HEAD)

YELLOW='\033[1;33m'
NC='\033[0m' # No Color

docker build -t asamerh4/spark-eo-testbed:$BUILD .

echo -e ${YELLOW}"**build finished -> asamerh4/spark-eo-testbed:$BUILD"${NC}

docker run --rm -v $(pwd):/out asamerh4/spark-eo-testbed:$BUILD ./buildJar.sh
