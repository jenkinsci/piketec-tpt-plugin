SETLOCAL

: install maven nach c:\tools\Maven
set JAVA_HOME=C:\Tools\Java\jdk1.8.0_144
set M2_HOME=C:\tools\maven
set M2=%M2_HOME%\bin
call %M2%\mvn clean package -DskipTests %*

echo "Build is done."

: dann hochladen in jenkins

ENDLOCAL