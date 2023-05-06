package com.humaininc.gestionprojets.service

import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

/**
 * Gère l'accès aux services par les différentes classes du système.
 *
 * @author Alexandre
 * @since 06/05/2023
 */
class ConteneurService {

    /**
     * Liste des services de l'application
     */
    private val services: MutableList<IService> = mutableListOf()

    /**
     * Retourne le service demandé. Si le service n'existe pas, il est créé avant d'être retourné.
     *
     * @param classe la classe du service à créer
     * @return le service qui a été créé
     * @exception Exception si le service n'a pu être créé
     */
    fun <T> getService(classe: KClass<T>): IService where T : IService {
        for (service in services) {
            if (service::class == classe) {
                return service
            }
        }

        try {
            val nouveauService: IService = classe.primaryConstructor!!.call()
            services.add(nouveauService)

            return nouveauService
        } catch (exception: NullPointerException) {
            // TODO: spécialiser l'exception
            throw  Exception("Impossible de créer le service de type : ${classe.simpleName}")
        }

    }
}