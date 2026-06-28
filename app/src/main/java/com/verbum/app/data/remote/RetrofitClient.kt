package com.verbum.app.data.remote

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Singleton que fornece o cliente Retrofit configurado para a Free Dictionary API.
 *
 * Configurações:
 * - Timeout de 15s (conexão e leitura)
 * - Logging de requisições em nível BASIC (apenas URL + código de resposta)
 * - Kotlinx Serialization como conversor JSON com modo leniente
 *   (ignora campos desconhecidos e permite valores nulos onde esperado)
 */
object RetrofitClient {

    private const val BASE_URL = "https://api.dictionaryapi.dev/api/v2/"

    /**
     * Configuração do parser JSON — leniente para lidar com campos inesperados
     * que a API possa adicionar no futuro sem quebrar o app.
     */
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()

    /** Instância preguiçosa do serviço de dicionário. Criada uma única vez. */
    val dictionaryApi: DictionaryApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json; charset=UTF-8".toMediaType()))
            .build()
            .create(DictionaryApiService::class.java)
    }
}
