package com.humaininc.gestionprojets;

/**
 * Utilisateur du système.
 *
 * Classe placeholder avec uniquement un nom et un id d'utilisateur. TODO : bonifier la définition de l'utilisateur
 *
 * @author Alexandre
 * @since 06/05/2023
 */
class Utilisateur(id: Int, nom:String) {

    /**
     * Identifiant de l'utilisateur pour la base de données.
     */
    private val id: Int = id

    /**
     * Nom d'utilisateur dans le système.
     */
    private var nom: String = nom

}
