#!/bin/bash
sbt clean compile assembly
mv target/scala-2.11/testbed-assembly*.jar /out