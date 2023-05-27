package com.humaininc.gestionprojets.dao

import com.humaininc.gestionprojets.service.ServiceBD
import java.sql.PreparedStatement
import java.sql.ResultSet

/**
 * Interface commune aux DAO. Elle permet d'enregistrer un objet, charger tous les objets du type
 * ou de charger un des objets par son id.
 *
 * @param le service de BD utilisé pour se connecter.
 *
 * @author Alexandre
 * @since 06/05/2023
 */
abstract class DAOAbstraite<T>(serviceBD : ServiceBD) where T : Entite{

    /**
     * Service de BD utilisé par la DAO
     */
    protected val serviceBD : ServiceBD = serviceBD

    /**
     * Enregistre une entitée dans la BD
     *
     * @param entite l'entitée à enregistrer
     */
    abstract fun enregistrer(entite : T)

    abstract fun chargerTout() : MutableList<T>

    /**
     * Charge toutes les entités du type indiqué. La [requeteSql] indiquée en paramètre récupère les informations
     * et la méthode [associerBdObjet] est utilisé pour convertir le contenu de la ligne chargée en un objet
     */
    protected fun chargerTout(requeteSql: String) : MutableList<T> {
        val connexion = serviceBD.ouvrirConnexion()
        val requete: PreparedStatement = connexion.prepareStatement(requeteSql)
        val resultats: ResultSet = requete.executeQuery()
        val projets: MutableList<T> = mutableListOf()

        while(resultats.next()){
            projets.add(associerBdObjet(resultats))
        }

        serviceBD.fermerConnexion()
        return projets
    }

    /**
     * Charge une entité selon l'identifiant indiqué.
     *
     * @param id l'identifiant de l'entité à charger.
     * @return l'entité chargée.
     */
    abstract fun chargerParId(id : Int) : T

    /**
     * Converti les données de [ligne] en un objet de type [T]. Cette méthode peut déclencher
     * des requêtes à la BD.
     */
    protected abstract fun associerBdObjet(ligne: ResultSet) : T;
}