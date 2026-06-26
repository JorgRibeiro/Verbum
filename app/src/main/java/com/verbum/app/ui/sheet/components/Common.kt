package com.verbum.app.ui.sheet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.verbum.app.ui.theme.VerbumIndigo
import com.verbum.app.ui.theme.VerbumIndigoMuted
import com.verbum.app.ui.theme.VerbumOnSurfaceLow
import com.verbum.app.ui.theme.VerbumTeal
import com.verbum.app.ui.theme.VerbumTealMuted

/**
 * Badge exibindo o código de idioma (ex: "EN", "PT").
 *
 * Usa cores diferentes para idioma de origem (índigo) e de destino (teal),
 * para diferenciar visualmente a direção da tradução.
 *
 * @param language    Código ISO 639-1 do idioma (ex: "en", "pt").
 * @param isSource    Se verdadeiro, usa estilo de origem (índigo); caso contrário, destino (teal).
 */
@Composable
fun LanguageBadge(
    language: String,
    isSource: Boolean = false
) {
    val bgColor: Color = if (isSource) VerbumIndigoMuted else VerbumTealMuted
    val textColor: Color = if (isSource) VerbumIndigo else VerbumTeal

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bgColor)
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .semantics {
                contentDescription = "Idioma: ${language.uppercase()}"
            }
    ) {
        Text(
            text = language.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * Rodapé padrão exibido no fim de cada conteúdo do Bottom Sheet.
 * Indica que os dados são de demonstração.
 */
@Composable
fun VerbumFooter() {
    Row(modifier = Modifier.padding(top = 8.dp)) {
        Text(
            text = "Verbum",
            style = MaterialTheme.typography.labelSmall,
            color = VerbumOnSurfaceLow.copy(alpha = 0.55f),
            fontWeight = FontWeight.Medium
        )
        Text(
            text = "  ·  dados de demonstração",
            style = MaterialTheme.typography.labelSmall,
            color = VerbumOnSurfaceLow.copy(alpha = 0.35f)
        )
    }
}
