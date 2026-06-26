package com.verbum.app.ui.sheet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FormatQuote
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.verbum.app.data.model.DefinitionResult
import com.verbum.app.ui.theme.VerbumDivider
import com.verbum.app.ui.theme.VerbumIndigo
import com.verbum.app.ui.theme.VerbumIndigoMuted
import com.verbum.app.ui.theme.VerbumOnBackground
import com.verbum.app.ui.theme.VerbumOnSurface
import com.verbum.app.ui.theme.VerbumOnSurfaceLow
import com.verbum.app.ui.theme.VerbumOnSurfaceMedium
import com.verbum.app.ui.theme.VerbumSurface
import com.verbum.app.ui.theme.VerbumSurfaceVariant

/**
 * Conteúdo do Bottom Sheet para o estado [VerbumUiState.DefinitionSuccess].
 *
 * Exibe:
 * - Palavra em destaque + badge de idioma
 * - Transcrição fonética (IPA)
 * - Chip de classe gramatical
 * - Definição principal
 * - Card de exemplo com ícone de aspas
 * - Flow de chips de sinônimos
 * - Rodapé Verbum
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DefinitionContent(result: DefinitionResult) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        // ── Cabeçalho: Palavra + Badge de idioma ──────────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = result.word,
                    style = MaterialTheme.typography.headlineLarge,
                    color = VerbumOnBackground,
                    fontWeight = FontWeight.Bold
                )
                if (!result.phonetic.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = result.phonetic,
                        style = MaterialTheme.typography.bodySmall,
                        color = VerbumOnSurfaceMedium
                    )
                }
            }
            LanguageBadge(language = result.language, isSource = true)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ── Chip: Classe Gramatical ───────────────────────────────────────────
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(VerbumIndigoMuted)
                .padding(horizontal = 12.dp, vertical = 5.dp)
        ) {
            Text(
                text = result.partOfSpeech,
                style = MaterialTheme.typography.labelMedium,
                color = VerbumIndigo,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // ── Definição ─────────────────────────────────────────────────────────
        Text(
            text = result.definition,
            style = MaterialTheme.typography.bodyLarge,
            color = VerbumOnSurface,
            lineHeight = 24.sp
        )

        // ── Card de Exemplo ───────────────────────────────────────────────────
        if (!result.example.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(14.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(VerbumSurfaceVariant)
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = Icons.Rounded.FormatQuote,
                    contentDescription = "Ícone de citação",
                    tint = VerbumIndigo,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = result.example,
                    style = MaterialTheme.typography.bodyMedium,
                    color = VerbumOnSurfaceMedium,
                    fontStyle = FontStyle.Italic,
                    lineHeight = 20.sp
                )
            }
        }

        // ── Seção de Sinônimos ────────────────────────────────────────────────
        if (result.synonyms.isNotEmpty()) {
            Spacer(modifier = Modifier.height(22.dp))
            Text(
                text = "SINÔNIMOS",
                style = MaterialTheme.typography.titleMedium,
                color = VerbumOnSurfaceLow
            )
            Spacer(modifier = Modifier.height(10.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                result.synonyms.forEach { synonym ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(VerbumSurface)
                            .border(1.dp, VerbumDivider, RoundedCornerShape(20.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                            .semantics { contentDescription = synonym }
                    ) {
                        Text(
                            text = synonym,
                            style = MaterialTheme.typography.labelMedium,
                            color = VerbumOnSurfaceMedium
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))
        VerbumFooter()
    }
}
