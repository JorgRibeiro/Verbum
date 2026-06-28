package com.verbum.app.data.remote

import com.verbum.app.data.remote.dto.DictionaryResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Interface Retrofit para a Free Dictionary API.
 *
 * Base URL: https://api.dictionaryapi.dev/api/v2/
 *
 * Exemplos de uso:
 * - GET entries/en/ephemeral   → definição em inglês
 * - GET entries/pt/saudade     → definição em português (cobertura limitada)
 *
 * A API retorna HTTP 404 com JSON de erro quando a palavra não é encontrada.
 * O Retrofit lança [retrofit2.HttpException] nesse caso.
 */
interface DictionaryApiService {

    @GET("entries/{language}/{word}")
    suspend fun getDefinition(
        @Path("language") language: String,
        @Path("word") word: String
    ): List<DictionaryResponseDto>
}
