package com.humaininc.gestionprojets.dao

import com.humaininc.gestionprojets.modele.Projet
import com.humaininc.gestionprojets.modele.Utilisateur
import com.humaininc.gestionprojets.service.ServiceBD
import java.sql.Date
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

/**
 * DAO spécialiser pour les projets.
 *
 * @param serviceBD le service de base de données utilisé.
 *
 * @author Alexandre
 * @since 06/05/2023
 */
class ProjetDAO(serviceBD: ServiceBD) : DAOAbstraite<Projet>(serviceBD) {

    override fun enregistrer(entite: Projet) {
        val connexion = serviceBD.ouvrirConnexion()
        val estInsertion : Boolean = entite.id == null

        var requete : PreparedStatement = if(estInsertion) {
            connexion.prepareStatement("INSERT INTO projet (nom_projet, date_debut, date_fin, description, createur) VALUES (?, ?, ?, ? ,?);",
                Statement.RETURN_GENERATED_KEYS)
        } else {
            connexion.prepareStatement("UPDATE projet SET nom_projet = ?, date_debut = ?, date_fin = ?, description = ?, createur = ?;")
        }

        requete.setString(1, entite.nomProjet)
        requete.setDate(2, Date.valueOf(entite.dateDebut))
        requete.setDate(3, Date.valueOf(entite.dateFin))
        requete.setString(4, entite.descriptionProjet)
        requete.setInt(5, entite.createur.id!!)

        requete.executeUpdate()

        if(estInsertion) {
            val cleGenere : ResultSet = requete.generatedKeys

            if(cleGenere.next()) {
                entite.id = cleGenere.getInt(1)
            }
        }

        serviceBD.fermerConnexion()
    }


    override fun chargerTout(): MutableList<Projet> {
        val connexion = serviceBD.ouvrirConnexion()
        val requete: PreparedStatement = connexion.prepareStatement("SELECT * FROM Projet")
        val resultats: ResultSet = requete.executeQuery()
        val projets: MutableList<Projet> = mutableListOf()

        while(resultats.next()){
            projets.add(Projet(
                resultats.getInt("id"),
                resultats.getString("nom_projet"),
                resultats.getDate("date_debut").toLocalDate(),
                resultats.getDate("date_fin").toLocalDate(),
                resultats.getString("description"),
                // TODO : charger de la DAO d'utilisateurs
                Utilisateur(resultats.getInt("createur"), "Bob"),
                resultats.getBoolean("actif")
            ))
        }

        serviceBD.fermerConnexion()
        return projets
    }

    override fun chargerParId(id: Int): Projet {
        TODO("Not yet implemented")
    }


}