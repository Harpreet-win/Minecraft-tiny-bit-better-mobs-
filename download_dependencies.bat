@echo off
echo Downloading Spigot API...
powershell -Command "Invoke-WebRequest https://hub.spigotmc.org/nexus/content/repositories/snapshots/org/spigotmc/spigot-api/1.16.5-R0.1-SNAPSHOT/spigot-api-1.16.5-R0.1-20210612.205020-1.jar -OutFile libs/spigot-api.jar"
echo Dependencies downloaded successfully!
pause