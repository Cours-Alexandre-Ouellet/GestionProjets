package com.humaininc.gestionprojets.utils

import javafx.scene.Node
import javafx.scene.layout.GridPane

/**
 * Réécrit les indices de ligne pour qu'ils soient consécutifs
 */
object ReindicageLigneGridPane {

    fun reindicerLigne(panneau : GridPane, premierIndice : Int = 0) {
        if(panneau.children.isEmpty()) {
            return
        }

        val infoEnfants = panneau.children.map { e -> Triple(e, GridPane.getRowIndex(e), GridPane.getColumnIndex(e))}
        infoEnfants.sortedBy { e -> e.second }      // Tri croissant par ligne
        panneau.children.clear()

        // On réajoute les enfants de façon séquentielle
        var ligne = -1
        var precedent : Triple<Node, Int, Int>? = null

        for(element in infoEnfants) {
            if(precedent == null || element.second != precedent.second) {
                ligne++
            }

            panneau.add(element.first, element.third, ligne)
            precedent = element
        }
    }

}