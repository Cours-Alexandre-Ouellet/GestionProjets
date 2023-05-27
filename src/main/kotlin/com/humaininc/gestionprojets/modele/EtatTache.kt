package com.humaininc.gestionprojets.modele

import com.humaininc.gestionprojets.dao.Entite

/**
 * État d'une tâche dans le système. La progression des tâches est définie par cet objet.
 *
 * Les états comportent un [nom] les identifiant de façon unique dans un projet et un [ordre]
 * indiquant dans quel ordre les tâches du projet progresse. Chaque ordre doit aussi être unique
 * par projet.
 */
class EtatTache(id : Int?, var nom : String, var ordre : Int) : Entite(id) {
}