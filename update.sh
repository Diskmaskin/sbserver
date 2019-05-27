#!/bin/bash

./fetch.sh $1
PATHSEP=":"
if [[ $OS == "Windows_NT" ]] || [[ $OSTYPE == "cygwin" ]]
then
    PATHSEP=";"
fi

CP=target/classes/${PATHSEP}target/sbserver-1.0-SNAPSHOT/WEB-INF/lib/*

java -cp $CP se.gu.ait.sbserver.main.Update