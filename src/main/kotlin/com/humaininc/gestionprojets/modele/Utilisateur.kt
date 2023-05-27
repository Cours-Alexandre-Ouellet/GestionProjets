package com.humaininc.gestionprojets.modele;

import com.humaininc.gestionprojets.dao.Entite

/**
 * Utilisateur du système.
 *
 * Classe placeholder avec uniquement un nom et un id d'utilisateur. TODO : bonifier la définition de l'utilisateur
 *
 * @author Alexandre
 * @since 06/05/2023
 */
class Utilisateur(id: Int, nom:String) : Entite(id) {

    /**
     * Nom d'utilisateur dans le système.
     */
    var nom: String = nom

    override fun toString(): String {
        return nom
    }
}
