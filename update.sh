#!/bin/bash

#./fetch.sh
PATHSEP=":"
if [[ $OS == "Windows_NT" ]] || [[ $OSTYPE == "cygwin" ]]
then
    PATHSEP=";"
fi

java -cp target/classes/${PATHSEP}target/sbserver-1.0-SNAPSHOT/WEB-INF/lib/* se.gu.ait.sbserver.main.Update