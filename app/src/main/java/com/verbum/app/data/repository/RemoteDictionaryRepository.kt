package com.verbum.app.data.repository

import com.verbum.app.data.model.DefinitionResult
import com.verbum.app.data.remote.DictionaryApiService
import com.verbum.app.data.remote.RetrofitClient
import retrofit2.HttpException
import java.io.IOException

/**
 * Implementação real do [DictionaryRepository] que consulta a Free Dictionary API.
 *
 * Endpoint: https://api.dictionaryapi.dev/api/v2/entries/{language}/{word}
 *
 * Comportamento:
 * - Detecta automaticamente o idioma da palavra (EN ou PT) por heurística de caracteres
 * - Para palavras em PT: tenta o endpoint `/pt/`, com fallback para `/en/` se 404
 * - Mapeia a resposta da API para [DefinitionResult]
 * - Trata todos os erros de rede e HTTP com mensagens amigáveis em português
 *
 * Limitações conhecidas:
 * - Cobertura de palavras em PT é menor que em EN na API
 * - Sem cache local (palavras repetidas fazem nova requisição)
 */
class RemoteDictionaryRepository(
    private val apiService: DictionaryApiService = RetrofitClient.dictionaryApi
) : DictionaryRepository {

    override suspend fun getDefinition(word: String): Result<DefinitionResult> {
        val cleanWord = word.lowercase().trim()
        val detectedLang = if (isPortuguese(word)) "pt" else "en"

        return try {
            fetchDefinition(cleanWord, detectedLang)
        } catch (e: HttpException) {
            // Para PT, se a palavra não foi encontrada, tenta em EN como fallback
            if (e.code() == 404 && detectedLang == "pt") {
                try {
                    fetchDefinition(cleanWord, "en")
                } catch (fallbackException: Exception) {
                    handleException(fallbackException, word)
                }
            } else {
                handleException(e, word)
            }
        } catch (e: Exception) {
            handleException(e, word)
        }
    }

    private suspend fun fetchDefinition(word: String, language: String): Result<DefinitionResult> {
        val response = apiService.getDefinition(language, word)

        val dto = response.firstOrNull()
            ?: return Result.failure(Exception("\"$word\" não encontrado no dicionário."))

        val meaning = dto.meanings.firstOrNull { it.definitions.isNotEmpty() }
            ?: return Result.failure(Exception("Nenhum significado encontrado para \"$word\"."))

        val definition = meaning.definitions.firstOrNull()
            ?: return Result.failure(Exception("Nenhuma definição encontrada para \"$word\"."))

        // Fonética: prefere a lista de phonetics (mais completa) sobre o campo top-level
        val phonetic = dto.phonetics
            .firstOrNull { !it.text.isNullOrBlank() }
            ?.text
            ?: dto.phonetic

        // Sinônimos: combina nível-significado + nível-definição, sem duplicatas, máximo 6
        val synonyms = (meaning.synonyms + definition.synonyms)
            .filter { it.isNotBlank() }
            .distinct()
            .take(6)

        return Result.success(
            DefinitionResult(
                word = dto.word,
                phonetic = phonetic,
                partOfSpeech = meaning.partOfSpeech,
                definition = definition.definition,
                example = definition.example,
                synonyms = synonyms,
                language = language
            )
        )
    }

    private fun handleException(e: Exception, word: String): Result<DefinitionResult> =
        when (e) {
            is HttpException -> when (e.code()) {
                404 -> Result.failure(Exception("\"$word\" não foi encontrado no dicionário."))
                429 -> Result.failure(Exception("Muitas consultas seguidas. Aguarde um momento e tente novamente."))
                500, 503 -> Result.failure(Exception("O servidor do dicionário está fora do ar. Tente novamente mais tarde."))
                else -> Result.failure(Exception("Erro do servidor (código ${e.code()}). Tente novamente."))
            }
            is IOException -> Result.failure(Exception("Sem conexão com a internet. Verifique sua rede e tente novamente."))
            else -> Result.failure(Exception("Erro inesperado: ${e.message ?: "desconhecido"}"))
        }

    /**
     * Heurística simples para detectar se o texto está em Português.
     * Verifica a presença de caracteres exclusivos do PT (acentos, cedilha).
     */
    private fun isPortuguese(text: String): Boolean {
        val ptChars = setOf('ã', 'õ', 'ç', 'á', 'é', 'í', 'ó', 'ú', 'â', 'ê', 'ô', 'à', 'ü')
        return text.any { it.lowercaseChar() in ptChars }
    }
}
