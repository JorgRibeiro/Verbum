package com.verbum.app.ui.sheet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.ArrowDownward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.verbum.app.data.model.TranslationResult
import com.verbum.app.ui.theme.VerbumBackground
import com.verbum.app.ui.theme.VerbumOnBackground
import com.verbum.app.ui.theme.VerbumOnSurfaceLow
import com.verbum.app.ui.theme.VerbumOnSurfaceMedium
import com.verbum.app.ui.theme.VerbumTeal
import com.verbum.app.ui.theme.VerbumTealMuted

/**
 * Conteúdo do Bottom Sheet para o estado [VerbumUiState.TranslationSuccess].
 *
 * Exibe:
 * - Direção da tradução: [EN] → [PT] com badges de idioma
 * - Texto original em tamanho menor
 * - Seta de direção (↓)
 * - Tradução em destaque com tipografia bold
 * - Lista de alternativas com marcadores teal
 * - Rodapé Verbum
 */
@Composable
fun TranslationContent(result: TranslationResult) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        // ── Cabeçalho: Direção da tradução ────────────────────────────────────
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.semantics {
                contentDescription = "Traduzir de ${result.sourceLanguage} para ${result.targetLanguage}"
            }
        ) {
            LanguageBadge(language = result.sourceLanguage, isSource = true)
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                contentDescription = null,
                tint = VerbumOnSurfaceLow,
                modifier = Modifier.size(14.dp)
            )
            LanguageBadge(language = result.targetLanguage, isSource = false)
        }

        Spacer(modifier = Modifier.height(22.dp))

        // ── Texto Original ────────────────────────────────────────────────────
        Text(
            text = result.originalText,
            style = MaterialTheme.typography.bodyMedium,
            color = VerbumOnSurfaceMedium,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // ── Seta direcional ───────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(VerbumTealMuted),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowDownward,
                contentDescription = null,
                tint = VerbumTeal,
                modifier = Modifier.size(14.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // ── Tradução em Destaque ──────────────────────────────────────────────
        Text(
            text = result.translatedText,
            style = MaterialTheme.typography.headlineMedium,
            color = VerbumOnBackground,
            fontWeight = FontWeight.Bold,
            lineHeight = 34.sp
        )

        // ── Alternativas ──────────────────────────────────────────────────────
        if (result.alternatives.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "ALTERNATIVAS",
                style = MaterialTheme.typography.titleMedium,
                color = VerbumOnSurfaceLow
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                result.alternatives.forEach { alternative ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(VerbumBackground)
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(VerbumTeal)
                        )
                        Text(
                            text = alternative,
                            style = MaterialTheme.typography.bodyMedium,
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
