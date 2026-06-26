package com.verbum.app.ui.sheet.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.verbum.app.ui.theme.VerbumSurface
import com.verbum.app.ui.theme.VerbumSurfaceElevated
import com.verbum.app.ui.theme.VerbumSurfaceVariant

/**
 * Caixa com efeito shimmer para indicar carregamento.
 *
 * Anima um gradiente horizontal da esquerda para a direita,
 * simulando a aparência de dados sendo carregados.
 *
 * @param modifier  Modificador Compose para layout customizado.
 * @param height    Altura da caixa de shimmer.
 * @param cornerRadius Raio dos cantos arredondados.
 */
@Composable
fun ShimmerBox(
    modifier: Modifier = Modifier,
    height: Dp = 16.dp,
    cornerRadius: Dp = 8.dp
) {
    val shimmerColors = listOf(
        VerbumSurface,
        VerbumSurfaceElevated,
        VerbumSurfaceVariant,
        VerbumSurfaceElevated,
        VerbumSurface,
    )

    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1200f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerTranslate"
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim - 400f, 0f),
        end = Offset(translateAnim, 0f)
    )

    Box(
        modifier = modifier
            .height(height)
            .clip(RoundedCornerShape(cornerRadius))
            .background(brush)
    )
}

/**
 * Conteúdo completo de shimmer que imita a estrutura do [DefinitionContent],
 * exibido enquanto os dados estão sendo carregados.
 */
@Composable
fun ShimmerLoadingContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Simula a linha da palavra
        ShimmerBox(
            modifier = Modifier.fillMaxWidth(0.55f),
            height = 34.dp,
            cornerRadius = 8.dp
        )

        // Simula a fonética
        ShimmerBox(
            modifier = Modifier.fillMaxWidth(0.30f),
            height = 13.dp,
            cornerRadius = 6.dp
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Simula o chip de classe gramatical
        ShimmerBox(
            modifier = Modifier.width(80.dp),
            height = 26.dp,
            cornerRadius = 13.dp
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Simula 3 linhas de definição
        ShimmerBox(modifier = Modifier.fillMaxWidth(), height = 14.dp)
        ShimmerBox(modifier = Modifier.fillMaxWidth(), height = 14.dp)
        ShimmerBox(modifier = Modifier.fillMaxWidth(0.75f), height = 14.dp)

        Spacer(modifier = Modifier.height(6.dp))

        // Simula o card de exemplo
        ShimmerBox(
            modifier = Modifier.fillMaxWidth(),
            height = 56.dp,
            cornerRadius = 12.dp
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Simula os chips de sinônimos
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            repeat(4) {
                ShimmerBox(
                    modifier = Modifier.width(72.dp),
                    height = 30.dp,
                    cornerRadius = 15.dp
                )
            }
        }
    }
}
