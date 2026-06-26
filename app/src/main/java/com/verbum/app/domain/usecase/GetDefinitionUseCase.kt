package com.verbum.app.domain.usecase

import com.verbum.app.data.model.DefinitionResult
import com.verbum.app.data.repository.DictionaryRepository

/**
 * Caso de uso para buscar a definição de uma palavra.
 *
 * Encapsula a lógica de negócio relacionada a consultas ao dicionário,
 * mantendo a camada de UI agnóstica em relação à fonte de dados.
 *
 * Validações realizadas:
 * - A palavra não pode estar vazia ou em branco.
 *
 * Em versões futuras, este use case pode incluir:
 * - Normalização de texto (remover pontuação, acentos)
 * - Lógica de fallback (tentar fonte remota, depois local)
 * - Cache em memória para consultas recentes
 */
class GetDefinitionUseCase(
    private val repository: DictionaryRepository
) {
    /**
     * Executa o caso de uso.
     *
     * @param word A palavra a ser consultada.
     * @return [Result.success] com [DefinitionResult], ou [Result.failure] com exceção.
     */
    suspend operator fun invoke(word: String): Result<DefinitionResult> {
        if (word.isBlank()) {
            return Result.failure(
                IllegalArgumentException("A palavra não pode estar vazia.")
            )
        }
        return repository.getDefinition(word.trim())
    }
}
