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
    @Suppress("UNCHECKED_CAST")
    fun <T : ControleurAbstrait> initialiser(contexte: Contexte, init: ((controleur: T) -> Nothing)? = null) {
        this.contexte = contexte

        init?.invoke(this as T)
    }

    /**
     * Charge une vue FXML et l'affiche dans la scène.
     *
     * @param stage le stage dans lequel faire l'affichage.
     * @param cheminVue le chemin duquel charger la vue.
     * @param nomFenetre le nom de la fenêtre après le rechargement. Si null, le nom n'est pas changé.
     * @param init fonction qui s'exécute sur le contrôleur avant l'affichage de la fenêtre.
     */
    protected fun <T : ControleurAbstrait> chargerVue(stage: Stage, cheminVue: String, nomFenetre: String? = null, init: ((controleur: T) -> Nothing)? = null) {
        val chargeur = FXMLLoader(ControleurAbstrait::class.java.getResource(cheminVue))
        val parent: Parent = chargeur.load()
        val controleur: ControleurAbstrait = chargeur.getController()
        controleur.initialiser(Contexte(), init)

        if (nomFenetre != null) {
            stage.title = nomFenetre
        }
        stage.scene = Scene(parent, 800.0, 800.0)
        stage.show()
    }

}
