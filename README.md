# 📺 IPTV Player - Aplicativo Android Profissional

## 🎯 Visão Geral

Aplicativo Android completo para streaming IPTV com interface moderna inspirada no YouCine. Inclui todas as funcionalidades avançadas necessárias para uma experiência profissional de IPTV.

## ✨ Funcionalidades Implementadas

### 🔥 Funcionalidades Básicas
- ✅ **Interface YouCine** - Design moderno e intuitivo
- ✅ **Player ExoPlayer** - Reprodução de alta qualidade
- ✅ **Navigation Drawer** - Menu lateral deslizante
- ✅ **Bottom Navigation** - Navegação inferior
- ✅ **Carrosséis Animados** - Banners em destaque
- ✅ **Cards de Conteúdo** - Destaques, Lançamentos, Populares
- ✅ **Material Design 3** - Interface consistente
- ✅ **Splash Screen** - Tela de carregamento animada

### 🚀 Funcionalidades Avançadas (NOVAS!)
- ✅ **Integração API IPTV** - Suporte completo M3U/M3U8
- ✅ **Parser M3U Avançado** - Análise completa de playlists
- ✅ **Sistema de Favoritos** - Canais favoritos com banco de dados
- ✅ **Histórico de Reprodução** - Última posição e duração
- ✅ **Busca Global** - Pesquisa instantânea em todos os canais
- ✅ **EPG (Guia de Programação)** - Programação completa XML
- ✅ **Múltiplos Servidores** - Gerenciamento de vários provedores
- ✅ **Proteção/Camuflagem** - Segurança avançada do app

### 🛡️ Recursos de Segurança
- 🔒 **Proteção por PIN** - Bloqueio do aplicativo
- 🥷 **Modo Stealth** - Camuflagem da interface
- 💥 **Fake Crash** - Proteção contra detecção
- 🚫 **Filtro de Conteúdo** - Bloqueio de conteúdo adulto
- 🔍 **Verificação de Segurança** - Detecção de root/debug
- 🆘 **Limpeza de Emergência** - Remoção completa de dados

### 📊 Gerenciamento de Dados
- 💾 **Banco de Dados Room** - Armazenamento local eficiente
- 🔄 **Sincronização Automática** - Atualização de canais
- 📱 **Cache Inteligente** - Otimização de performance
- 🧹 **Limpeza Automática** - Remoção de dados antigos

## Como testar o app

### Pré-requisitos
1. **Android Studio** instalado
2. **Java 8+** configurado
3. **Emulador Android** ou dispositivo físico

### Passos para compilar e testar

#### Opção 1: Android Studio (Recomendado)
1. Abra o Android Studio
2. Clique em "Open" e selecione a pasta `apktv`
3. Aguarde o Gradle sync terminar
4. Clique no botão "Run" (▶️) ou pressione Shift+F10
5. Escolha um emulador ou dispositivo conectado

#### Opção 2: Terminal (se Java estiver configurado)
```bash
# Windows
.\gradlew.bat assembleDebug

# Linux/Mac
./gradlew assembleDebug
```

### Login de teste
- **Usuário:** demo
- **Senha:** demo
- **Servidor:** qualquer URL (ex: http://exemplo.com)

### Estrutura do projeto

```
apktv/
├── app/
│   ├── src/main/
│   │   ├── java/com/iptvplayer/streaming/
│   │   │   ├── ui/splash/      # Tela de splash
│   │   │   ├── ui/login/       # Tela de login
│   │   │   ├── ui/main/        # Tela principal
│   │   │   ├── ui/home/        # Fragment Home (banners, cards)
│   │   │   ├── ui/channels/    # Fragment TV/Canais
│   │   │   ├── ui/movies/      # Fragment Filmes
│   │   │   ├── ui/series/      # Fragment Séries
│   │   │   ├── ui/profile/     # Fragment Perfil
│   │   │   └── ui/player/      # Player de vídeo
│   │   └── res/
│   │       ├── layout/         # Layouts XML
│   │       ├── drawable/       # Ícones e imagens
│   │       └── values/         # Colors, strings, themes
│   └── build.gradle
├── build.gradle
└── README.md
```

### Próximos passos (melhorias)

- [ ] Integração com API IPTV real
- [ ] Parser de listas M3U
- [ ] Sistema de favoritos
- [ ] Histórico de reprodução
- [ ] Busca global
- [ ] EPG (guia de programação)
- [ ] Múltiplos servidores
- [ ] Proteção/camuflagem do app

### Tecnologias utilizadas

- **Kotlin** - Linguagem principal
- **ExoPlayer** - Player de vídeo
- **Material Design** - Interface moderna
- **Glide** - Carregamento de imagens
- **ViewPager2** - Carrosséis e navegação
- **RecyclerView** - Listas de conteúdo

---

**Nota:** Este é um projeto educacional para demonstrar técnicas de desenvolvimento Android e streaming.
