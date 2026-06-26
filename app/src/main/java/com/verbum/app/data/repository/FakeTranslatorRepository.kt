package com.verbum.app.data.repository

import com.verbum.app.data.model.TranslationResult
import kotlinx.coroutines.delay

/**
 * Implementação de demonstração do [TranslatorRepository] com dados estáticos.
 *
 * Realiza detecção básica de idioma (EN/PT) com base em caracteres especiais
 * e palavras comuns do português, e exibe traduções mockadas.
 *
 * Simula um atraso de rede (700ms) para testar os estados de Loading → Success/Error.
 *
 * **TODO**: Substituir por uma implementação real em versão futura:
 * - `LibreTranslateRepository` usando a API open-source LibreTranslate
 * - `MLKitTranslatorRepository` usando Google ML Kit Translate (offline)
 * - `DeepLTranslatorRepository` para traduções de alta qualidade
 */
class FakeTranslatorRepository : TranslatorRepository {

    private val enToPt: Map<String, TranslationResult> = mapOf(
        "hello" to TranslationResult("hello", "olá", "en", "pt", listOf("oi", "bom dia", "saudações")),
        "world" to TranslationResult("world", "mundo", "en", "pt", listOf("terra", "universo", "globo")),
        "love" to TranslationResult("love", "amor", "en", "pt", listOf("carinho", "afeto", "paixão")),
        "book" to TranslationResult("book", "livro", "en", "pt", listOf("obra", "volume", "tomo")),
        "water" to TranslationResult("water", "água", "en", "pt", listOf("líquido", "H₂O")),
        "time" to TranslationResult("time", "tempo", "en", "pt", listOf("hora", "duração", "período")),
        "life" to TranslationResult("life", "vida", "en", "pt", listOf("existência", "vivência")),
        "house" to TranslationResult("house", "casa", "en", "pt", listOf("lar", "residência", "moradia")),
        "friend" to TranslationResult("friend", "amigo", "en", "pt", listOf("colega", "companheiro", "parceiro")),
        "beautiful" to TranslationResult("beautiful", "bonito", "en", "pt", listOf("lindo", "belo", "formoso")),
        "ephemeral" to TranslationResult("ephemeral", "efêmero", "en", "pt", listOf("passageiro", "transitório", "fugaz")),
        "serendipity" to TranslationResult("serendipity", "serendipidade", "en", "pt", listOf("feliz coincidência", "boa sorte inesperada")),
        "knowledge" to TranslationResult("knowledge", "conhecimento", "en", "pt", listOf("saber", "aprendizado", "sabedoria")),
        "dream" to TranslationResult("dream", "sonho", "en", "pt", listOf("devaneio", "aspiração", "visão")),
        "moon" to TranslationResult("moon", "lua", "en", "pt", listOf("satélite natural")),
        "sun" to TranslationResult("sun", "sol", "en", "pt", listOf("estrela", "astro-rei")),
        "peace" to TranslationResult("peace", "paz", "en", "pt", listOf("tranquilidade", "harmonia", "sossego")),
        "freedom" to TranslationResult("freedom", "liberdade", "en", "pt", listOf("independência", "autonomia")),
        "language" to TranslationResult("language", "idioma", "en", "pt", listOf("língua", "linguagem", "dialeto")),
        "resilience" to TranslationResult("resilience", "resiliência", "en", "pt", listOf("robustez", "resistência", "elasticidade")),
    )

    private val ptToEn: Map<String, TranslationResult> = mapOf(
        "olá" to TranslationResult("olá", "hello", "pt", "en", listOf("hi", "hey", "greetings")),
        "oi" to TranslationResult("oi", "hi", "pt", "en", listOf("hello", "hey")),
        "amor" to TranslationResult("amor", "love", "pt", "en", listOf("affection", "passion", "adoration")),
        "livro" to TranslationResult("livro", "book", "pt", "en", listOf("volume", "tome", "work")),
        "água" to TranslationResult("água", "water", "pt", "en", listOf("liquid", "H₂O")),
        "tempo" to TranslationResult("tempo", "time", "pt", "en", listOf("weather", "duration", "period")),
        "vida" to TranslationResult("vida", "life", "pt", "en", listOf("existence", "living")),
        "casa" to TranslationResult("casa", "house", "pt", "en", listOf("home", "residence", "dwelling")),
        "amigo" to TranslationResult("amigo", "friend", "pt", "en", listOf("companion", "buddy", "pal")),
        "saudade" to TranslationResult("saudade", "longing", "pt", "en", listOf("nostalgia", "yearning", "melancholy")),
        "beleza" to TranslationResult("beleza", "beauty", "pt", "en", listOf("loveliness", "attractiveness")),
        "conhecimento" to TranslationResult("conhecimento", "knowledge", "pt", "en", listOf("wisdom", "learning", "expertise")),
        "sonho" to TranslationResult("sonho", "dream", "pt", "en", listOf("aspiration", "vision")),
        "lua" to TranslationResult("lua", "moon", "pt", "en", listOf("natural satellite")),
        "paz" to TranslationResult("paz", "peace", "pt", "en", listOf("tranquility", "harmony", "calm")),
        "liberdade" to TranslationResult("liberdade", "freedom", "pt", "en", listOf("liberty", "independence", "autonomy")),
        "idioma" to TranslationResult("idioma", "language", "pt", "en", listOf("tongue", "dialect", "speech")),
        "resiliência" to TranslationResult("resiliência", "resilience", "pt", "en", listOf("toughness", "elasticity")),
        "efêmero" to TranslationResult("efêmero", "ephemeral", "pt", "en", listOf("transient", "fleeting", "short-lived")),
    )

    /** Detecta se o texto está em Português com base em caracteres e palavras típicas. */
    private fun isPortuguese(text: String): Boolean {
        val ptSpecificChars = setOf('ã', 'õ', 'ç', 'á', 'é', 'í', 'ó', 'ú', 'â', 'ê', 'ô', 'à', 'ü')
        val commonPtWords = setOf("olá", "oi", "não", "sim", "que", "com", "por", "uma", "para", "mas", "são", "está")
        val normalizedText = text.lowercase().trim()
        return text.any { it in ptSpecificChars } || normalizedText in commonPtWords
    }

    override suspend fun translate(text: String): Result<TranslationResult> {
        delay(700L) // Simula latência de rede

        val normalizedText = text.lowercase().trim()
        val isPt = isPortuguese(text)

        val result = if (isPt) ptToEn[normalizedText] else enToPt[normalizedText]

        return if (result != null) {
            Result.success(result)
        } else {
            // Fallback genérico para texto não mapeado
            val (source, target, translated) = if (isPt) {
                Triple("pt", "en", "[tradução indisponível na demonstração]")
            } else {
                Triple("en", "pt", "[translation unavailable in demo]")
            }
            Result.success(
                TranslationResult(
                    originalText = text,
                    translatedText = translated,
                    sourceLanguage = source,
                    targetLanguage = target,
                    alternatives = listOf("Traduções reais serão disponibilizadas em uma versão futura com integração de API.")
                )
            )
        }
    }
}
