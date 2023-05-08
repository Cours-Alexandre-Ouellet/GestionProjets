package com.humaininc.gestionprojets;

import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

/**
 * Controleur abstrait commun aux différents controleurs du système.
 *
 * @author Alexandre
 * @since 06/05/2023
 */
abstract class ControleurAbstrait {

    /**
     * Contexte d'exécution du système.
     */
    protected var contexte: Contexte = Contexte()

    /**
     * Initialiser le contexte du d'exécution.
     */
    fun initialiser(contexte: Contexte) {
        this.contexte = contexte
    }

    /**
     * Charge une vue FXML et l'affiche dans la scène.
     *
     * @param stage le stage dans lequel faire l'affichage.
     * @param chemin le chemin duquel charger la vue.
     * @param nomFenetre le nom de la fenêtre après le rechargement. Si null, le nom n'est pas changé.
     */
    protected fun chargerVue(stage: Stage, chemin: String, nomFenetre: String? = null) {
        val chargeur = FXMLLoader(ControleurAbstrait::class.java.getResource("creer_projet.fxml"))
        val parent: Parent = chargeur.load()

        chargeur.getController<ControleurAbstrait>().initialiser(Contexte())

        if (nomFenetre != null) {
            stage.title = nomFenetre
        }
        stage.scene = Scene(parent, 800.0, 800.0)
        stage.show()
    }

}
