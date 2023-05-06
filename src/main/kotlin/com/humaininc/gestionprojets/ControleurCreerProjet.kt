package com.humaininc.gestionprojets

import javafx.fxml.FXML
import javafx.scene.control.DatePicker
import javafx.scene.control.TextArea
import javafx.scene.control.TextField

/**
 * Contrôleur responsable de la logique de création du projet. Lit les données de l'interface creer_projet
 * et lance les opérations sur la BD.
 *
 * Le créateur du projet est le compte connecté.
 *
 * @param utilisateurConnecte utilisateur qui est actuellement connecte au système.
 * @author Alexandre
 * @since 06/05/2023
 */
class ControleurCreerProjet : ControleurAbstrait() {

    /**
     * Zone de texte pour saisir le nom du projet.
     */
    @FXML  private lateinit var nomProjet : TextField

    /**
     * Contrôle de sélection de la date de début.
     */
    @FXML private lateinit var dateDebut : DatePicker

    /**
     * Contrôle de sélection de la date de fin.
     */
    @FXML private lateinit var dateFin : DatePicker

    /**
     * Zone de texte de la description du projet.
     */
    @FXML private lateinit var descriptionProjet : TextArea

    init {
        this.utilisateurConnecte = Utilisateur(1, "Bob")
    }

    @FXML private fun creerProjet() {
        val projet = Projet(0, nomProjet.text, dateDebut.value, dateFin.value, descriptionProjet.text)
        println(projet)
    }

    @FXML private fun annuler() {
        // TODO: revenir à l'interface précédente
    }

}