package com.verbum.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.verbum.app.ui.theme.*

/**
 * Tela principal do Verbum — aparece quando o usuário abre o app pelo launcher.
 *
 * Exibe instruções claras de como usar o Verbum via menu de seleção de texto
 * e apresenta palavras de exemplo para testar as funcionalidades.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            com.verbum.app.ui.theme.VerbumTheme {
                VerbumWelcomeScreen()
            }
        }
    }
}

@Composable
fun VerbumWelcomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VerbumBackground)
    ) {
        // Gradient de fundo sutil no topo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            VerbumIndigo.copy(alpha = 0.12f),
                            Color.Transparent
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .systemBarsPadding()
                .padding(horizontal = 28.dp)
                .padding(top = 56.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ── Logo / Header ─────────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(VerbumIndigo, VerbumIndigoDark)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "V",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Verbum",
                style = MaterialTheme.typography.headlineLarge,
                color = VerbumOnBackground,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Dicionário e tradutor contextual",
                style = MaterialTheme.typography.bodyMedium,
                color = VerbumOnSurfaceMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            // ── Como usar ─────────────────────────────────────────────────────
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = VerbumSurface)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        text = "COMO USAR",
                        style = MaterialTheme.typography.titleMedium,
                        color = VerbumOnSurfaceLow
                    )

                    InstructionStep(
                        number = "1",
                        text = "Abra qualquer app com texto",
                        color = VerbumIndigo
                    )
                    InstructionStep(
                        number = "2",
                        text = "Selecione uma palavra ou frase",
                        color = VerbumIndigo
                    )
                    InstructionStep(
                        number = "3",
                        text = "Toque em Verbum: Definir ou Verbum: Traduzir",
                        color = VerbumIndigo
                    )
                    InstructionStep(
                        number = "4",
                        text = "Veja o resultado sem sair do app!",
                        color = VerbumTeal
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Palavras de exemplo ───────────────────────────────────────────
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = VerbumSurface)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "TENTE COM ESTAS PALAVRAS",
                        style = MaterialTheme.typography.titleMedium,
                        color = VerbumOnSurfaceLow
                    )

                    Text(
                        text = "Copie uma das palavras abaixo, vá a outro app e selecione-a para testar o Verbum:",
                        style = MaterialTheme.typography.bodySmall,
                        color = VerbumOnSurfaceMedium,
                        lineHeight = 18.sp
                    )

                    val exampleWords = listOf(
                        "ephemeral" to "Definir (EN)",
                        "serendipity" to "Definir (EN)",
                        "saudade" to "Definir (PT)",
                        "resilience" to "Definir (EN)",
                        "love" to "Traduzir (EN→PT)",
                        "saudade" to "Traduzir (PT→EN)"
                    )

                    exampleWords.forEach { (word, hint) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(VerbumSurfaceVariant)
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = word,
                                style = MaterialTheme.typography.bodyMedium,
                                color = VerbumOnSurface,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = hint,
                                style = MaterialTheme.typography.labelSmall,
                                color = VerbumOnSurfaceLow
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ── Footer ────────────────────────────────────────────────────────
            Text(
                text = "Verbum · v1.0 · dados de demonstração",
                style = MaterialTheme.typography.labelSmall,
                color = VerbumOnSurfaceLow.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
private fun InstructionStep(number: String, text: String, color: Color) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number,
                style = MaterialTheme.typography.labelMedium,
                color = color,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = VerbumOnSurface
        )
    }
}
