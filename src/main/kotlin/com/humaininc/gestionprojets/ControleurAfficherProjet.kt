package com.humaininc.gestionprojets

import com.humaininc.gestionprojets.modele.Projet
import javafx.fxml.FXML
import javafx.scene.control.Label

/**
 * Gère l'affichage des caractéristiques d'un projet
 *
 * @author Alexandre
 * @since 07/05/2023
 */
class ControleurAfficherProjet(contexte: Contexte, projet: Projet) : ControleurAbstrait(contexte) {

    private val projet: Projet = projet

    @FXML
    private lateinit var nomProjet: Label

    @FXML
    private fun initialize() {
        nomProjet.text = projet.nomProjet
    }
}