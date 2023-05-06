package com.humaininc.gestionprojets.service

import java.sql.Connection
import java.sql.DriverManager

/**
 * Service de la base de données. Assure la connexion et la déconnexion au SGBD.
 *
 * @author Alexandre
 * @since 06/05/2023
 */
class ServiceBD : IService {

    /**
     * Définition des constantes de la BD
     */
    companion object {
        /**
         * Hôte de la BD
         */
        private const val HOTE : String = "jdbc:mysql://127.0.0.1:3306/gestionprojets"

        /**
         * Nom de l'utilisateur
         */
        private const val UTILISATEUR : String = "root"

        /**
         * Mot de passe
         */
        private const val MOT_DE_PASSE : String = "mysql"
    }

    /**
     * Contient la connexion à la base de données
     */
    private lateinit var connexion : Connection

    /**
     * Ouvre une connexion avec le SGBD. Si une connexion est déjà ouverte au moment de l'appel, la connexion
     * précédente est fermée, puis réouverte.
     *
     * @return la connexion active
     * TODO : gérer les exceptions
     */
    fun ouvrirConnexion() : Connection {
        if(this::connexion.isInitialized && !connexion.isClosed){
            fermerConnexion()
        }

        connexion = DriverManager.getConnection(
            HOTE, UTILISATEUR, MOT_DE_PASSE
        )
        return connexion
    }

    /**
     * Ferme la connexion si elle n'est pas déjà fermée.
     */
    fun fermerConnexion() {
        if(!connexion.isClosed) {
            connexion.close()
        }
    }
}