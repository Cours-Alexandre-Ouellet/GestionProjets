package com.humaininc.gestionprojets

import javafx.fxml.FXML

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

   

    @FXML private fun creerProjet() {

    }

    @FXML private fun annuler() {
        // TODO: revenir à l'interface précédente
    }

}