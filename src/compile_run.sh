#!/bin/bash
javac -cp ".:../lib/*" com/ccpsalerts/*.java
java -cp ".:../lib/*" com.ccpsalerts.Driver