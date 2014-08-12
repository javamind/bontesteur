package com.ninjamind.conference.utils;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Date;

/**
 * Classe de test de {@link com.ninjamind.conference.utils.Utils}
 */
public class UtilsTest{
    /**
     * Test de {@link com.ninjamind.conference.utils.Utils#dateJavaToJson(java.util.Date)}
     * Une date nulle de doit pas planter
     */
    @Test
    public void dateJsonToJavaNull_should_ReturnNull(){
        Assertions.assertThat(Utils.dateJavaToJson(null)).isNull();
    }

    /**
     * Test de {@link com.ninjamind.conference.utils.Utils#dateJavaToJson(java.util.Date)}
     * Cas nominal
     */
    @Test
    public void dateJsonToJavaValid_should_ReturnString(){
        Date date = new Date(0);
        Assertions.assertThat(Utils.dateJavaToJson(date)).isNotNull().isEqualTo("1970-01-01 01:00:00");
    }

    /**
     * Test de {@link com.ninjamind.conference.utils.Utils#dateJsonToJava(String)}
     * Une date nulle de doit pas planter
     */
    @Test
    public void dateJavaToJsonNull_should_ReturnNull() throws Exception {
        Assertions.assertThat(Utils.dateJsonToJava(null)).isNull();
    }

    /**
     * Test de {@link com.ninjamind.conference.utils.Utils#dateJsonToJava(String)}
     * Une date invalide de doit pas planter
     */
    @Test
    public void dateJavaToJsonNotValid_should_ReturnNull() throws Exception {
        Assertions.assertThat(Utils.dateJsonToJava("date")).isNull();
    }

    /**
     * Test de {@link com.ninjamind.conference.utils.Utils#dateJsonToJava(String)}
     * Cas nominal
     */
    @Test
    public void dateJsonToJavaNotValid_should_ReturnNull() throws Exception {
        Assertions.assertThat(Utils.dateJsonToJava("1970-01-01 01:00:00")).isNotNull().isEqualTo(new Date(0));
    }

    /**
     * Test de {@link com.ninjamind.conference.utils.Utils#stringToLong(String)}
     * Cas ou argument null
     */
    @Test
    public void stringToLong_should_ReturnNullIfParamIsNull() throws Exception {
        Assertions.assertThat(Utils.stringToLong(null)).isNull();
    }

    /**
     * Test de {@link com.ninjamind.conference.utils.Utils#stringToLong(String)}
     * Cas ou argument invalid
     */
    @Test
    public void stringToLong_should_ReturnNullIfParamIsInvalid() throws Exception {
        Assertions.assertThat(Utils.stringToLong("toto")).isNull();
    }

    /**
     * Test de {@link com.ninjamind.conference.utils.Utils#stringToLong(String)}
     * Cas nominal
     */
    @Test
    public void stringToLong_should_Return1() throws Exception {
        Assertions.assertThat(Utils.stringToLong("1")).isNotNull().isEqualTo(1L);
    }
}
