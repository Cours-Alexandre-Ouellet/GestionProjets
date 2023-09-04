package com.humaininc.gestionprojets

import com.humaininc.gestionprojets.dao.TacheDAO
import com.humaininc.gestionprojets.dao.UtilisateurDAO
import com.humaininc.gestionprojets.modele.EtatTache
import com.humaininc.gestionprojets.modele.Projet
import com.humaininc.gestionprojets.modele.Tache
import com.humaininc.gestionprojets.modele.Utilisateur
import com.humaininc.gestionprojets.service.ServiceBD
import com.humaininc.gestionprojets.utils.CelluleUtilisateur
import com.humaininc.gestionprojets.utils.ReindicageLigneGridPane
import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import javafx.scene.text.Text
import javafx.util.Callback

/**
 * Contrôleur responsable de la création de tâche. Il reçoit le [contexte] d'application en paramètre et le [projet]
 * dans lequel la tâche sera créé
 */
class ControleurCreerTache(contexte: Contexte, val projet : Projet) : ControleurAbstrait(contexte) {

    @FXML
    private lateinit var nomTache : TextField

    @FXML
    private lateinit var erreurNomTache: Text

    @FXML
    private lateinit var  dateRealisationPrevue : DatePicker

    @FXML
    private lateinit var erreurDateRealisationPrevue : Text

    @FXML
    private lateinit var description : TextArea

    @FXML
    private lateinit var personnesAffectees : GridPane

    private var personnesAffecteesChoiceList : MutableList<Node> = mutableListOf()

    @FXML
    private lateinit var boutonAffecterPlus : Button

    @FXML
    private lateinit var etatInitial : ChoiceBox<EtatTache>

    @FXML
    private lateinit var importance : ChoiceBox<Boolean>

    @FXML
    private lateinit var boutonAnnuler : Button

    @FXML
    private lateinit var boutonAjouterTache : Button

    private val fabriqueLigneAffectation : FabriqueLigneAffectation =
        FabriqueLigneAffectation(contexte) { indice -> retirerAffectation(indice) }

    @FXML
    private fun initialize() {
        boutonAffecterPlus.onAction = EventHandler { _ -> affecterPlus() }
        boutonAnnuler.onAction = EventHandler { _ -> annuler() }
        boutonAjouterTache.onAction = EventHandler { _ -> creerTache() }

        // Ajoute la première ligne pour l'affectation
        affecterPlus()
    }

    /**
     * Ajoute une ligne de personne affectée au projet
     */
    private fun affecterPlus() {
        val nouvelleLigne: Pair<Node, Node> = if(personnesAffecteesChoiceList.isEmpty()) {
            fabriqueLigneAffectation.creerLigneAffectationInitiale()
        } else {
            fabriqueLigneAffectation.creerLigneAffectation()
        }

        personnesAffectees.add(nouvelleLigne.first, 0, personnesAffecteesChoiceList.size)
        personnesAffectees.add(nouvelleLigne.second, 1, personnesAffecteesChoiceList.size)

        personnesAffecteesChoiceList.add(nouvelleLigne.first)

    }

    /**
     * Retire une ligne de personne affectée du projet
     */
    private fun retirerAffectation(indice: Int){
        personnesAffectees.children.removeIf { element -> GridPane.getRowIndex(element) == indice }
        personnesAffecteesChoiceList.removeAt(indice)
        ReindicageLigneGridPane.reindicerLigne(personnesAffectees)
    }

    private fun recupererUtilisateursAffectes() : List<Utilisateur> {
        return personnesAffecteesChoiceList.map { affecation ->
                (affecation as ComboBox<Utilisateur>).selectionModel.selectedItem as Utilisateur
        }
    }

    private fun annuler() {

    }

    private fun creerTache() {
        val nouvelleTache = Tache(
            id = null,
            nom = nomTache.text,
            dateRelatisationPrevue = dateRealisationPrevue.value,
            description = description.text,
            projet = projet,
            affectes = recupererUtilisateursAffectes(),
            createur = contexte.utilisateurConnecte!!,
            etat = EtatTache(1, "ETAT TEST", 0),        // TODO: etat par défaut du projet
            importance = true
        )

        TacheDAO(contexte.services.getService<ServiceBD>() as ServiceBD).enregistrer(nouvelleTache)
    }



    /**
     * Crée des blocs de contrôle pour la sélection de personnes affectées.
     */
    internal class FabriqueLigneAffectation(private val contexte: Contexte,
                                            private val actionRetrait : (Int) -> Unit) {
        /*
         * Crée une nouvelle ligne avec un message d'erreur possible pour les utilisateurs affectés. Retourne la zone
         * de sélection et le message d'erreur
         */
        fun creerLigneAffectationInitiale(): Pair<Node, Node> {
            return Pair(creerControleSelection(), Label())
        }

        /**
         * Crée un nouvelle ligne avec un bouton de retrait. Retourne la zone de sélection et le bouton de retrait.
         */
        fun creerLigneAffectation(): Pair<Node, Node> {
            val boutonRetrait = Button("Retirer la personne")
            boutonRetrait.onAction =
                EventHandler { evenement -> actionRetrait(GridPane.getRowIndex(evenement.target as Node)) }
            return Pair(creerControleSelection(), boutonRetrait)
        }

        /**
         * Crée un contrôle de sélection pour un utilisateur à affecter au projet.
         */
        private fun creerControleSelection(): Node {
            val choixUtilisateur = ComboBox(
                FXCollections.observableList(
                    UtilisateurDAO(contexte.services.getService<ServiceBD>() as ServiceBD).chargerTout()        // TODO : charge seulement les autorises au projet
                )
            )

            choixUtilisateur.cellFactory =
                Callback<ListView<Utilisateur>, ListCell<Utilisateur>> { CelluleUtilisateur() }
            choixUtilisateur.buttonCell = CelluleUtilisateur()

            return choixUtilisateur
        }
    }
}