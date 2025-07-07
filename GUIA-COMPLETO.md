# 🎬 IPTV Player - Guia Completo de Compilação

## 🚀 PASSO 1: Gitpod (Compilação Online)

### Abrir no Gitpod:
1. **Link direto:** https://gitpod.io/#https://github.com/PaulaSabino25/apktv
2. Aguarde carregar o workspace
3. No terminal, execute:

```bash
# Opção 1: Script automático (recomendado)
chmod +x build-auto.sh
./build-auto.sh

# OU Opção 2: Manual
chmod +x compile.sh
./compile.sh

# OU Opção 3: Gradle direto
./gradlew assembleDebug
```

### Tempo estimado: 3-5 minutos

---

## 📱 PASSO 2: Download do APK

Após a compilação bem-sucedida:

1. **Navegue para:** `app/build/outputs/apk/debug/`
2. **Clique com botão direito** em `app-debug.apk`
3. **Selecione:** "Download"
4. **Salve o arquivo** no seu computador

---

## 🌐 PASSO 3: Testar Online (Appetize.io)

### 3.1 - Abrir emulador:
1. **Acesse:** https://appetize.io/demo
2. **Clique:** "Upload App"
3. **Selecione:** o arquivo `app-debug.apk`
4. **Configure:**
   - Device: Android
   - OS Version: 11.0+
   - Orientation: Portrait

### 3.2 - Testar o app:
1. **Clique:** "Run"
2. **Aguarde** o app carregar
3. **Teste** as funcionalidades!

---

## 🔑 Credenciais de Teste

```
Usuário: demo
Senha: demo
Servidor: http://example.com (ou qualquer URL)
```

---

## 🎯 O que você verá no app:

### ✅ Funcionalidades implementadas:
- **Splash Screen** - Tela de abertura estilizada
- **Login Moderno** - Interface de autenticação
- **Home com Banners** - Carrosséis de destaques
- **Menu Inferior** - Navegação entre Home, TV, Filmes, Séries, Perfil
- **Cards de Conteúdo** - Listas horizontais (Destaques, Lançamentos, Populares)
- **Player IPTV** - Reprodução de streams HLS
- **Visual YouCine** - Design escuro e moderno

### 🎨 Interface:
- Material Design 3
- Tema escuro
- Animações suaves
- Layout responsivo
- Ícones modernos

---

## 🔧 Solução de Problemas

### Se der erro na compilação:
```bash
# Limpar cache
./gradlew clean

# Tentar novamente
./gradlew assembleDebug --stacktrace
```

### Se o Gitpod não abrir:
- Verifique se está logado no GitHub
- Tente em modo anônimo/privado
- Use link alternativo: https://gitpod.io/workspaces

### Alternativas ao Appetize.io:
- **ApkOnline:** https://www.apkonline.net/
- **Android Studio Online:** https://www.android-studio-online.com/
- **Replit:** https://replit.com/ (projeto Android)

---

## 📊 Especificações Técnicas

- **Linguagem:** Kotlin
- **SDK mínimo:** Android 5.0 (API 21)
- **SDK alvo:** Android 14 (API 34)
- **Player:** ExoPlayer 2.19.1
- **Arquitetura:** MVVM + ViewBinding
- **Dependências:** Material Design, Glide, Retrofit

---

## 🚀 Próximas melhorias possíveis:

- [ ] Parser de listas M3U real
- [ ] Sistema de favoritos
- [ ] Histórico de reprodução
- [ ] EPG (guia de programação)
- [ ] Múltiplos servidores
- [ ] Busca global
- [ ] Downloads offline
- [ ] Proteção/camuflagem

---

**🎉 Projeto 100% funcional e pronto para teste!**
