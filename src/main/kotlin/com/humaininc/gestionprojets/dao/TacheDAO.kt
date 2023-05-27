package com.humaininc.gestionprojets.dao

import com.humaininc.gestionprojets.modele.Tache
import com.humaininc.gestionprojets.service.ServiceBD
import java.sql.ResultSet

class TacheDAO(serviceBD: ServiceBD) : DAOAbstraite<Tache>(serviceBD) {

    override fun enregistrer(entite: Tache) {
        TODO("Not yet implemented")
    }

    override fun chargerTout(): MutableList<Tache> {
        TODO("Not yet implemented")
    }

    override fun chargerParId(id: Int): Tache {
        TODO("Not yet implemented")
    }

    override fun associerBdObjet(ligne: ResultSet): Tache {
        TODO("Not yet implemented")
    }

}