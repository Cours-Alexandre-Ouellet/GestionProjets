package com.humaininc.gestionprojets

import javafx.application.Application

/**
 * Point d'entré du système
 * @param args les arguments passés en ligne de commandes au système. Actuellement inutilisé.
 * @author Alexandre
 * @since 06/05/2023
 */
fun main(@Suppress("UNUSED_PARAMETER") args: Array<String>) {
    Application.launch(GestionProjetApp::class.java, *args)
}