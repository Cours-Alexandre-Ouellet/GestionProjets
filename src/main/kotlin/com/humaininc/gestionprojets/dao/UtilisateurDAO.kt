package com.humaininc.gestionprojets.dao

import com.humaininc.gestionprojets.modele.Utilisateur
import com.humaininc.gestionprojets.service.ServiceBD
import java.sql.ResultSet

/**
 * Manipule les utilisateurs à partir de la base de données.
 */
class UtilisateurDAO(serviceBD: ServiceBD) : DAOAbstraite<Utilisateur>(serviceBD) {
    override fun enregistrer(entite: Utilisateur) {
        TODO("Not yet implemented")
    }

    override fun chargerTout(): MutableList<Utilisateur> {
        return chargerListe("SELECT * FROM utilisateur")
    }

    override fun chargerParId(id: Int): Utilisateur? {
        return chargerParId(id, "utilisateur")
    }

    override fun associerBdObjet(ligne: ResultSet): Utilisateur {
        return Utilisateur(
            ligne.getInt("id"),
            ligne.getString("nom")
        )
    }
}