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
        val chargeur = FXMLLoader(GestionProjetApp::class.java.getResource("lister_projets.fxml"))
        chargeur.setController(ControleurListerProjets(Contexte(stage)))
        val parent : Parent = chargeur.load()


        stage.title = "Gestion projets"
        stage.scene = Scene(parent, 800.0, 800.0)
        stage.show()
    }

}