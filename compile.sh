#!/bin/bash

echo "🎬 IPTV Player - Compilador Automático"
echo "======================================"
echo ""

echo "📱 Compilando APK..."
./gradlew assembleDebug

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ APK compilado com sucesso!"
    echo "📍 Localização: app/build/outputs/apk/debug/app-debug.apk"
    echo ""
    echo "🌐 Próximos passos:"
    echo "1. Baixe o arquivo app-debug.apk"
    echo "2. Acesse https://appetize.io/"
    echo "3. Faça upload do APK"
    echo "4. Teste no emulador!"
    echo ""
    echo "🔑 Login de teste:"
    echo "   Usuário: demo"
    echo "   Senha: demo"
    echo "   Servidor: qualquer URL"
else
    echo ""
    echo "❌ Erro na compilação!"
    echo "Verifique os logs acima para detalhes."
fi
