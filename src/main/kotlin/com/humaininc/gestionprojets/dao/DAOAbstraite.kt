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
     * Charge une liste d'entités du type indiqué. La [requeteSql] indiquée en paramètre récupère les informations
     * et la méthode [associerBdObjet] est utilisé pour convertir le contenu de la ligne chargée en un objet
     */
    protected fun chargerListe(requeteSql: String) : MutableList<T> {
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
     * Charge une entité selon l'identifiant [id] indiqué. Si aucun élément ne porte cet [id], alors la valeur null est
     * retournée.
     */
    abstract fun chargerParId(id : Int) : T?

    /**
     * Charge une entité selon l'identifiant [id] indiqué dans la table du nom de [nomTable].
     * Si aucun élément ne porte cet [id], alors la valeur nulle est retournée. La méthode [associerBdObjet] est utilisée
     * pour la conversion.
     */
    protected fun chargerParId(id : Int, nomTable: String) : T?
    {
        val connexion = serviceBD.ouvrirConnexion()
        var requete : PreparedStatement = connexion.prepareStatement("SELECT * FROM $nomTable WHERE id = ?")
        requete.setInt(1, id)

        val resultats: ResultSet = requete.executeQuery()

        return if(resultats.next()) {
             associerBdObjet(resultats)
        } else {
            null
        }
    }

    /**
     * Converti les données de [ligne] en un objet de type [T]. Cette méthode peut déclencher
     * des requêtes à la BD.
     */
    protected abstract fun associerBdObjet(ligne: ResultSet) : T;
}