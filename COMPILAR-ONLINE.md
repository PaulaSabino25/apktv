# 🚀 Como Compilar o APK Online

## Método 1: GitHub Actions (RECOMENDADO) ⭐

### Passo a Passo:

1. **Acesse o repositório**: https://github.com/PaulaSabino25/apktv

2. **Dispare o build automático**:
   - Vá na aba **Actions**
   - Clique em **Android CI**
   - Clique em **Run workflow** (botão verde)
   - Selecione a branch **main**
   - Clique em **Run workflow**

3. **Aguarde o build** (3-5 minutos)

4. **Baixe o APK**:
   - Quando terminar, clique no workflow executado
   - Na seção **Artifacts**, baixe o arquivo **app-debug**
   - Extraia o ZIP e você terá o **app-debug.apk**

---

## Método 2: GitHub Codespaces 🌐

### Passo a Passo:

1. **Abra no Codespaces**:
   - No repositório GitHub, clique no botão verde **Code**
   - Selecione **Codespaces**
   - Clique em **Create codespace on main**

2. **Aguarde a inicialização** (2-3 minutos)

3. **Compile o projeto**:
   ```bash
   chmod +x gradlew
   ./gradlew assembleDebug
   ```

4. **Baixe o APK**:
   - O arquivo estará em: `app/build/outputs/apk/debug/app-debug.apk`
   - Clique com botão direito → **Download**

---

## Método 3: Gitpod 🚀

### Passo a Passo:

1. **Abra no Gitpod**:
   - Vá para: https://gitpod.io/#https://github.com/PaulaSabino25/apktv

2. **Aguarde a inicialização**

3. **Compile o projeto**:
   ```bash
   ./gradlew assembleDebug
   ```

4. **Baixe o APK**:
   - Navegue até `app/build/outputs/apk/debug/`
   - Baixe o arquivo `app-debug.apk`

---

## Método 4: Replit 📝

### Passo a Passo:

1. **Importe no Replit**:
   - Acesse https://replit.com
   - Clique em **Create** → **Import from GitHub**
   - Cole: `https://github.com/PaulaSabino25/apktv`

2. **Configure o ambiente**:
   ```bash
   # Instalar dependências Android
   wget https://dl.google.com/android/repository/commandlinetools-linux-9477386_latest.zip
   unzip commandlinetools-linux-9477386_latest.zip
   mkdir -p Android/sdk/cmdline-tools/latest
   mv cmdline-tools/* Android/sdk/cmdline-tools/latest/
   export ANDROID_HOME=$PWD/Android/sdk
   export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin
   ```

3. **Compile**:
   ```bash
   chmod +x gradlew
   ./gradlew assembleDebug
   ```

---

## 🎯 Testar o APK

### Online (Recomendado):
1. **Appetize.io**: https://appetize.io
   - Upload do APK
   - Teste direto no navegador
   - Gratuito (limitado)

2. **BrowserStack**: https://app-live.browserstack.com
   - Upload do APK
   - Teste em dispositivos reais

### Dispositivo Físico:
1. Baixe o APK
2. Transfira para o dispositivo
3. Ative "Origens desconhecidas"
4. Instale o APK

---

## 🔧 Troubleshooting

### Se der erro de Java/Android SDK:
- Use **GitHub Actions** (método mais confiável)
- O ambiente já está configurado automaticamente

### Se der erro de permissões:
```bash
chmod +x gradlew
```

### Se der erro de dependências:
```bash
./gradlew clean
./gradlew assembleDebug
```

---

## 📱 Resultado Esperado

Após compilar, você terá:
- **app-debug.apk** (~15-20MB)
- Interface moderna inspirada no YouCine
- Player ExoPlayer funcional
- Navigation Drawer e Bottom Navigation
- Carrosséis e cards de conteúdo
- Material Design 3

---

**🎉 Pronto! Seu app IPTV está compilado e pronto para usar!**
