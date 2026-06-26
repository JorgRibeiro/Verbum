package com.verbum.app.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.verbum.app.data.di.ServiceLocator
import com.verbum.app.ui.sheet.VerbumBottomSheet
import com.verbum.app.ui.theme.VerbumTheme
import com.verbum.app.ui.viewmodel.VerbumAction
import com.verbum.app.ui.viewmodel.VerbumViewModel
import com.verbum.app.ui.viewmodel.VerbumViewModelFactory

/**
 * Activity transparente que responde à ação **"Verbum: Traduzir"** no menu de seleção de texto.
 *
 * Fluxo:
 * 1. Android dispara [Intent.ACTION_PROCESS_TEXT] com o texto selecionado
 * 2. Esta Activity captura o texto via [Intent.EXTRA_PROCESS_TEXT]
 * 3. Delega ao [VerbumViewModel] com [VerbumAction.Translate]
 * 4. Renderiza o [VerbumBottomSheet] sobre o app atual (graças ao tema transparente)
 * 5. Finaliza quando o usuário desliza o Bottom Sheet para baixo
 *
 * Idêntica ao [DefineActivity] em estrutura — a diferença está na [VerbumAction]
 * passada ao ViewModel, que determina o caso de uso a ser executado.
 */
class TranslateActivity : ComponentActivity() {

    private val viewModel: VerbumViewModel by viewModels {
        VerbumViewModelFactory(
            dictionaryRepository = ServiceLocator.getDictionaryRepository(),
            translatorRepository = ServiceLocator.getTranslatorRepository()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selectedText = intent
            .getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)
            ?.toString()
            ?.trim()
            .orEmpty()

        viewModel.process(VerbumAction.Translate, selectedText)

        setContent {
            VerbumTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                ) {
                    VerbumBottomSheet(
                        viewModel = viewModel,
                        onDismiss = ::finish
                    )
                }
            }
        }
    }
}
