package com.verbum.app.ui.viewmodel

import com.verbum.app.data.model.DefinitionResult
import com.verbum.app.data.model.TranslationResult

/**
 * Representa o estado da UI do Bottom Sheet do Verbum.
 *
 * Segue o padrão Sealed Interface para estados mutuamente exclusivos,
 * permitindo exaustividade no `when` sem um ramo `else`.
 */
sealed interface VerbumUiState {

    /** Estado inicial enquanto aguarda a resposta do repositório. Exibe o Shimmer. */
    data object Loading : VerbumUiState

    /** Definição obtida com sucesso. Exibe o conteúdo do dicionário. */
    data class DefinitionSuccess(val result: DefinitionResult) : VerbumUiState

    /** Tradução obtida com sucesso. Exibe o conteúdo da tradução. */
    data class TranslationSuccess(val result: TranslationResult) : VerbumUiState

    /** Ocorreu um erro. Exibe a mensagem e o botão de retry. */
    data class Error(val message: String) : VerbumUiState
}
