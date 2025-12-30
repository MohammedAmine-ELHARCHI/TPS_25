@echo off
echo ========================================
echo Demarrage du Service SOAP
echo ========================================
echo.

cd /d "%~dp0"

if exist mvnw.cmd (
    echo Utilisation du Maven Wrapper...
    call mvnw.cmd clean spring-boot:run
) else (
    echo Utilisation de Maven installe...
    call mvn clean spring-boot:run
)

pause
