package com.humaininc.gestionprojets.dao

import com.humaininc.gestionprojets.modele.Tache
import com.humaininc.gestionprojets.modele.Utilisateur
import com.humaininc.gestionprojets.service.ServiceBD
import java.sql.Date
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class TacheDAO(serviceBD: ServiceBD) : DAOAbstraite<Tache>(serviceBD) {

    override fun enregistrer(entite: Tache) {
        val connexion = serviceBD.ouvrirConnexion()
        val estInsertion : Boolean = entite.id == null

        val requete : PreparedStatement = if(estInsertion) {
            connexion.prepareStatement("INSERT INTO tache (nom_tache, date_realisation_prevue, description, projet, createur, etat, importance) " +
                    "VALUES (?, ?, ?, ? ,?, ?, ?);",
                Statement.RETURN_GENERATED_KEYS)
        } else {
            connexion.prepareStatement("UPDATE projet SET nom_projet = ?, date_debut = ?, date_fin = ?, description = ?, createur = ?;")
        }

        requete.setString(1, entite.nom)
        requete.setDate(2, Date.valueOf(entite.dateRelatisationPrevue))
        requete.setString(3, entite.description)
        requete.setInt(4, entite.projet.id!!)
        requete.setInt(5, entite.createur.id!!)
        requete.setInt(6, entite.etat.id!!)
        requete.setBoolean(7, entite.importance)

        requete.executeUpdate()

        if(estInsertion) {
            val cleGenere : ResultSet = requete.generatedKeys

            if(cleGenere.next()) {
                entite.id = cleGenere.getInt(1)
            }
        }

        serviceBD.fermerConnexion()
    }

    override fun chargerTout(): MutableList<Tache> {
        return chargerListe("SELECT * FROM tache")
    }

    override fun chargerParId(id: Int): Tache? {
        TODO("Not yet implemented")
    }

    override fun associerBdObjet(ligne: ResultSet): Tache {
        return Tache(
            ligne.getInt("id"),
            ligne.getString("nom_tache"),
            ligne.getDate("date_realisation_prevue").toLocalDate(),
            ligne.getString("description"),
            ProjetDAO(serviceBD).chargerParId(ligne.getInt("projet"))!!,        // Violation de clé étrangère
            //TODO : charger de la DAO
            Utilisateur(ligne.getInt("createur"), "Bob"),
            //TODO : charger de la DAO
            listOf(Utilisateur(1, "Bob")),
            EtatTacheDAO(serviceBD).chargerParId(ligne.getInt("etat"))!!,       // Violation de clé étrangère
            ligne.getBoolean("importance")
        )
    }

}