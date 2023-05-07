package com.humaininc.gestionprojets

import com.humaininc.gestionprojets.dao.ProjetDAO
import com.humaininc.gestionprojets.modele.Projet
import com.humaininc.gestionprojets.service.ServiceBD
import com.humaininc.gestionprojets.utils.FabriqueCelluleTableBouton
import com.humaininc.gestionprojets.utils.PropertyValueFactoryStringAgregeeLectureSeule
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory

/**
 * Controleur qui liste les projets. Il est aussi responsable de lancer les opérations d'ouverture et de
 * suppression de projet
 *
 * @param contexte le contexte d'exécution du système
 * @author Alexandre
 * @since 06/05/2023
 */
class ControleurListerProjets : ControleurAbstrait() {

    @FXML
    private lateinit var afficherProjetsClos: CheckBox

    @FXML
    private lateinit var listeProjets: TableView<Projet>

    @FXML
    private lateinit var nomsProjet: TableColumn<Projet, String>

    @FXML
    private lateinit var createurs: TableColumn<Projet, String>

    @FXML
    private lateinit var datesDebut: TableColumn<Projet, String>

    @FXML
    private lateinit var datesFin: TableColumn<Projet, String>

    @FXML
    private lateinit var colOuvrir: TableColumn<Projet, Button>

    @FXML
    private lateinit var colSupprimer: TableColumn<Projet, Button>

    private lateinit var projets: ObservableList<Projet>

    @FXML
    fun initialize() {
        // TODO: charger uniquement les projets où l'utilisateur a les droits d'acces
        projets = FXCollections.observableList(
            ProjetDAO(
                contexte.services.getService<ServiceBD>() as ServiceBD
            ).chargerTout()
        )

        nomsProjet.cellValueFactory = PropertyValueFactory("nomProjet")
        createurs.cellValueFactory = PropertyValueFactoryStringAgregeeLectureSeule("createur.nom")
        datesDebut.cellValueFactory = PropertyValueFactory("dateDebut")
        datesFin.cellValueFactory = PropertyValueFactory("dateFin")
        colOuvrir.cellFactory = FabriqueCelluleTableBouton<Projet> ("Ouvrir") {event ->
            run {
                println((event.source as Button).userData)
            }
        }
        colSupprimer.cellFactory = FabriqueCelluleTableBouton<Projet> ("Supprimer") {event ->
            run {
                println((event.source as Button).userData)
            }
        }

        listeProjets.items.setAll(projets)
    }
}