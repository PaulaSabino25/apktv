@echo off
echo ========================================
echo IPTV Player - Setup Automatico
echo ========================================
echo.

echo 1. Verificando Java...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Java nao encontrado!
    echo.
    echo Opcoes para instalar Java:
    echo 1^) Baixar Oracle JDK: https://www.oracle.com/java/technologies/downloads/
    echo 2^) Baixar OpenJDK: https://adoptium.net/
    echo 3^) Usar chocolatey: choco install openjdk
    echo.
    echo Apos instalar Java, execute este script novamente.
    pause
    exit /b 1
)

echo Java encontrado!
echo.

echo 2. Compilando projeto Android...
call gradlew.bat assembleDebug
if %errorlevel% neq 0 (
    echo Erro na compilacao!
    pause
    exit /b 1
)

echo.
echo ========================================
echo APK gerado com sucesso!
echo ========================================
echo Localizacao: app\build\outputs\apk\debug\app-debug.apk
echo.
echo Para instalar no dispositivo:
echo 1^) Habilite "Fontes desconhecidas" no Android
echo 2^) Transfira o APK para o dispositivo
echo 3^) Abra o arquivo e instale
echo.
echo Ou use ADB: adb install -r app\build\outputs\apk\debug\app-debug.apk
echo.
pause
