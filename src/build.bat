@echo off
javac -cp ".;..\bin\*" com\ccpsalerts\*.java
jar cvfe ccpsalerts.jar com.ccpsalerts.Driver com\ccpsalerts\*.class
@pause