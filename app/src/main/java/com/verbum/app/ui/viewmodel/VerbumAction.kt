package com.verbum.app.ui.viewmodel

/**
 * Representa as ações disponíveis no Verbum, disparadas pelo menu de seleção de texto.
 */
enum class VerbumAction {
    /** Busca a definição da palavra selecionada no dicionário. */
    Define,

    /** Traduz o texto selecionado (EN → PT ou PT → EN, detectado automaticamente). */
    Translate
}
