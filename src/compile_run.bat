@echo off
cls
goto compile_run

:compile_run
javac -cp ".;..\lib\*" com\ccpsalerts\*.java
java -cp ".;..\lib\*" com.ccpsalerts.Driver -task lastdaycountdown
goto delete_files

:delete_files
del com\ccpsalerts\*.class