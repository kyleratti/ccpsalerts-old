@echo off
cls
goto compile_run

:compile_run
javac -cp ".;..\bin\*" com\ccpsalerts\*.java
java -cp ".;..\bin\*" com.ccpsalerts.Driver
goto delete_files

:delete_files
del com\ccpsalerts\*.class