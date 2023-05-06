package com.humaininc.gestionprojets

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

/**
 * Génère l'application JavaFX qui permet le lancement du projet.
 *
 * @author Alexandre
 * @since 06/05/2023
 */
class GestionProjetApp : Application() {

    override fun start(stage: Stage) {
        val parent : Parent = FXMLLoader.load(GestionProjetApp::class.java.getResource("creer_projet.fxml"))
        stage.title = "Test"
        stage.scene = Scene(parent, 400.0, 200.0)
        stage.show()
    }

}