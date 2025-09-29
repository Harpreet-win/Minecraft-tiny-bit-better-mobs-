@echo off
echo Starting Minecraft Server with AI-Driven Mobs Plugin...
echo.

REM Check if server JAR exists
if not exist "server.jar" (
    echo Error: server.jar not found
    echo Please download a Spigot/Paper server JAR and rename it to server.jar
    echo.
    pause
    exit /b 1
)

REM Check if plugin is built
if not exist "target\aidrivenmobs-1.0-SNAPSHOT.jar" (
    echo Error: Plugin JAR not found
    echo Please build the plugin first using build.bat
    echo.
    pause
    exit /b 1
)

REM Create plugins directory if it doesn't exist
if not exist "plugins" mkdir plugins

REM Copy plugin to plugins directory
copy "target\aidrivenmobs-1.0-SNAPSHOT.jar" "plugins\"

REM Accept EULA if it doesn't exist
if not exist "eula.txt" (
    echo eula=true > eula.txt
    echo Created eula.txt and accepted EULA
    echo.
)

REM Start the server
echo Starting server...
java -Xmx2G -Xms1G -jar server.jar

pause