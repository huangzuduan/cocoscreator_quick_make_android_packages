@echo off
echo +++++++++++++++++
echo ++启动多渠道打包服务器++
echo +++++++++++++++++
set PATH=%CD%;%PATH%;
call java -classpath %~dp0\bin;%~dp0\jar\* gateway.ServerStart %~dp0 config.xml
pause
