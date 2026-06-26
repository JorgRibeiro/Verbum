# Verbum

> Dicionário e tradutor contextual integrado ao menu de seleção de texto do Android.

[![Android](https://img.shields.io/badge/Platform-Android%206.0%2B-green?logo=android)](https://developer.android.com/)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-7F52FF?logo=kotlin)](https://kotlinlang.org/)
[![Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?logo=jetpack-compose)](https://developer.android.com/jetpack/compose)
[![Architecture](https://img.shields.io/badge/Architecture-MVVM%20%2B%20Repository-orange)](https://developer.android.com/topic/architecture)

---

## 📖 Descrição

O **Verbum** é um aplicativo Android nativo que permite consultar o significado ou tradução de uma palavra **sem sair do aplicativo que você está usando**.

Ao selecionar qualquer texto em qualquer app (navegador, leitor de e-books, app de notícias), o menu flutuante do Android exibirá duas opções do Verbum:

- **Verbum: Definir** — abre um Bottom Sheet com a definição completa da palavra
- **Verbum: Traduzir** — abre um Bottom Sheet com a tradução EN ↔ PT

O resultado aparece em um overlay elegante e escuro sobre o app atual, sem interromper o contexto de leitura. Basta deslizar o sheet para baixo e continuar.

---

## 💡 Motivação

Quantas vezes você estava lendo um artigo em inglês, encontrou uma palavra desconhecida e teve que:

1. Copiar a palavra
2. Sair do app
3. Abrir o Google Tradutor (ou similar)
4. Colar e traduzir
5. Voltar ao app original (e perder o contexto de leitura)

O Verbum elimina todas essas etapas. **Selecione → Verbum → Resultado → Continue lendo.**

---

## ✨ Funcionalidades

| Funcionalidade | Status |
|---|---|
| Integração com `ACTION_PROCESS_TEXT` | ✅ |
| Duas entradas no menu (Definir / Traduzir) | ✅ |
| Bottom Sheet transparente sobre qualquer app | ✅ |
| Estado de Loading com animação shimmer | ✅ |
| Definição completa: palavra, fonética, classe gramatical, exemplo, sinônimos | ✅ |
| Tradução com direção automática (EN→PT / PT→EN) e alternativas | ✅ |
| Estado de erro com botão de retry | ✅ |
| Arquitetura MVVM + Repository Pattern | ✅ |
| Camada de dados mockada (pronta para integração com API ou SQLite) | ✅ |
| Tela de boas-vindas com instruções de uso | ✅ |

---

## 🛠️ Stack Tecnológica

| Componente | Tecnologia |
|---|---|
| Linguagem | Kotlin 2.0 |
| UI | Jetpack Compose + Material3 |
| Arquitetura | MVVM + Repository Pattern |
| Estado da UI | `StateFlow` + `collectAsStateWithLifecycle` |
| Coroutines | `kotlinx.coroutines` |
| DI (temporário) | Service Locator manual |
| SDK Mínimo | Android 6.0 (API 23) |
| SDK Alvo | Android 15 (API 35) |
| Build System | Gradle 8.7 com Version Catalogs |

---

## 📁 Estrutura de Pastas

```
Verbum/
├── app/
│   └── src/main/
│       ├── AndroidManifest.xml          ← Intent-filters para PROCESS_TEXT
│       ├── java/com/verbum/app/
│       │   ├── VerbumApplication.kt
│       │   ├── data/
│       │   │   ├── model/
│       │   │   │   ├── DefinitionResult.kt
│       │   │   │   └── TranslationResult.kt
│       │   │   ├── repository/
│       │   │   │   ├── DictionaryRepository.kt     ← Interface
│       │   │   │   ├── TranslatorRepository.kt     ← Interface
│       │   │   │   ├── FakeDictionaryRepository.kt ← Mock (dados estáticos)
│       │   │   │   └── FakeTranslatorRepository.kt ← Mock (dados estáticos)
│       │   │   └── di/
│       │   │       └── ServiceLocator.kt
│       │   ├── domain/
│       │   │   └── usecase/
│       │   │       ├── GetDefinitionUseCase.kt
│       │   │       └── GetTranslationUseCase.kt
│       │   └── ui/
│       │       ├── MainActivity.kt          ← Tela de boas-vindas
│       │       ├── DefineActivity.kt        ← Responde a "Verbum: Definir"
│       │       ├── TranslateActivity.kt     ← Responde a "Verbum: Traduzir"
│       │       ├── viewmodel/
│       │       │   ├── VerbumAction.kt
│       │       │   ├── VerbumUiState.kt
│       │       │   ├── VerbumViewModel.kt
│       │       │   └── VerbumViewModelFactory.kt
│       │       ├── sheet/
│       │       │   ├── VerbumBottomSheet.kt
│       │       │   └── components/
│       │       │       ├── Common.kt            ← LanguageBadge, VerbumFooter
│       │       │       ├── ShimmerBox.kt
│       │       │       ├── DefinitionContent.kt
│       │       │       ├── TranslationContent.kt
│       │       │       └── ErrorContent.kt
│       │       └── theme/
│       │           ├── Color.kt
│       │           ├── Type.kt
│       │           └── Theme.kt
│       └── res/
│           └── values/
│               ├── strings.xml
│               └── themes.xml
├── gradle/
│   ├── libs.versions.toml   ← Version Catalog
│   └── wrapper/
│       └── gradle-wrapper.properties
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── README.md
```

---

## 🚀 Como Rodar o Projeto

### Pré-requisitos

- **Android Studio** Ladybug (2024.2) ou superior
- **JDK 11** ou superior
- **Android SDK** com API 23+ instalado

### Passos

**1. Clonar o repositório:**
```bash
git clone https://github.com/seu-usuario/Verbum.git
cd Verbum
```

**2. Abrir no Android Studio:**
Abra o Android Studio, selecione **"Open"** e escolha a pasta `Verbum`. O Android Studio detectará o projeto Gradle automaticamente e configurará o wrapper.

**3. (Alternativa via linha de comando):**

Se você tiver o Gradle instalado no sistema, gere o wrapper primeiro:
```bash
gradle wrapper --gradle-version 8.7
chmod +x gradlew
```

Em seguida, compile e instale:
```bash
# Build de debug
./gradlew assembleDebug

# Instalar diretamente no dispositivo/emulador conectado
./gradlew installDebug
```

**4. Testar o app:**
- Abra qualquer app com texto (Chrome, Google Docs, Notes, etc.)
- Selecione uma palavra ou frase
- No menu flutuante, toque em **"Verbum: Definir"** ou **"Verbum: Traduzir"**
- O Bottom Sheet aparecerá sobre o app atual

**Palavras de exemplo para testar:**

| Palavra | Ação | Resultado esperado |
|---|---|---|
| `ephemeral` | Definir | Definição EN completa |
| `serendipity` | Definir | Definição EN completa |
| `saudade` | Definir | Definição PT completa |
| `resilience` | Definir | Definição EN completa |
| `love` | Traduzir | EN → PT: "amor" |
| `saudade` | Traduzir | PT → EN: "longing" |

---

## 🗄️ Fontes de Dados

> ⚠️ **Esta seção será preenchida em uma etapa futura do projeto.**

Atualmente, o Verbum utiliza dados **mockados/estáticos** (`FakeDictionaryRepository` e `FakeTranslatorRepository`) para demonstração. As interfaces `DictionaryRepository` e `TranslatorRepository` já estão desenhadas para receber implementações reais sem impacto na camada de UI ou de domínio.

### Opções em Avaliação

**Para Dicionário:**
- [ ] [Free Dictionary API](https://dictionaryapi.dev/) — REST API gratuita, sem chave
- [ ] [Merriam-Webster API](https://dictionaryapi.com/) — Alta qualidade, requer chave
- [ ] WordNet via SQLite + Room — Completamente offline

**Para Tradutor:**
- [ ] [LibreTranslate](https://libretranslate.com/) — Open-source, auto-hospedável
- [ ] [Google ML Kit Translate](https://developers.google.com/ml-kit/language/translation) — Offline, on-device
- [ ] [DeepL API](https://www.deepl.com/pro-api) — Alta qualidade, freemium

### Como Integrar uma Nova Fonte

1. Crie uma nova implementação das interfaces:
   ```kotlin
   class RemoteDictionaryRepository(
       private val apiService: DictionaryApiService
   ) : DictionaryRepository {
       override suspend fun getDefinition(word: String): Result<DefinitionResult> {
           // Sua implementação aqui
       }
   }
   ```

2. Registre-a no `ServiceLocator`:
   ```kotlin
   fun getDictionaryRepository(): DictionaryRepository =
       RemoteDictionaryRepository(retrofitService)
   ```

3. Nenhuma outra mudança é necessária na UI ou no domínio. ✅

---

## 🎨 Design System

O Verbum usa uma paleta escura inspirada em ambientes de leitura:

| Token | Cor | Uso |
|---|---|---|
| `VerbumIndigo` | `#7C6AF7` | Accent principal, chips, botões |
| `VerbumTeal` | `#4ECDC4` | Idioma de destino, alternativas |
| `VerbumBackground` | `#0D0D0F` | Fundo do Bottom Sheet |
| `VerbumOnBackground` | `#F0EFF5` | Texto principal |
| `VerbumOnSurfaceMedium` | `#B0AECB` | Texto secundário |

---

## 📄 Licença

```
MIT License — © 2026 Verbum Contributors
```