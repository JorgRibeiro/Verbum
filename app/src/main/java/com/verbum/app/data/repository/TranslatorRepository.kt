package com.verbum.app.data.repository

import com.verbum.app.data.model.TranslationResult

/**
 * Contrato para a fonte de dados do tradutor.
 *
 * Define a API pública que qualquer implementação de tradução deve seguir.
 * Suporta detecção automática de idioma com tradução bidirecional EN ↔ PT.
 *
 * ## Implementações planejadas:
 * - [FakeTranslatorRepository]: dados estáticos para demonstração (atual)
 * - `LibreTranslateRepository`: API open-source auto-hospedável
 * - `DeepLTranslatorRepository`: API DeepL para alta qualidade
 * - `OfflineTranslatorRepository`: modelo de tradução local (ex: ML Kit Translate)
 */
interface TranslatorRepository {

    /**
     * Traduz um texto, detectando automaticamente o idioma de origem.
     * A direção padrão de tradução é EN ↔ PT (bidirecional).
     *
     * A operação é suspensa pois pode envolver I/O (rede ou disco/modelo).
     *
     * @param text O texto a ser traduzido. Não deve ser vazio nem em branco.
     * @return [Result.success] com [TranslationResult] em caso de êxito,
     *         ou [Result.failure] com a exceção relevante em caso de erro.
     */
    suspend fun translate(text: String): Result<TranslationResult>
}
