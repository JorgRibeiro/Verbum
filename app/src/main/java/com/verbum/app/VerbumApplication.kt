package com.verbum.app

import android.app.Application

/**
 * Classe Application do Verbum.
 *
 * Ponto de entrada da aplicação. Responsável por inicializações globais.
 *
 * Em versões futuras, este é o lugar certo para:
 * - Configurar o Timber para logging: `Timber.plant(Timber.DebugTree())`
 * - Inicializar o banco de dados Room
 * - Configurar frameworks de DI (Hilt, Koin)
 * - Inicializar SDKs de analytics/crash reporting
 */
class VerbumApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Inicializações futuras aqui
    }
}
