@echo off
echo ========================================
echo 🎬 IPTV Player - Upload para GitHub
echo ========================================
echo.

echo 📝 Inicializando repositorio Git...
git init
if %errorlevel% neq 0 (
    echo ❌ Erro: Git nao encontrado!
    echo Instale o Git: https://git-scm.com/download/win
    pause
    exit /b 1
)

echo ➕ Adicionando arquivos...
git add .

echo 💾 Fazendo commit...
git commit -m "🎬 IPTV Player - App Android estilo YouCine completo"

echo 🌐 Configurando repositorio remoto...
echo.
echo ⚠️  IMPORTANTE: Crie um repositorio no GitHub primeiro!
echo 1. Acesse: https://github.com/new
echo 2. Nome: iptv-player-youcine
echo 3. Publico ou privado
echo 4. NAO inicialize com README
echo 5. Clique "Create repository"
echo.
set /p repo_url="📋 Cole a URL do repositorio (ex: https://github.com/usuario/iptv-player-youcine.git): "

git remote add origin %repo_url%

echo 🚀 Enviando para GitHub...
git branch -M main
git push -u origin main

if %errorlevel% eq 0 (
    echo.
    echo ✅ Upload concluido com sucesso!
    echo.
    echo 🔗 Repositorio: %repo_url%
    echo.
    echo 📋 Proximo passo - Gitpod:
    echo 1. Acesse: https://gitpod.io/
    echo 2. Cole: %repo_url%
    echo 3. Clique "Continue"
    echo 4. Execute: ./compile.sh
    echo.
    echo 🎯 Depois teste em: https://appetize.io/
) else (
    echo ❌ Erro no upload!
    echo Verifique suas credenciais do GitHub
)

pause
