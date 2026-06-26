package com.verbum.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.verbum.app.domain.usecase.GetDefinitionUseCase
import com.verbum.app.domain.usecase.GetTranslationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel principal do Verbum.
 *
 * Responsável por:
 * 1. Gerenciar o [VerbumUiState] exposto como [StateFlow] para a UI
 * 2. Orquestrar as chamadas aos casos de uso ([GetDefinitionUseCase], [GetTranslationUseCase])
 * 3. Persistir o contexto da última ação para suporte ao retry
 *
 * Não tem conhecimento direto de nenhum framework de UI (Android/Compose),
 * apenas depende dos casos de uso da camada de domínio.
 */
class VerbumViewModel(
    private val getDefinitionUseCase: GetDefinitionUseCase,
    private val getTranslationUseCase: GetTranslationUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<VerbumUiState>(VerbumUiState.Loading)

    /** Estado da UI observável pelo Bottom Sheet. */
    val uiState: StateFlow<VerbumUiState> = _uiState.asStateFlow()

    // Contexto da última ação para suporte ao retry
    private var lastAction: VerbumAction? = null
    private var lastText: String = ""

    /**
     * Processa uma ação com o texto selecionado pelo usuário.
     *
     * Emite [VerbumUiState.Loading] imediatamente e depois
     * [VerbumUiState.DefinitionSuccess] / [VerbumUiState.TranslationSuccess] / [VerbumUiState.Error].
     *
     * @param action A ação a ser executada ([VerbumAction.Define] ou [VerbumAction.Translate]).
     * @param text   O texto selecionado pelo usuário via [android.content.Intent.EXTRA_PROCESS_TEXT].
     */
    fun process(action: VerbumAction, text: String) {
        lastAction = action
        lastText = text

        viewModelScope.launch {
            _uiState.value = VerbumUiState.Loading

            when (action) {
                VerbumAction.Define -> {
                    getDefinitionUseCase(text)
                        .onSuccess { result ->
                            _uiState.value = VerbumUiState.DefinitionSuccess(result)
                        }
                        .onFailure { throwable ->
                            _uiState.value = VerbumUiState.Error(
                                throwable.message ?: "Erro ao buscar a definição."
                            )
                        }
                }
                VerbumAction.Translate -> {
                    getTranslationUseCase(text)
                        .onSuccess { result ->
                            _uiState.value = VerbumUiState.TranslationSuccess(result)
                        }
                        .onFailure { throwable ->
                            _uiState.value = VerbumUiState.Error(
                                throwable.message ?: "Erro ao traduzir o texto."
                            )
                        }
                }
            }
        }
    }

    /**
     * Repete a última ação realizada. Usado pelo botão de retry no estado de erro.
     * Não faz nada se nenhuma ação foi executada anteriormente.
     */
    fun retry() {
        val action = lastAction ?: return
        process(action, lastText)
    }
}
