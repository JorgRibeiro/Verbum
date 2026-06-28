package com.verbum.app.data.di

import com.verbum.app.data.repository.DictionaryRepository
import com.verbum.app.data.repository.MLKitTranslatorRepository
import com.verbum.app.data.repository.RemoteDictionaryRepository
import com.verbum.app.data.repository.TranslatorRepository

/**
 * Localizador de serviços (Service Locator) que fornece as implementações dos repositórios.
 *
 * ## Implementações ativas:
 * - **Dicionário:** [RemoteDictionaryRepository] → Free Dictionary API (dictionaryapi.dev)
 * - **Tradutor:** [MLKitTranslatorRepository] → Google ML Kit Translate (offline, on-device)
 *
 * ## Como trocar implementações (ex: para testes):
 * ```kotlin
 * ServiceLocator.setDictionaryRepository(FakeDictionaryRepository())
 * // ... executa os testes ...
 * ServiceLocator.reset()
 * ```
 */
object ServiceLocator {

    @Volatile
    private var dictionaryRepository: DictionaryRepository? = null

    @Volatile
    private var translatorRepository: TranslatorRepository? = null

    fun getDictionaryRepository(): DictionaryRepository =
        dictionaryRepository ?: synchronized(this) {
            dictionaryRepository ?: RemoteDictionaryRepository().also {
                dictionaryRepository = it
            }
        }

    fun getTranslatorRepository(): TranslatorRepository =
        translatorRepository ?: synchronized(this) {
            translatorRepository ?: MLKitTranslatorRepository().also {
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

    /** Reseta todas as instâncias para os padrões (útil entre testes). */
    fun reset() {
        synchronized(this) {
            dictionaryRepository = null
            translatorRepository = null
        }
    }
}
