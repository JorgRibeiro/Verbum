package com.verbum.app.domain.usecase

import com.verbum.app.data.model.TranslationResult
import com.verbum.app.data.repository.TranslatorRepository

/**
 * Caso de uso para traduzir um texto.
 *
 * Encapsula a lógica de negócio relacionada a traduções,
 * com detecção automática de idioma (EN ↔ PT).
 *
 * Validações realizadas:
 * - O texto não pode estar vazio ou em branco.
 *
 * Em versões futuras, este use case pode incluir:
 * - Detecção de idioma mais robusta (antes de delegar ao repositório)
 * - Configuração de par de idiomas pelo usuário (nas Settings)
 * - Truncagem de textos muito longos
 */
class GetTranslationUseCase(
    private val repository: TranslatorRepository
) {
    /**
     * Executa o caso de uso.
     *
     * @param text O texto a ser traduzido.
     * @return [Result.success] com [TranslationResult], ou [Result.failure] com exceção.
     */
    suspend operator fun invoke(text: String): Result<TranslationResult> {
        if (text.isBlank()) {
            return Result.failure(
                IllegalArgumentException("O texto para tradução não pode estar vazio.")
            )
        }
        return repository.translate(text.trim())
    }
}
