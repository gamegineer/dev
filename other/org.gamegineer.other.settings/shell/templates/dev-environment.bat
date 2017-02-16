@ECHO OFF

REM =========================================================================
REM
REM dev-environment.bat
REM Copyright 2008-2017 Gamegineer contributors and others.
REM All rights reserved.
REM
REM This is a sample development environment setup script required for a
REM Windows-based Gamegineer development workstation.  You should download
REM this file to your workstation and customize the variable definitions
REM between the BEGIN-CUSTOM-VARIABLES and END-CUSTOM-VARIABLES markers.
REM The customized file should then be archived on your workstation and be
REM part of your development environment startup process.
REM
REM =========================================================================

REM -------------------------------------------------------------------------
REM
REM BEGIN-CUSTOM-VARIABLES
REM

REM
REM Home directories of all build tools.
REM
SET ECLIPSE_HOME=C:\Program Files\eclipse-neon
SET JAVA_HOME=C:\Program Files\Java\jdk1.8.0_121
SET MAVEN_HOME=C:\Program Files\apache-maven-3.3.9

REM
REM Build tool options.
REM
SET ECLIPSE_OPTS=-Xmx1536M

REM
REM END-CUSTOM-VARIABLES
REM
REM -------------------------------------------------------------------------

REM
REM Update path to include all build-related binaries.
REM
SET PATH=%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%ECLIPSE_HOME%;%PATH%

REM
REM Create useful macros.
REM
DOSKEY eclipse-dev=eclipse -vm "%JAVA_HOME%\jre\bin\javaw.exe" -vmargs %ECLIPSE_OPTS%

ECHO Gamegineer Development Environment
ECHO.
ECHO Type 'eclipse-dev' to start the Eclipse IDE.
