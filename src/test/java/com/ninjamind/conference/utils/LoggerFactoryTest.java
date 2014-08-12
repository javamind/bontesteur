package com.ninjamind.conference.utils;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Test de la classe {@link LoggerFactory}  pour v�rifier le fonction de la factory
 * de {@link org.apache.log4j.Logger}
 */
public class LoggerFactoryTest {

    @Test
    public void makeTest_Should_BeOk() {
        Logger logger = LoggerFactory.make();

        Assertions.assertThat(logger).isNotNull();
        Assertions.assertThat(logger.getName()).isEqualTo("com.ninjamind.conference.utils.LoggerFactoryTest");
        //Par defaut pour les tests on est en niveau info
        Assertions.assertThat(logger.getEffectiveLevel()).isEqualTo(Level.INFO);
    }
}
