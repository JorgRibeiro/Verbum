package com.verbum.app.data.model

/**
 * Representa o resultado de uma consulta ao dicionário.
 *
 * Esta é a entidade de domínio principal para definições de palavras.
 * Em implementações futuras, pode ser mapeada a partir de respostas de APIs
 * (ex: Free Dictionary API, Merriam-Webster) ou de entradas do banco SQLite/Room.
 *
 * @param word          A palavra consultada.
 * @param phonetic      A transcrição fonética em IPA, se disponível.
 * @param partOfSpeech  A classe gramatical (ex: "noun", "verb", "substantivo").
 * @param definition    A definição principal da palavra.
 * @param example       Uma frase de exemplo exibindo a palavra em contexto.
 * @param synonyms      Lista de sinônimos ou palavras relacionadas.
 * @param language      Código ISO 639-1 do idioma da palavra (ex: "en", "pt").
 */
data class DefinitionResult(
    val word: String,
    val phonetic: String?,
    val partOfSpeech: String,
    val definition: String,
    val example: String?,
    val synonyms: List<String>,
    val language: String = "en"
)
