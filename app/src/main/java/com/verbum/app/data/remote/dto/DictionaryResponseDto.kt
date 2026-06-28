package com.verbum.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTOs que mapeiam a resposta JSON da Free Dictionary API.
 * https://api.dictionaryapi.dev/api/v2/entries/en/{word}
 *
 * A API retorna uma lista de objetos [DictionaryResponseDto], cada um
 * representando uma entrada do dicionário para a palavra consultada.
 */

@Serializable
data class DictionaryResponseDto(
    val word: String,
    val phonetic: String? = null,
    val phonetics: List<PhoneticDto> = emptyList(),
    val meanings: List<MeaningDto> = emptyList()
)

@Serializable
data class PhoneticDto(
    val text: String? = null,
    val audio: String? = null
)

@Serializable
data class MeaningDto(
    val partOfSpeech: String = "",
    val definitions: List<DefinitionDto> = emptyList(),
    val synonyms: List<String> = emptyList(),
    val antonyms: List<String> = emptyList()
)

@Serializable
data class DefinitionDto(
    val definition: String = "",
    val example: String? = null,
    val synonyms: List<String> = emptyList(),
    val antonyms: List<String> = emptyList()
)

/**
 * Resposta de erro da API quando a palavra não é encontrada (HTTP 404).
 */
@Serializable
data class DictionaryErrorDto(
    val title: String? = null,
    val message: String? = null,
    val resolution: String? = null
)
