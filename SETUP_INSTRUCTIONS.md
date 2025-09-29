# AI-Driven Mobs Plugin - Setup Instructions

## Prerequisites

1. **Java Development Kit (JDK) 8 or higher** - You already have this installed
2. **Maven** - Build automation tool
3. **Git** - Version control system (optional)

## Option 1: Install Maven (Recommended)

### Windows Installation

1. Download Apache Maven from https://maven.apache.org/download.cgi
2. Extract the archive to a directory (e.g., `C:\apache-maven`)
3. Add Maven to your PATH:
   - Open System Properties → Advanced → Environment Variables
   - Add `C:\apache-maven\bin` to your PATH variable
4. Restart your command prompt/PowerShell
5. Verify installation by running: `mvn -v`

### Building with Maven

Once Maven is installed:

1. Open PowerShell or Command Prompt in the project directory
2. Run: `mvn clean package`
3. The compiled JAR will be in the `target/` directory

## Option 2: Manual Compilation (Advanced)

If you prefer not to install Maven, you can manually compile the plugin:

1. Download the Spigot API JAR:
   - Visit https://getbukkit.org/download/spigot
   - Download the 1.16.5 version
   - Rename it to `spigot-1.16.5.jar` and place it in the `libs/` directory
ww
2. Compile the Java files:
   ```cmd
   javac -source 1.8 -target 1.8 -d target/classes -cp "libs/*;src/main/java" src/main/java/com/example/aidrivenmobs/*.java src/main/java/com/example/aidrivenmobs/data/*.java
   ```

3. Create the JAR:
   ```cmd
   cd target/classes
   jar cf ../aidrivenmobs-plugin.jar *
   cd ../..
   ```

## Option 3: Use Pre-built JAR

Since compiling requires the server API, I recommend:

1. Download a pre-built JAR from the releases page (when available)
2. Or use a build service like GitHub Actions to compile the plugin

## Installing the Plugin

1. Copy the compiled JAR file to your Minecraft server's `plugins/` folder
2. Start or restart your server
3. The plugin will generate configuration files in `plugins/AIDrivenMobs/`

## Troubleshooting

### Common Issues

1. **ClassNotFoundException**: Make sure all dependencies are in the classpath
2. **NoClassDefFoundError**: Check that the correct Java version is being used
3. **Plugin not loading**: Check server logs for specific error messages

### Getting Help

If you encounter issues:
1. Check the server logs for detailed error messages
2. Verify that you're using a compatible server version (1.16.5 or higher)
3. Ensure the JAR file was built correctly

## Development Environment

For development, I recommend using an IDE like:
- IntelliJ IDEA
- Eclipse
- Visual Studio Code with Java extensions

These IDEs can import Maven projects and handle dependencies automatically.