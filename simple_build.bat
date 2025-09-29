@echo off
echo Building AI-Driven Mobs Plugin...
echo.

REM Create directories if they don't exist
if not exist "target" mkdir target
if not exist "target\classes" mkdir target\classes

REM Compile Java files
echo Compiling Java files...
javac -source 1.8 -target 1.8 -d target\classes -cp "libs/*;src\main\java" src\main\java\com\example\aidrivenmobs\*.java src\main\java\com\example\aidrivenmobs\data\*.java

if %errorlevel% equ 0 (
    echo.
    echo Compilation successful!
    echo.
    
    REM Copy resources
    echo Copying resources...
    xcopy /E /I /Y src\main\resources target\classes >nul 2>&1
    
    REM Create JAR file
    echo Creating JAR file...
    cd target\classes
    jar cf ..\aidrivenmobs-plugin.jar *
    cd ..\..
    
    if exist "target\aidrivenmobs-plugin.jar" (
        echo.
        echo Build successful!
        echo Plugin JAR created: target\aidrivenmobs-plugin.jar
        echo.
        echo To use this plugin:
        echo 1. Copy target\aidrivenmobs-plugin.jar to your Minecraft server's plugins folder
        echo 2. Restart your server
        echo.
    ) else (
        echo.
        echo Error: Failed to create JAR file
        echo.
    )
) else (
    echo.
    echo Compilation failed!
    echo Please check the error messages above
    echo.
)

pause