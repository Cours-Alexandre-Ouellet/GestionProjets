package com.humaininc.gestionprojets;

import com.humaininc.gestionprojets.modele.Utilisateur

/**
 * Controleur abstrait commun aux différents controleurs du système.
 *
 * @author Alexandre
 * @since 06/05/2023
 */
abstract class ControleurAbstrait() {

    protected var utilisateurConnecte : Utilisateur? = null

}
