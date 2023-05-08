package com.humaininc.gestionprojets

import com.humaininc.gestionprojets.modele.Utilisateur
import com.humaininc.gestionprojets.service.ConteneurService
import javafx.stage.Stage

/**
 * Définit des données propre au contexte d'exécution du système
 *
 * @author Alexandre
 * @since 06/05/2023
 */
class Contexte(stage: Stage) {
    val services : ConteneurService = ConteneurService()
    var utilisateurConnecte : Utilisateur? = Utilisateur(1, "Bob")
    var stage: Stage = stage
}