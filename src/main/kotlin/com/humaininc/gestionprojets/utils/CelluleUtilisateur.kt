package com.humaininc.gestionprojets.utils

import com.humaininc.gestionprojets.modele.Utilisateur
import javafx.scene.control.ListCell
import javafx.scene.text.Text

/**
 * Cellule qui affiche le nom de l'utilisateur dans une liste d'options
 */
class CelluleUtilisateur : ListCell<Utilisateur>() {
    override fun updateItem(item: Utilisateur?, empty: Boolean) {
        super.updateItem(item, empty)

        graphic = if (empty) {
            null
        } else {
            Text(item?.nom)
        }
    }
}