#!/bin/bash

echo "🎬 IPTV Player - Compilação Automática no Gitpod"
echo "================================================"
echo ""

# Configurar permissões
echo "📋 Configurando permissões..."
chmod +x gradlew
chmod +x compile.sh

# Limpar build anterior (se existir)
echo "🧹 Limpando builds anteriores..."
./gradlew clean

# Compilar APK
echo "🔨 Compilando APK de debug..."
./gradlew assembleDebug --no-daemon --stacktrace

# Verificar se o build foi bem-sucedido
if [ -f "app/build/outputs/apk/debug/app-debug.apk" ]; then
    echo ""
    echo "✅ SUCESSO! APK compilado com êxito!"
    echo "📍 Localização: app/build/outputs/apk/debug/app-debug.apk"
    echo "📊 Tamanho do arquivo:"
    ls -lh app/build/outputs/apk/debug/app-debug.apk
    echo ""
    echo "🌐 Próximos passos:"
    echo "1. Baixe o arquivo app-debug.apk"
    echo "2. Acesse https://appetize.io/"
    echo "3. Faça upload do APK"
    echo "4. Teste o app online!"
    echo ""
    echo "🔑 Credenciais de teste:"
    echo "   Usuário: demo"
    echo "   Senha: demo"
    echo "   Servidor: http://example.com"
    echo ""
    echo "🎯 Funcionalidades do app:"
    echo "   ✅ Splash screen estilizada"
    echo "   ✅ Login moderno"
    echo "   ✅ Home com banners/carrosséis"
    echo "   ✅ Menu inferior (Home, TV, Filmes, Séries, Perfil)"
    echo "   ✅ Player de vídeo IPTV"
    echo "   ✅ Visual escuro estilo YouCine"
else
    echo ""
    echo "❌ ERRO na compilação!"
    echo "Verifique os logs acima para mais detalhes."
    echo ""
    echo "💡 Soluções comuns:"
    echo "1. Execute: ./gradlew clean"
    echo "2. Tente novamente: ./gradlew assembleDebug"
    echo "3. Verifique se há erros de sintaxe no código"
fi

echo ""
echo "================================================"
