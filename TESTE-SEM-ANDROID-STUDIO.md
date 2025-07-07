# Como testar o app sem Android Studio

## Opção 1: Instalar Java e compilar

1. **Baixe e instale Java:**
   - Oracle JDK: https://www.oracle.com/java/technologies/downloads/
   - Ou OpenJDK: https://adoptium.net/

2. **Execute o script de build:**
   ```
   build.bat
   ```

3. **Instale o APK gerado:**
   - Arquivo: `app\build\outputs\apk\debug\app-debug.apk`
   - Transfira para o celular e instale

## Opção 2: Android Studio (Recomendado)

1. **Baixe Android Studio:** https://developer.android.com/studio
2. **Abra o projeto:** Selecione a pasta `apktv`
3. **Clique em Run (▶️)**

## Opção 3: Emulador online

Use um emulador Android online:
- **ApkOnline:** https://www.apkonline.net/
- **Appetize.io:** https://appetize.io/
- Faça upload do APK compilado

## Opção 4: Dispositivo físico

Se tiver um celular Android:

1. **Habilite "Fontes desconhecidas":**
   - Configurações → Segurança → Fontes desconhecidas

2. **Transfira o APK:**
   - Via cabo USB, email, ou nuvem
   - Abra o arquivo e instale

## Login de teste
- Usuário: `demo`
- Senha: `demo`
- Servidor: qualquer URL

## Estrutura criada
- ✅ Splash screen
- ✅ Tela de login
- ✅ Menu inferior (Home, TV, Filmes, Séries, Perfil)
- ✅ Banners e carrosséis na Home
- ✅ Player de vídeo
- ✅ Visual estilo YouCine
