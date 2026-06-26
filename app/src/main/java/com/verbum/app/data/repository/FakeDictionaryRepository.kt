package com.verbum.app.data.repository

import com.verbum.app.data.model.DefinitionResult
import kotlinx.coroutines.delay

/**
 * Implementação de demonstração do [DictionaryRepository] com dados estáticos.
 *
 * Simula um atraso de rede (600ms) para permitir o teste dos estados
 * Loading → Success/Error na UI.
 *
 * **TODO**: Substituir por uma implementação real em versão futura:
 * - `RemoteDictionaryRepository` usando Free Dictionary API (https://dictionaryapi.dev/)
 * - `LocalDictionaryRepository` usando Room + WordNet SQLite
 */
class FakeDictionaryRepository : DictionaryRepository {

    private val definitions: Map<String, DefinitionResult> = mapOf(

        "ephemeral" to DefinitionResult(
            word = "ephemeral",
            phonetic = "/ɪˈfem.ər.əl/",
            partOfSpeech = "adjective",
            definition = "Lasting for a very short time; transitory. Used to describe things that exist or are significant for only a brief period.",
            example = "The ephemeral beauty of cherry blossoms makes them all the more precious.",
            synonyms = listOf("transient", "fleeting", "momentary", "brief", "short-lived"),
            language = "en"
        ),

        "serendipity" to DefinitionResult(
            word = "serendipity",
            phonetic = "/ˌser.ənˈdɪp.ɪ.ti/",
            partOfSpeech = "noun",
            definition = "The occurrence and development of events by chance in a happy or beneficial way; the faculty of making fortunate discoveries by accident.",
            example = "Finding my favorite book at a garage sale for one dollar was pure serendipity.",
            synonyms = listOf("luck", "fortune", "providence", "happy chance", "kismet"),
            language = "en"
        ),

        "eloquent" to DefinitionResult(
            word = "eloquent",
            phonetic = "/ˈel.ə.kwənt/",
            partOfSpeech = "adjective",
            definition = "Fluent or persuasive in speaking or writing; clearly expressing or indicating something with skill and style.",
            example = "She gave an eloquent speech that moved the entire audience to tears.",
            synonyms = listOf("articulate", "expressive", "fluent", "persuasive", "well-spoken"),
            language = "en"
        ),

        "metamorphosis" to DefinitionResult(
            word = "metamorphosis",
            phonetic = "/ˌmet.əˈmɔː.fə.sɪs/",
            partOfSpeech = "noun",
            definition = "A process of transformation from an immature form to an adult form in distinct stages; a change of the form or nature of a thing or person into a completely different one.",
            example = "The caterpillar's metamorphosis into a butterfly is one of nature's most remarkable transformations.",
            synonyms = listOf("transformation", "transmutation", "change", "conversion", "evolution"),
            language = "en"
        ),

        "resilience" to DefinitionResult(
            word = "resilience",
            phonetic = "/rɪˈzɪl.i.əns/",
            partOfSpeech = "noun",
            definition = "The capacity to recover quickly from difficulties; toughness. The ability of something to return to its original shape after being bent or stretched.",
            example = "Her resilience in the face of adversity inspired everyone who knew her story.",
            synonyms = listOf("toughness", "adaptability", "durability", "flexibility", "strength"),
            language = "en"
        ),

        "saudade" to DefinitionResult(
            word = "saudade",
            phonetic = "/saw.ˈda.dʒi/",
            partOfSpeech = "substantivo",
            definition = "Sentimento melancólico de saudosismo por algo ou alguém querido que está ausente; longing profundo com um misto de amor, nostalgia e senso de perda.",
            example = "Sinto saudade dos dias de infância passados na casa dos meus avós no interior.",
            synonyms = listOf("nostalgia", "melancolia", "anseio", "longing", "ânsia"),
            language = "pt"
        ),

        "efêmero" to DefinitionResult(
            word = "efêmero",
            phonetic = "/e.ˈfe.me.ro/",
            partOfSpeech = "adjetivo",
            definition = "Que dura muito pouco tempo; passageiro, transitório. Que existe ou é significante apenas por um período muito breve, desaparecendo rapidamente.",
            example = "A beleza efêmera das flores de cerejeira torna cada florescimento ainda mais especial e aguardado.",
            synonyms = listOf("passageiro", "transitório", "fugaz", "momentâneo", "breve"),
            language = "pt"
        ),
    )

    override suspend fun getDefinition(word: String): Result<DefinitionResult> {
        delay(600L) // Simula latência de rede

        val normalizedWord = word.lowercase().trim()
        val result = definitions[normalizedWord]

        return if (result != null) {
            Result.success(result)
        } else {
            // Fallback genérico para qualquer palavra não mapeada
            Result.success(
                DefinitionResult(
                    word = word,
                    phonetic = null,
                    partOfSpeech = "—",
                    definition = "Esta palavra não foi encontrada nos dados de demonstração. Em uma versão futura, o Verbum consultará um dicionário real (API online ou banco de dados local).",
                    example = null,
                    synonyms = emptyList(),
                    language = "en"
                )
            )
        }
    }
}
