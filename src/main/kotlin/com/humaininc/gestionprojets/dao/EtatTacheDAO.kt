package com.humaininc.gestionprojets.dao

import com.humaininc.gestionprojets.modele.EtatTache
import com.humaininc.gestionprojets.service.ServiceBD
import java.sql.ResultSet

class EtatTacheDAO(serviceBD: ServiceBD) : DAOAbstraite<EtatTache>(serviceBD) {

    override fun enregistrer(entite: EtatTache) {
        TODO("Not yet implemented")
    }

    override fun chargerTout(): MutableList<EtatTache> {
        return chargerListe("SELECT * FROM EtatTache")
    }

    override fun chargerParId(id: Int): EtatTache? {
        TODO("Not yet implemented")
    }

    override fun associerBdObjet(ligne: ResultSet): EtatTache {
        return EtatTache(
            ligne.getInt("id"),
            ligne.getString("nom_etat"),
            ligne.getInt("ordre")
        )
    }
}