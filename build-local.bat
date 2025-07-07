@echo off
echo ==========================================
echo    COMPILADOR AUTOMATICO APK IPTV
echo ==========================================
echo.

echo [1/4] Verificando Java...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java nao encontrado!
    echo.
    echo Baixe e instale o JDK 11 ou superior:
    echo https://adoptium.net/temurin/releases/
    echo.
    echo Ou use a opcao online:
    echo - GitHub Actions: https://github.com/PaulaSabino25/apktv/actions
    echo - Codespaces: Clique no botao Code > Codespaces
    echo.
    pause
    exit /b 1
)
echo ✅ Java encontrado!

echo.
echo [2/4] Verificando Android SDK...
if not defined ANDROID_HOME (
    echo ❌ ANDROID_HOME nao configurado!
    echo.
    echo Instale o Android Studio ou configure manualmente:
    echo https://developer.android.com/studio
    echo.
    echo Ou use a opcao online no GitHub Actions!
    echo.
    pause
    exit /b 1
)
echo ✅ Android SDK encontrado!

echo.
echo [3/4] Limpando build anterior...
call gradlew clean
if %errorlevel% neq 0 (
    echo ❌ Erro na limpeza!
    pause
    exit /b 1
)

echo.
echo [4/4] Compilando APK...
call gradlew assembleDebug
if %errorlevel% neq 0 (
    echo ❌ Erro na compilacao!
    echo.
    echo Tente usar o GitHub Actions para compilacao online:
    echo https://github.com/PaulaSabino25/apktv/actions
    echo.
    pause
    exit /b 1
)

echo.
echo ==========================================
echo    ✅ COMPILACAO CONCLUIDA COM SUCESSO!
echo ==========================================
echo.
echo APK gerado em:
echo app\build\outputs\apk\debug\app-debug.apk
echo.

if exist "app\build\outputs\apk\debug\app-debug.apk" (
    echo 📱 Tamanho do APK:
    for %%I in ("app\build\outputs\apk\debug\app-debug.apk") do echo %%~zI bytes
    echo.
    echo 🎯 Para testar:
    echo 1. Transfira o APK para seu dispositivo Android
    echo 2. Ative "Origens desconhecidas" nas configuracoes
    echo 3. Instale o APK
    echo.
    echo 🌐 Ou teste online em:
    echo https://appetize.io (upload do APK)
    echo.
    
    set /p choice="Abrir pasta do APK? (s/n): "
    if /i "%choice%"=="s" (
        explorer "app\build\outputs\apk\debug\"
    )
) else (
    echo ❌ APK nao encontrado!
)

echo.
pause
