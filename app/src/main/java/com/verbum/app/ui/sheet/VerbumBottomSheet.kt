package com.verbum.app.ui.sheet

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.verbum.app.ui.sheet.components.DefinitionContent
import com.verbum.app.ui.sheet.components.ErrorContent
import com.verbum.app.ui.sheet.components.ShimmerLoadingContent
import com.verbum.app.ui.sheet.components.TranslationContent
import com.verbum.app.ui.theme.VerbumBackground
import com.verbum.app.ui.theme.VerbumDragHandle
import com.verbum.app.ui.theme.VerbumOnBackground
import com.verbum.app.ui.viewmodel.VerbumUiState
import com.verbum.app.ui.viewmodel.VerbumViewModel

/**
 * Bottom Sheet principal do Verbum.
 *
 * Exibe o resultado da consulta (definição ou tradução) em um [ModalBottomSheet]
 * que flutua sobre o app atual. A transparência da Activity garante que
 * apenas este sheet seja visível.
 *
 * Transições de estado são animadas com [AnimatedContent] usando fade in/out.
 *
 * @param viewModel ViewModel que fornece o [VerbumUiState].
 * @param onDismiss Callback chamado quando o usuário dispensa o sheet (swipe ou toque fora).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerbumBottomSheet(
    viewModel: VerbumViewModel,
    onDismiss: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = VerbumBackground,
        contentColor = VerbumOnBackground,
        // O scrim dimeia o app por baixo — a Activity transparente permite que
        // este dim funcione corretamente sobre o conteúdo anterior.
        scrimColor = Color.Black.copy(alpha = 0.55f),
        tonalElevation = 0.dp,
        dragHandle = {
            // Drag handle customizado, mais elegante que o padrão
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 14.dp, bottom = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    modifier = Modifier
                        .width(40.dp)
                        .height(4.dp),
                    color = VerbumDragHandle,
                    shape = MaterialTheme.shapes.small
                ) {}
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(bottom = 36.dp)
        ) {
            AnimatedContent(
                targetState = uiState,
                transitionSpec = {
                    fadeIn(animationSpec = tween(durationMillis = 280)) togetherWith
                        fadeOut(animationSpec = tween(durationMillis = 180))
                },
                label = "VerbumStateTransition"
            ) { state ->
                when (state) {
                    is VerbumUiState.Loading ->
                        ShimmerLoadingContent()

                    is VerbumUiState.DefinitionSuccess ->
                        DefinitionContent(result = state.result)

                    is VerbumUiState.TranslationSuccess ->
                        TranslationContent(result = state.result)

                    is VerbumUiState.Error ->
                        ErrorContent(
                            message = state.message,
                            onRetry = { viewModel.retry() }
                        )
                }
            }
        }
    }
}
