package com.verbum.app.data.di

import com.verbum.app.data.repository.DictionaryRepository
import com.verbum.app.data.repository.FakeDictionaryRepository
import com.verbum.app.data.repository.FakeTranslatorRepository
import com.verbum.app.data.repository.TranslatorRepository

/**
 * Localizador de serviços (Service Locator) que fornece as implementações dos repositórios.
 *
 * Atua como um contêiner de dependências simples e de fácil substituição.
 * Em versões futuras, pode ser migrado para um framework de DI completo
 * como Hilt ou Koin sem impacto nas camadas de domínio e UI.
 *
 * ## Como trocar implementações:
 * Para migrar de dados mockados para uma API real, basta substituir as
 * instâncias nas funções `get*Repository()` abaixo:
 * ```kotlin
 * fun getDictionaryRepository(): DictionaryRepository =
 *     dictionaryRepository ?: RemoteDictionaryRepository(apiService).also { ... }
 * ```
 *
 * ## Uso em testes:
 * Use [setDictionaryRepository] e [setTranslatorRepository] para injetar
 * implementações de teste e [reset] para limpar após cada teste.
 */
object ServiceLocator {

    @Volatile
    private var dictionaryRepository: DictionaryRepository? = null

    @Volatile
    private var translatorRepository: TranslatorRepository? = null

    fun getDictionaryRepository(): DictionaryRepository =
        dictionaryRepository ?: synchronized(this) {
            dictionaryRepository ?: FakeDictionaryRepository().also {
                dictionaryRepository = it
            }
        }

    fun getTranslatorRepository(): TranslatorRepository =
        translatorRepository ?: synchronized(this) {
            translatorRepository ?: FakeTranslatorRepository().also {
                translatorRepository = it
            }
        }

    /** Injeta um repositório de dicionário customizado (útil em testes). */
    fun setDictionaryRepository(repository: DictionaryRepository) {
        synchronized(this) { dictionaryRepository = repository }
    }

    /** Injeta um repositório de tradutor customizado (útil em testes). */
    fun setTranslatorRepository(repository: TranslatorRepository) {
        synchronized(this) { translatorRepository = repository }
    }

    /** Reseta todas as instâncias (útil entre testes). */
    fun reset() {
        synchronized(this) {
            dictionaryRepository = null
            translatorRepository = null
        }
    }
}
