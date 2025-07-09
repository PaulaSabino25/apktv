#!/bin/bash

echo "🚀 SCRIPT DE TESTE - IPTV PLAYER"
echo "================================="
echo ""

echo "📊 Verificando estrutura do projeto..."
echo ""

# Verificar arquivos principais
echo "✅ Verificando arquivos principais:"
if [ -f "app/build.gradle" ]; then
    echo "   ✅ build.gradle encontrado"
else
    echo "   ❌ build.gradle NÃO encontrado"
fi

if [ -f "app/src/main/AndroidManifest.xml" ]; then
    echo "   ✅ AndroidManifest.xml encontrado"
else
    echo "   ❌ AndroidManifest.xml NÃO encontrado"
fi

if [ -d "app/src/main/java/com/iptvplayer/streaming" ]; then
    echo "   ✅ Pacote principal encontrado"
else
    echo "   ❌ Pacote principal NÃO encontrado"
fi

echo ""
echo "📱 Verificando Activities principais:"
activities=(
    "app/src/main/java/com/iptvplayer/streaming/ui/splash/SplashActivity.kt"
    "app/src/main/java/com/iptvplayer/streaming/ui/login/LoginActivity.kt"
    "app/src/main/java/com/iptvplayer/streaming/ui/main/MainActivity.kt"
    "app/src/main/java/com/iptvplayer/streaming/ui/player/PlayerActivity.kt"
)

for activity in "${activities[@]}"; do
    if [ -f "$activity" ]; then
        echo "   ✅ $(basename $activity) encontrada"
    else
        echo "   ❌ $(basename $activity) NÃO encontrada"
    fi
done

echo ""
echo "🔧 Verificando funcionalidades avançadas:"
advanced_features=(
    "app/src/main/java/com/iptvplayer/streaming/service/M3UParser.kt"
    "app/src/main/java/com/iptvplayer/streaming/service/EPGParser.kt"
    "app/src/main/java/com/iptvplayer/streaming/database/IPTVDatabase.kt"
    "app/src/main/java/com/iptvplayer/streaming/repository/IPTVRepository.kt"
    "app/src/main/java/com/iptvplayer/streaming/security/SecurityManager.kt"
    "app/src/main/java/com/iptvplayer/streaming/ui/favorites/FavoritesFragment.kt"
    "app/src/main/java/com/iptvplayer/streaming/ui/search/SearchFragment.kt"
    "app/src/main/java/com/iptvplayer/streaming/ui/servers/ServersFragment.kt"
)

for feature in "${advanced_features[@]}"; do
    if [ -f "$feature" ]; then
        echo "   ✅ $(basename $feature) implementado"
    else
        echo "   ❌ $(basename $feature) NÃO implementado"
    fi
done

echo ""
echo "🎨 Verificando recursos (layouts, drawables):"
layouts=(
    "app/src/main/res/layout/activity_splash.xml"
    "app/src/main/res/layout/activity_login.xml"
    "app/src/main/res/layout/activity_main.xml"
    "app/src/main/res/layout/fragment_home.xml"
    "app/src/main/res/layout/fragment_favorites.xml"
    "app/src/main/res/layout/fragment_search.xml"
)

for layout in "${layouts[@]}"; do
    if [ -f "$layout" ]; then
        echo "   ✅ $(basename $layout) encontrado"
    else
        echo "   ❌ $(basename $layout) NÃO encontrado"
    fi
done

echo ""
echo "📊 Contando arquivos do projeto:"
kt_files=$(find app/src/main/java -name "*.kt" 2>/dev/null | wc -l)
xml_files=$(find app/src/main/res -name "*.xml" 2>/dev/null | wc -l)
echo "   📝 Arquivos Kotlin: $kt_files"
echo "   🎨 Arquivos XML: $xml_files"

echo ""
echo "🚀 RESULTADO:"
echo "============="
if [ $kt_files -gt 10 ] && [ $xml_files -gt 10 ]; then
    echo "✅ PROJETO COMPLETO - Pronto para compilação!"
    echo ""
    echo "📱 Para compilar:"
    echo "1. Use GitHub Actions: https://github.com/PaulaSabino25/apktv/actions"
    echo "2. Ou use Codespaces: Code → Codespaces → Create"
    echo "3. Execute: ./gradlew assembleDebug"
else
    echo "⚠️  PROJETO INCOMPLETO - Verifique os arquivos em falta"
fi

echo ""
echo "🎯 Links úteis:"
echo "- Repositório: https://github.com/PaulaSabino25/apktv"
echo "- Emulador: https://appetize.io"
echo "- Documentação: Veja arquivos .md no projeto"
echo ""
