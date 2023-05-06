package com.humaininc.gestionprojets

import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.fxml.FXML
import javafx.scene.control.DatePicker
import javafx.scene.control.Label
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

    private val messageNomProjetCourt: String = "Le nom du projet doit comporter au moins 3 caractères."

    /**
     * Zone de texte pour saisir le nom du projet.
     */
    @FXML  private lateinit var nomProjet : TextField

    /**
     * Message d'erreur pour le nom du projet
     */
    @FXML private lateinit var erreurNomProjet : Label

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

    /**
     * Constructeur
     * TODO : lire l'utilisateur connecté du controleur précédent
     */
    init {
        this.utilisateurConnecte = Utilisateur(1, "Bob")
    }

    @FXML private fun initialize() {
        // Ajout les listener pour la validation des données
        nomProjet.focusedProperty().addListener(ValiderNomProjet())
    }

    /**
     * Crée un nouveau projet et l'enregistre dans la base de données.
     */
    @FXML private fun creerProjet() {
        val projet = Projet(0, nomProjet.text, dateDebut.value, dateFin.value, descriptionProjet.text)
        println(projet)
    }

    /**
     * Annule la création du projet
     */
    @FXML private fun annuler() {
        // TODO: revenir à l'interface précédente
    }


    /**
     * Valide le nom du projet lorsque le focus est perdu sur le contrôle de nom.
     */
    inner class ValiderNomProjet : ChangeListener<Boolean> {
        override fun changed(observable: ObservableValue<out Boolean>?, oldValue: Boolean?, newValue: Boolean?) {

            if(!newValue!!) {
                if(nomProjet.text.length < 3) {
                    erreurNomProjet.text = messageNomProjetCourt
                }
                else if(false) {
                    // TODO: Tester nom unique
                }
                else {
                    erreurNomProjet.text = ""
                }
            }
        }

    }
}
