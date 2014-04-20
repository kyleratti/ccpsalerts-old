#!/bin/bash
rm com/ccpsalerts/*.class com/ccpsalerts/calendar/*.class
javac -cp ".:../lib/*" com/ccpsalerts/*.java com/ccpsalerts/calendar/*.java
java -cp ".:../lib/*" com.ccpsalerts.Driver