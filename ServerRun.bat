@echo off
echo +++++++++++++++++
echo ++�������������������++
echo +++++++++++++++++
set PATH=%CD%;%PATH%;
call java -classpath %~dp0\bin;%~dp0\jar\* gateway.ServerStart %~dp0 config.xml
pause
