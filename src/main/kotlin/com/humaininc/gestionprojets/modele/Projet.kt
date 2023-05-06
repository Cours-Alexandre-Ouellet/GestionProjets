package com.humaininc.gestionprojets.modele

import com.humaininc.gestionprojets.dao.Entite
import java.time.LocalDate

/**
 * Représente un projet qui peut comporter des tâches et qui peut être sauvegardé en BD.
 *
 * @param id identifiant unique du projet. null Si le projet n'a jamais été enregistré dans la BD.
 * @param nomProjet le nom du projet.
 * @param dateDebut la date de début du projet.
 * @param dateFin la date de la fin du projet.
 * @param descriptionProjet une description complète du projet.
 * @param createur l'utilisateur qui a créé le projet.
 *
 * @author Alexandre
 * @since 06/05/2023
 */
class Projet(id : Int?, nomProjet : String, dateDebut : LocalDate, dateFin : LocalDate,
             descriptionProjet : String, createur : Utilisateur) : Entite(id) {

    /**
     * Nom du projet. Il permet d'identifier uniquement un projet
     */
    var nomProjet : String = nomProjet

    /**
     * Date de début du projet
     */
    var dateDebut : LocalDate = dateDebut

    /**
     * Date de fin du projet
     */
    var dateFin : LocalDate = dateFin

    /**
     * Desription textuelle du projet
     */
    var descriptionProjet : String = descriptionProjet

    /**
     * Identifiant du créateur du projet
     */
    var createur : Utilisateur = createur


}