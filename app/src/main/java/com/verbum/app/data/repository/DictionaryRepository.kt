package com.verbum.app.data.repository

import com.verbum.app.data.model.DefinitionResult

/**
 * Contrato para a fonte de dados do dicionário.
 *
 * Define a API pública que qualquer implementação de dicionário deve seguir.
 * A camada de domínio e de UI dependem apenas desta interface, nunca das
 * implementações concretas — garantindo o Princípio da Inversão de Dependência.
 *
 * ## Implementações planejadas:
 * - [FakeDictionaryRepository]: dados estáticos para demonstração (atual)
 * - `RemoteDictionaryRepository`: consulta à Free Dictionary API ou similar
 * - `LocalDictionaryRepository`: banco de dados SQLite/Room com WordNet ou similar
 * - `CachedDictionaryRepository`: combina remoto com cache local
 */
interface DictionaryRepository {

    /**
     * Busca a definição de uma palavra.
     *
     * A operação é suspensa pois pode envolver I/O (rede ou disco).
     *
     * @param word A palavra a ser consultada. Não deve ser vazia nem em branco.
     * @return [Result.success] com [DefinitionResult] em caso de êxito,
     *         ou [Result.failure] com a exceção relevante em caso de erro.
     */
    suspend fun getDefinition(word: String): Result<DefinitionResult>
}
