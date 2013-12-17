SETLOCAL

: install maven nach c:\tools\Maven

set M2_HOME=c:\tools\maven
set M2=%M2_HOME%\bin
call %M2%\mvn package -DskipTests %*

echo "Build is done."

: dann hochladen in jenkins

ENDLOCAL