package com.humaininc.gestionprojets.modele

import com.humaininc.gestionprojets.dao.Entite
import java.time.LocalDate

/**
 * Plus petite unité de travail du système. Chaque [Tache] possède un [nom] unique dans son environnement (projet ou groupe
 * de tâche). Les tâches ont une [dateRealisationPrevue] pour indiquer le moment où elles devraient avoir atteint le dernier
 * état de tâche. Le [createur] de la tâche est sauvegardé et immuable. Chaque tâche possède une liste [Utilisateur] qui
 * lui ont été affectés dans la liste [affectes]. La tâche possède un [etat] qui indique sa progression et un niveau d'[importance].
 */
class Tache(id : Int?,
            var nom : String,
            var dateRelatisationPrevue:LocalDate,
            private val createur:Utilisateur,
            var affectes:List<Utilisateur>,
            var etat:EtatTache,
            var importance:Boolean) : Entite(id) {
}