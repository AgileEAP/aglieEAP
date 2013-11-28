@echo off
echo [Pre-Requirement] Makesure install JDK 6.0+ and set the JAVA_HOME.
echo [Pre-Requirement] Makesure install Maven 3.0.3+ and set the PATH.

set MVN=mvn
set MAVEN_OPTS=%MAVEN_OPTS% -XX:MaxPermSize=128m

echo [INFO] Start ireport web project.
start "aglieEAP-portal" %MVN% clean jetty:run 
if errorlevel 1 goto error

echo [INFO] Please wait a moment. When you see "[INFO] Started Jetty Server" in 1 popup consoles, you can access below demo sites:
echo [INFO] http://localhost:8080/aglieEAP-portal

pause