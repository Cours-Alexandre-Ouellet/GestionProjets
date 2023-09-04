package com.humaininc.gestionprojets.dao

import com.humaininc.gestionprojets.modele.Tache
import com.humaininc.gestionprojets.modele.Utilisateur
import com.humaininc.gestionprojets.service.ServiceBD
import java.lang.Exception
import java.sql.Date
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class TacheDAO(serviceBD: ServiceBD) : DAOAbstraite<Tache>(serviceBD) {

    override fun enregistrer(entite: Tache) {
        enregistrerTache(entite)
        enregistrerAffectations(entite)
    }

    /**
     * Enregistre la [tache] dans la base de données.
     */
    private fun enregistrerTache(tache: Tache) {
        val connexion = serviceBD.ouvrirConnexion()
        val estInsertion: Boolean = tache.id == null

        val requete: PreparedStatement = if (estInsertion) {
            connexion.prepareStatement(
                "INSERT INTO tache (nom_tache, date_realisation_prevue, description, projet, createur, etat, importance) " +
                        "VALUES (?, ?, ?, ? ,?, ?, ?);",
                Statement.RETURN_GENERATED_KEYS
            )
        } else {
            connexion.prepareStatement("UPDATE projet SET nom_projet = ?, date_debut = ?, date_fin = ?, description = ?, createur = ?;")
        }

        requete.setString(1, tache.nom)
        requete.setDate(2, Date.valueOf(tache.dateRelatisationPrevue))
        requete.setString(3, tache.description)
        requete.setInt(4, tache.projet.id!!)
        requete.setInt(5, tache.createur.id!!)
        requete.setInt(6, tache.etat.id!!)
        requete.setBoolean(7, tache.importance)

        requete.executeUpdate()

        if (estInsertion) {
            val cleGeneree: ResultSet = requete.generatedKeys

            if (cleGeneree.next()) {
                tache.id = cleGeneree.getInt(1)
            }
        }

        serviceBD.fermerConnexion()
    }

    /**
     * Enregistre toutes les affectations de la [tache] dans la base de données
     */
    private fun enregistrerAffectations(tache: Tache) {
        val connexion = serviceBD.ouvrirConnexion()

        for (utilisateur in tache.affectes) {
            val requete: PreparedStatement = connexion.prepareStatement(
                "REPLACE INTO affectationtache" +
                        " (utilisateur, tache) VALUES (?, ?)"
            )

            requete.setInt(1, utilisateur.id!!)
            requete.setInt(2, tache.id!!)

            requete.executeUpdate()
        }
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