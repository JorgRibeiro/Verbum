package com.verbum.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

/**
 * Esquema de cores escuro do Verbum, mapeando a paleta personalizada
 * para os papéis semânticos do Material3.
 */
private val VerbumDarkColorScheme = darkColorScheme(
    primary             = VerbumIndigo,
    onPrimary           = VerbumOnBackground,
    primaryContainer    = VerbumSurfaceVariant,
    onPrimaryContainer  = VerbumIndigoLight,

    secondary             = VerbumTeal,
    onSecondary           = VerbumBackground,
    secondaryContainer    = VerbumSecondaryContainer,
    onSecondaryContainer  = VerbumTeal,

    background    = VerbumBackground,
    onBackground  = VerbumOnBackground,

    surface          = VerbumSurface,
    onSurface        = VerbumOnSurface,
    surfaceVariant   = VerbumSurfaceVariant,
    onSurfaceVariant = VerbumOnSurfaceMedium,

    error    = VerbumError,
    onError  = VerbumBackground,

    outline        = VerbumDivider,
    outlineVariant = VerbumDragHandle,
)

/**
 * Tema principal do Verbum.
 *
 * Aplica o esquema de cores escuro, a tipografia customizada e as formas
 * padrão do Material3 para todo o conteúdo dentro deste composable.
 *
 * Nota: Não define tema claro intencional — o Verbum é primariamente um
 * app de overlay sobre outros apps, otimizado para modo escuro e contextos
 * de leitura.
 */
@Composable
fun VerbumTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = VerbumDarkColorScheme,
        typography  = VerbumTypography,
        content     = content
    )
}
