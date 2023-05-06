package com.humaininc.gestionprojets

import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.fxml.FXML
import javafx.scene.control.DatePicker
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
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
class ControleurCreerProjet : ControleurAbstrait() {

    private val FORMAT_DATE : String = "YYYY-MM-DD"

    /**
     * Message d'erreur pour le nom de projet trop court.
     */
    private val MESSAGE_NOM_PROJET_COURT: String = "Le nom du projet doit comporter au moins 3 caractères."

    /**
     * Message d'erreur pour la date de fin avant la date de début.
     */
    private val MESSAGE_DATE_FIN_AVANT_DATE_DEBUT : String = "La date de fin doit être après la date de début."

    /**
     * Message d'erreur pour le format de la date.
     */
    private val MESSAGE_FORMAT_DATE_INVALIDE : String = "La date doit être entrée dans le format AAAA-MM-JJ"

    /**
     * Zone de texte pour saisir le nom du projet.
     */
    @FXML private lateinit var nomProjet: TextField

    /**
     * Message d'erreur pour le nom du projet
     */
    @FXML private lateinit var erreurNomProjet: Label

    /**
     * Contrôle de sélection de la date de début.
     */
    @FXML private lateinit var dateDebut: DatePicker

    /**
     * Message d'erreur de la date de début.
     */
    @FXML private lateinit var erreurDateDebut: Label

    /**
     * Contrôle de sélection de la date de fin.
     */
    @FXML private lateinit var dateFin: DatePicker

    /**
     * Message d'erreur pour la date de fin
     */
    @FXML private lateinit var erreurDateFin : Label

    /**
     * Zone de texte de la description du projet.
     */
    @FXML private lateinit var descriptionProjet: TextArea

    /**
     * Constructeur
     * TODO : lire l'utilisateur connecté du controleur précédent
     */
    init {
        this.utilisateurConnecte = Utilisateur(1, "Bob")
    }

    /**
     * Fonction d'initilisation des champs de l'objet après la génération de l'interface
     */
    @FXML private fun initialize() {
        // Ajout les listener pour la validation des données
        nomProjet.focusedProperty().addListener(ValiderNomProjet())
        dateDebut.focusedProperty().addListener(ValiderDateDebut())
        dateFin.focusedProperty().addListener(ValiderDateFin())
    }

    /**
     * Crée un nouveau projet et l'enregistre dans la base de données.
     */
    @FXML
    private fun creerProjet() {
        val projet = Projet(0, nomProjet.text, dateDebut.value, dateFin.value, descriptionProjet.text)
        println(projet)
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
     */
    private fun dateFormatValide(date: String) : Boolean {
        try {
            DateTimeFormatter.ofPattern(FORMAT_DATE).parse(date)
        } catch (exception : DateTimeParseException){
            return false
        }

        return true
    }

    /**
     * Valide le nom du projet lorsque le focus est perdu sur le contrôle de nom.
     */
    inner class ValiderNomProjet : ChangeListener<Boolean> {
        override fun changed(observable: ObservableValue<out Boolean>?, oldValue: Boolean?, newValue: Boolean?) {

            if (!newValue!!) {
                if (nomProjet.text.length < 3) {
                    erreurNomProjet.text = MESSAGE_NOM_PROJET_COURT
                } else if (false) {
                    // TODO: Tester nom unique
                } else {
                    erreurNomProjet.text = null
                }
            }
        }
    }

    /**
     * Valide la date de début lorsque le focus est perdu sur le contrôle de la date de début.
     */
    inner class ValiderDateDebut : ChangeListener<Boolean> {
        override fun changed(observable: ObservableValue<out Boolean>?, oldValue: Boolean?, newValue: Boolean?) {
            if (!newValue!!) {
                if (!dateFormatValide(dateDebut.editor.text)){
                    erreurDateDebut.text = MESSAGE_FORMAT_DATE_INVALIDE
                }  else {
                    erreurDateDebut.text = null
                }
            }
        }
    }

    /**
     * Valide la date de fin lorsque le focus est perdu sur le contrôle de la date de fin.
     */
    inner class ValiderDateFin : ChangeListener<Boolean> {
        override fun changed(observable: ObservableValue<out Boolean>?, oldValue: Boolean?, newValue: Boolean?) {
            if (!newValue!!) {
                if (!dateFormatValide(dateFin.editor.text)){
                    erreurDateFin.text = MESSAGE_FORMAT_DATE_INVALIDE
                }
                else if (dateDebut.value != null && dateFin.value != null && dateFin.value <= dateDebut.value) {
                    erreurDateFin.text = MESSAGE_DATE_FIN_AVANT_DATE_DEBUT
                }
                else {
                    erreurDateFin.text = null
                }
            }
        }
    }
}
