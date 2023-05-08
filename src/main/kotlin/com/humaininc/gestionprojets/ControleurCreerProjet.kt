package com.humaininc.gestionprojets

import com.humaininc.gestionprojets.dao.ProjetDAO
import com.humaininc.gestionprojets.modele.Projet
import com.humaininc.gestionprojets.modele.Utilisateur
import com.humaininc.gestionprojets.service.ConteneurService
import com.humaininc.gestionprojets.service.ServiceBD
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.fxml.FXML
import javafx.scene.control.*
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

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
class ControleurCreerProjet(contexte: Contexte) : ControleurAbstrait(contexte) {

    /**
     * Définit les messages de l'interface.
     */
    companion object {

        private const val MESSAGE_NOM_PROJET_NULL: String = "Un nom de projet doit être saisi."
        private const val MESSAGE_DATE_DEBUT_NULL: String = "Une date de début de projet doit être saisi."
        private const val MESSAGE_DATE_FIN_NULL: String = "Une date de fin de projet doit être saisi."

        /**
         * Format de date attendu dans le système.
         */
        private const val FORMAT_DATE: String = "YYYY-MM-DD"

        /**
         * Message d'erreur pour le nom de projet trop court.
         */
        private const val MESSAGE_NOM_PROJET_COURT: String = "Le nom du projet doit comporter au moins 3 caractères."

        /**
         * Message d'erreur pour la date de fin avant la date de début.
         */
        private const val MESSAGE_DATE_FIN_AVANT_DATE_DEBUT: String = "La date de fin doit être après la date de début."

        /**
         * Message d'erreur pour le format de la date.
         */
        private const val MESSAGE_FORMAT_DATE_INVALIDE: String = "La date doit être entrée dans le format AAAA-MM-JJ"
    }

    /**
     * Zone de texte pour saisir le nom du projet.
     */
    @FXML
    private lateinit var nomProjet: TextField

    /**
     * Message d'erreur pour le nom du projet
     */
    @FXML
    private lateinit var erreurNomProjet: Label

    /**
     * Contrôle de sélection de la date de début.
     */
    @FXML
    private lateinit var dateDebut: DatePicker

    /**
     * Message d'erreur de la date de début.
     */
    @FXML
    private lateinit var erreurDateDebut: Label

    /**
     * Contrôle de sélection de la date de fin.
     */
    @FXML
    private lateinit var dateFin: DatePicker

    /**
     * Message d'erreur pour la date de fin
     */
    @FXML
    private lateinit var erreurDateFin: Label

    /**
     * Zone de texte de la description du projet.
     */
    @FXML
    private lateinit var descriptionProjet: TextArea

    /**
     * Bouton de soumission du formulaire
     */
    @FXML
    private lateinit var boutonSoumission: Button

    /**
     * Fonction d'initilisation des champs de l'objet après la génération de l'interface
     */
    @FXML
    private fun initialize() {
        // Ajout les listener pour la validation des données
        nomProjet.focusedProperty().addListener { _: ObservableValue<out Boolean>?, _: Boolean?, new: Boolean? ->
            run {
                if (!new!!) {
                    validerNomProjet()
                }
            }
        }
        dateDebut.focusedProperty().addListener { _: ObservableValue<out Boolean>?, _: Boolean?, new: Boolean? ->
            run {
                if (!new!!) {
                    validerDateDebut()
                }
            }
        }
        dateFin.focusedProperty().addListener { _: ObservableValue<out Boolean>?, _: Boolean?, new: Boolean? ->
            run {
                if (!new!!) {
                    validerDateFin()
                }
            }
        }
    }

    /**
     * Crée un nouveau projet et l'enregistre dans la base de données.
     */
    @FXML
    private fun creerProjet() {
        // La fonction and évite une évaluation du circuit le plus court et permet d'afficher tous les messages d'erreur
        if (!(validerNomProjet() and validerDateDebut() and validerDateFin())) {
            return
        }
        actualiserEtatBoutonSoumission()

        val projet =
            Projet(null, nomProjet.text, dateDebut.value, dateFin.value, descriptionProjet.text, contexte.utilisateurConnecte!!, true)

        ProjetDAO(contexte.services.getService<ServiceBD>() as ServiceBD).enregistrer(projet)
    }

    /**
     * Annule la création du projet
     */
    @FXML
    private fun annuler() {
        // TODO: revenir à l'interface précédente
    }

    /**
     * Vérifie si la date, sous forme de chaîne de caractères, correspond au format de date prescrit par le système
     *
     * @param date la date à valider en format de chaîne de caractère.
     * @return un booléen indiquant si la date est dans le format valide ou non.
     */
    private fun dateFormatValide(date: String): Boolean {
        try {
            DateTimeFormatter.ofPattern(FORMAT_DATE).parse(date)
        } catch (exception: DateTimeParseException) {
            return false
        }

        return true
    }

    /**
     * Valide le nom du projet lorsque le focus est perdu sur le contrôle de nom.
     *
     * @return true si le nom est valide, false autrement
     */
    private fun validerNomProjet(): Boolean {
        erreurNomProjet.text =
            when {
                nomProjet.text == null -> {
                    MESSAGE_NOM_PROJET_NULL
                }
                nomProjet.text.length < 3 -> {
                    MESSAGE_NOM_PROJET_COURT
                }
                false -> {
                    null
                }
                else -> {
                    null
                }
            }

        actualiserEtatBoutonSoumission()
        return erreurNomProjet.text == null
    }

    /**
     * Valide la date de début lorsque le focus est perdu sur le contrôle de la date de début.
     *
     * @return true si la date est valide, false autrement
     */
    private fun validerDateDebut(): Boolean {
        erreurDateDebut.text =
            when {
                dateDebut.value == null -> {
                    MESSAGE_DATE_DEBUT_NULL
                }
                !dateFormatValide(dateDebut.editor.text) -> {
                    MESSAGE_FORMAT_DATE_INVALIDE
                }
                else -> {
                    null
                }
            }

        actualiserEtatBoutonSoumission()
        return erreurDateDebut.text == null
    }

    /**
     * Valide la date de fin lorsque le focus est perdu sur le contrôle de la date de fin.
     *
     * @return true si la date est valide, false autrement
     */
    private fun validerDateFin(): Boolean {
        erreurDateFin.text =
            when {
                dateFin.value == null -> {
                    MESSAGE_DATE_FIN_NULL
                }
                !dateFormatValide(dateFin.editor.text) -> {
                    MESSAGE_FORMAT_DATE_INVALIDE
                }
                dateDebut.value != null && dateFin.value <= dateDebut.value -> {
                    MESSAGE_DATE_FIN_AVANT_DATE_DEBUT
                }
                else -> {
                    null
                }
            }

        actualiserEtatBoutonSoumission()
        return erreurDateFin.text == null
    }

    /**
     * Désactive le bouton si l'un des champs obligatoires contient une erreur ou que sa valeur est nulle
     */
    private fun actualiserEtatBoutonSoumission() {
        boutonSoumission.isDisable = erreurNomProjet.text != null || erreurDateDebut.text != null
            erreurDateFin.text !=  null || nomProjet.text == null || dateDebut.value == null || dateFin.value == null
    }
}
