package com.humaininc.gestionprojets

import com.humaininc.gestionprojets.dao.UtilisateurDAO
import com.humaininc.gestionprojets.modele.EtatTache
import com.humaininc.gestionprojets.modele.Utilisateur
import com.humaininc.gestionprojets.service.ServiceBD
import com.humaininc.gestionprojets.utils.ReindicageLigneGridPane
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import javafx.scene.text.Text

/**
 * Contrôleur responsable de la création de tâche. Il reçoit le [contexte] d'application en paramètre.
 */
class ControleurCreerTache(contexte: Contexte) : ControleurAbstrait(contexte) {

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

    private fun annuler() {

    }

    private fun creerTache() {

    }

    /**
     * Crée des blocs de contrôle pour la sélection de personnes affectées.
     */
    internal class FabriqueLigneAffectation(private val contexte: Contexte,
                                            private val actionRetrait : (Int) -> Unit) {

        fun creerLigneAffectationInitiale() : Pair<Node, Node> {
            return Pair(creerControleSelection(), Label())
        }

        fun creerLigneAffectation() : Pair<Node, Node> {
            val boutonRetrait = Button("Retirer la personne")
            boutonRetrait.onAction = EventHandler { evenement -> actionRetrait(GridPane.getRowIndex(evenement.target as Node))}
            return Pair(creerControleSelection(), boutonRetrait)
        }

        fun creerControleSelection() : Node {
            return ChoiceBox(FXCollections.observableList(
                UtilisateurDAO(contexte.services.getService<ServiceBD>() as ServiceBD).chargerTout()
            ))
        }

    }
}