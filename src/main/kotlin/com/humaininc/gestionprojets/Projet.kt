package com.humaininc.gestionprojets

import java.time.LocalDate
import java.util.*

/**
 * Représente un projet qui peut comporter des tâches et qui peut être sauvegardé en BD.
 *
 * @author Alexandre
 * @since 06/05/2023
 */
class Projet(id : Int, nomProjet : String, dateDebut : LocalDate, dateFin : LocalDate, descriptionProjet : String) {

    /**
     * Identifiant assigne en BD pour le projet
     */
    private var id : Int = id

    /**
     * Nom du projet. Il permet d'identifier uniquement un projet
     */
    private var nomProjet : String = nomProjet

    /**
     * Date de début du projet
     */
    private var dateDebut : LocalDate = dateDebut

    /**
     * Date de fin du projet
     */
    private var dateFin : LocalDate = dateFin

    /**
     * Desription textuelle du projet
     */
    private var descriptionProjet : String = descriptionProjet


}