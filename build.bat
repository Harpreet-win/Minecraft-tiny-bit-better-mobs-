@echo off
echo Building AI-Driven Mobs Plugin...
echo.

REM Check if Maven is installed
mvn -v >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Maven is not installed or not in PATH
    echo Please install Maven and try again
    echo.
    pause
    exit /b 1
)

REM Build the plugin
echo Compiling plugin with Maven...
mvn clean package

if %errorlevel% equ 0 (
    echo.
    echo Build successful!
    echo The compiled plugin JAR can be found in the target/ directory
    echo.
    echo To install:
    echo 1. Copy the JAR file to your Minecraft server's plugins folder
    echo 2. Restart your server
    echo.
) else (
    echo.
    echo Build failed!
    echo Please check the error messages above
    echo.
)

pause