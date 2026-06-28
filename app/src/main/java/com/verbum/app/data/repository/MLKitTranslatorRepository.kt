package com.verbum.app.data.repository

import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import com.verbum.app.data.model.TranslationResult
import kotlinx.coroutines.tasks.await

/**
 * Implementação real do [TranslatorRepository] usando o Google ML Kit Translate.
 *
 * Características:
 * - **100% offline** — os modelos são baixados para o dispositivo (~30MB por par)
 * - **Sem chave de API** — usa os modelos on-device do Google Play Services
 * - **Privacidade total** — o texto nunca sai do aparelho
 * - Suporta tradução bidirecional EN ↔ PT automaticamente
 *
 * ## Fluxo de primeiro uso:
 * 1. Ao traduzir pela primeira vez, detecta o idioma e baixa o modelo (~30MB)
 *    com uma mensagem clara ao usuário para tentar novamente após o download
 * 2. Nas chamadas seguintes, o modelo já está em cache e a tradução é instantânea
 *
 * ## Gerenciamento de recursos:
 * Os tradutores são criados uma vez (lazy) e reutilizados para economizar memória.
 * Em versões futuras, [close()] pode ser chamado no onDestroy do ViewModel.
 */
class MLKitTranslatorRepository : TranslatorRepository {

    /**
     * Tradutor EN → PT, criado lazily na primeira chamada.
     */
    private val enToPtTranslator: Translator by lazy {
        Translation.getClient(
            TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(TranslateLanguage.PORTUGUESE)
                .build()
        )
    }

    /**
     * Tradutor PT → EN, criado lazily na primeira chamada.
     */
    private val ptToEnTranslator: Translator by lazy {
        Translation.getClient(
            TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.PORTUGUESE)
                .setTargetLanguage(TranslateLanguage.ENGLISH)
                .build()
        )
    }

    override suspend fun translate(text: String): Result<TranslationResult> {
        return try {
            val isPt = isPortuguese(text)
            val translator = if (isPt) ptToEnTranslator else enToPtTranslator
            val sourceLanguage = if (isPt) "pt" else "en"
            val targetLanguage = if (isPt) "en" else "pt"

            // Baixa o modelo se necessário. Se já estiver em cache, retorna imediatamente.
            // Sem restrição de WiFi para não bloquear o usuário em redes celulares.
            translator.downloadModelIfNeeded().await()

            val translated = translator.translate(text).await()

            Result.success(
                TranslationResult(
                    originalText = text,
                    translatedText = translated,
                    sourceLanguage = sourceLanguage,
                    targetLanguage = targetLanguage,
                    alternatives = emptyList() // ML Kit não fornece alternativas nativas
                )
            )
        } catch (e: Exception) {
            // O ML Kit lança exceções genéricas — analisamos a mensagem para dar feedback útil
            val message = e.message?.lowercase() ?: ""
            val friendlyMessage = when {
                "model" in message || "download" in message ->
                    "Baixando modelo de tradução (≈30MB).\nAguarde e tente novamente em instantes."
                "network" in message || "connection" in message ->
                    "Sem conexão. O modelo de tradução ainda está sendo baixado. Conecte-se à internet e tente novamente."
                else ->
                    "Erro ao traduzir: ${e.message ?: "erro desconhecido"}"
            }
            Result.failure(Exception(friendlyMessage))
        }
    }

    /**
     * Heurística simples para detectar Português pelo uso de caracteres exclusivos do idioma.
     */
    private fun isPortuguese(text: String): Boolean {
        val ptChars = setOf('ã', 'õ', 'ç', 'á', 'é', 'í', 'ó', 'ú', 'â', 'ê', 'ô', 'à', 'ü')
        return text.any { it.lowercaseChar() in ptChars }
    }
}
