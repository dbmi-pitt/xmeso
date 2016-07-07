REM
REM Execute Xmeso
REM

echo %XMESO_HOME%

set CLASSPATH=xmeso-0.0.1-SNAPSHOT.jar
set CLASSPATH=%CLASSPATH%;%XMESO_HOME%\target\classes

java -jar xmeso-0.0.1-SNAPSHOT.jar

