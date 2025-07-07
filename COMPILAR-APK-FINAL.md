# 🎯 COMPILAR APK - GUIA DEFINITIVO

## ✅ MÉTODOS ONLINE (RECOMENDADO)

### 🚀 Método 1: GitHub Actions (MAIS FÁCIL)

**Passo a passo:**

1. **Acesse**: https://github.com/PaulaSabino25/apktv

2. **Dispare o build**:
   - Clique na aba **Actions** (próximo a Code)
   - Clique em **Android CI** na lista de workflows
   - Clique no botão verde **Run workflow**
   - Selecione branch **main**
   - Clique em **Run workflow**

3. **Aguarde 3-5 minutos** ⏳

4. **Baixe o APK**:
   - Clique no build concluído (círculo verde ✅)
   - Na seção **Artifacts**, clique em **IPTV-Android-APK**
   - Baixa um ZIP, extraia para pegar o **app-debug.apk**

---

### 🌐 Método 2: GitHub Codespaces

1. **Abra o Codespaces**:
   - No repositório, clique em **Code** → **Codespaces**
   - **Create codespace on main**

2. **Compile**:
   ```bash
   chmod +x gradlew
   ./gradlew assembleDebug
   ```

3. **Baixe**:
   - Arquivo em: `app/build/outputs/apk/debug/app-debug.apk`

---

### 🔥 Método 3: Gitpod

1. **Acesse**: https://gitpod.io/#https://github.com/PaulaSabino25/apktv

2. **Compile**:
   ```bash
   ./gradlew assembleDebug
   ```

---

## 🧪 TESTAR O APK

### Online (Emulador Web):

1. **Appetize.io** 📱
   - Acesse: https://appetize.io
   - Clique em **Upload**
   - Arraste o APK
   - Clique em **Run**
   - Teste direto no navegador!

2. **BrowserStack**
   - https://app-live.browserstack.com

### Dispositivo Real:

1. Baixe o APK no celular
2. Configurações → Segurança → **Origens desconhecidas** ✅
3. Instale o APK
4. Abra o app **IPTV YouCine**

---

## 📋 ESPECIFICAÇÕES DO APP

**Funcionalidades:**
- ✅ Interface moderna inspirada no YouCine
- ✅ Player ExoPlayer integrado
- ✅ Navigation Drawer + Bottom Navigation
- ✅ Carrosséis de banners animados
- ✅ Cards de filmes/séries (Destaques, Lançamentos, Populares)
- ✅ Splash screen animada
- ✅ Login/cadastro
- ✅ Seções: TV, Filmes, Séries, Perfil
- ✅ Material Design 3
- ✅ Arquitetura MVVM

**Tecnologias:**
- Kotlin + Android Jetpack
- ExoPlayer para vídeo
- Glide para imagens
- ViewPager2 + RecyclerView
- Material Components

---

## 🎯 RESULTADO ESPERADO

**APK Final:**
- 📁 Nome: `app-debug.apk`
- 📏 Tamanho: ~15-20MB
- 🎨 Interface YouCine moderna
- 📱 Compatível Android 5.0+

---

## 🆘 RESOLUÇÃO DE PROBLEMAS

### Erro "JAVA_HOME not set":
- ✅ **Solução**: Use métodos online (GitHub Actions/Codespaces)

### Erro de compilação:
```bash
./gradlew clean
./gradlew assembleDebug
```

### Erro de permissão:
```bash
chmod +x gradlew
```

---

## 📞 SUPORTE

**Repositório**: https://github.com/PaulaSabino25/apktv
**Documentação**: Veja arquivos `.md` no repositório

---

**🎉 Seu app IPTV está pronto para ser compilado e testado!**

**Método mais rápido**: GitHub Actions (3 cliques, 5 minutos) 🚀
