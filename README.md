# IPTV Player - Estilo YouCine

Este é um aplicativo Android de streaming IPTV com interface moderna inspirada no YouCine.

## Funcionalidades

- ✅ Tela de login personalizada
- ✅ Menu inferior com navegação (Home, TV, Filmes, Séries, Perfil)
- ✅ Banner/carrossel de destaques na Home
- ✅ Cards horizontais de conteúdo (Destaques, Lançamentos, Populares)
- ✅ Player de vídeo com ExoPlayer
- ✅ Interface escura e moderna
- ✅ Suporte a streams HLS (.m3u8)

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
