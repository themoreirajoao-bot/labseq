@echo off
title LabSeq - Full Setup + Run

echo ================================
echo CHECKING WINGET
echo ================================
winget --version >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo ERROR: winget is not installed. Update Windows or install winget manually.
    pause
    exit /b
)

echo ================================
echo INSTALLING JAVA 21 (Temurin)
echo ================================
winget install --id EclipseAdoptium.Temurin.21.JDK -e --silent

echo ================================
echo INSTALLING MAVEN
echo ================================
winget install --id Apache.Maven -e --silent

echo ================================
echo INSTALLING NODE.JS 20 LTS
echo ================================
winget install --id OpenJS.NodeJS.LTS -e --silent

echo ================================
echo INSTALLING ANGULAR CLI
echo ================================
call npm install -g @angular/cli

echo ================================
echo ALL DEPENDENCIES INSTALLED
echo ================================
timeout /t 2 >nul

echo ================================
echo STARTING BACKEND (QUARKUS)
echo ================================
start cmd /k "cd /d C:\Quarkus\code-with-quarkus && quarkus dev"

echo ================================
echo WAITING 5 SECONDS FOR BACKEND
echo ================================
timeout /t 5 >nul

echo ================================
echo STARTING FRONTEND (ANGULAR)
echo ================================
start cmd /k "cd /d C:\Quarkus\labseq-ui && npm install && ng serve"

echo ================================
echo WAITING 5 SECONDS FOR FRONTEND
echo ================================
timeout /t 5 >nul

echo ================================
echo OPENING BROWSER
echo ================================
start http://localhost:4200
start http://localhost:8080/q/swagger-ui

echo ================================
echo SETUP + RUN COMPLETE!
echo ================================
pause
