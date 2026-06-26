package com.verbum.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.verbum.app.data.repository.DictionaryRepository
import com.verbum.app.data.repository.TranslatorRepository
import com.verbum.app.domain.usecase.GetDefinitionUseCase
import com.verbum.app.domain.usecase.GetTranslationUseCase

/**
 * Factory para criar instâncias de [VerbumViewModel] com suas dependências.
 *
 * Permite que as Activities criem o ViewModel passando as implementações
 * concretas dos repositórios fornecidas pelo [com.verbum.app.data.di.ServiceLocator],
 * sem necessidade de um framework de DI.
 */
class VerbumViewModelFactory(
    private val dictionaryRepository: DictionaryRepository,
    private val translatorRepository: TranslatorRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VerbumViewModel::class.java)) {
            return VerbumViewModel(
                getDefinitionUseCase = GetDefinitionUseCase(dictionaryRepository),
                getTranslationUseCase = GetTranslationUseCase(translatorRepository)
            ) as T
        }
        throw IllegalArgumentException("Classe de ViewModel desconhecida: ${modelClass.name}")
    }
}
