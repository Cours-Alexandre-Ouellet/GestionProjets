package com.humaininc.gestionprojets.service

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.assertSame

/**
 * Test unitiaire du conteneur de service
 *
 * @author Alexandre
 * @since 06/05/2023
 */

class ConteneurServiceTest {

    companion object {
        lateinit var services: ConteneurService

        @BeforeAll
        @JvmStatic
        fun initialiser() {
            services = ConteneurService()
        }
    }


    @Test
    fun testServiceSimple() {
        assertDoesNotThrow {
            services.getService<DummyService1>()
        }

    }

    @Test
    fun testServiceMultiple() {
       assertDoesNotThrow {
            services.getService<DummyService1>()
            services.getService<DummyService2>()
            services.getService<DummyService3>()
        }
    }

    @Test
    fun testRequeteMultiple() {
        val dummy1a: DummyService1 = services.getService<DummyService1>() as DummyService1
        services.getService<DummyService2>()
        services.getService<DummyService3>()
        val dummy1b: DummyService1 = services.getService<DummyService1>() as DummyService1

        assertSame(dummy1a, dummy1b)
    }

    class DummyService1 : IService
    class DummyService2 : IService
    class DummyService3 : IService
}