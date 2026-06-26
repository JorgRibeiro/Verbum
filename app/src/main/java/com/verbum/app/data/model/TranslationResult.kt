package com.verbum.app.data.model

/**
 * Representa o resultado de uma tradução.
 *
 * Esta é a entidade de domínio principal para traduções.
 * Em implementações futuras, pode ser mapeada a partir de respostas de APIs
 * (ex: LibreTranslate, DeepL, Google Cloud Translation) ou de modelos offline.
 *
 * @param originalText      O texto original fornecido pelo usuário.
 * @param translatedText    A tradução principal/primária.
 * @param sourceLanguage    Código ISO 639-1 do idioma de origem (ex: "en", "pt").
 * @param targetLanguage    Código ISO 639-1 do idioma de destino (ex: "pt", "en").
 * @param alternatives      Traduções alternativas ou contextuais para o mesmo texto.
 */
data class TranslationResult(
    val originalText: String,
    val translatedText: String,
    val sourceLanguage: String,
    val targetLanguage: String,
    val alternatives: List<String>
)
