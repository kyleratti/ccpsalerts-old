#!/bin/bash
javac -cp ".:../bin/*" com/ccpsalerts/*.java
java -cp ".:../bin/*" com.ccpsalerts.Driver