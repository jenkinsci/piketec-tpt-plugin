set JENKINS_HOME=C:\tools\Jenkins
set MAVEN_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=8000,suspend=n
mvn -Dmaven.javadoc.skip=true hpi:run