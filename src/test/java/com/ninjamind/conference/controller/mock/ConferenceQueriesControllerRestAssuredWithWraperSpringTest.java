package com.ninjamind.conference.controller.mock;

import com.ninjamind.conference.controller.ConferenceController;
import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.service.conference.ConferenceService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Test du controller {@link com.ninjamind.conference.controller.ConferenceController}
 * exemple utilisation de {@link com.jayway.restassured.module.mockmvc.RestAssuredMockMvc}.
 * rest-assured fournit un DSL pour simplifier les tests (https://code.google.com/p/rest-assured/wiki/Usage)
 *
 * @author ehret_g
 */
public class ConferenceQueriesControllerRestAssuredWithWraperSpringTest {
    @InjectMocks
    ConferenceController controller;

    @Mock
    ConferenceService conferenceService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void shouldFindMixit() throws Exception {
        //on veut que le service renvoie une entite
        when(conferenceService.getConference(any(Conference.class))).thenReturn(new Conference().setName("Mix-IT").setDateStart(new Date(0)).setDateEnd(new Date(0)));

        //wrapper rest assured
        given()
                .standaloneSetup(controller)
        .when()
                .get("/conferences/{id}", "1")
        .then()
                .statusCode(200)
                .body("name", equalTo("Mix-IT"))
                .contentType("application/json;charset=UTF-8");

        //A la place de la syntaxe mockvc
//        mockMvc.perform(get("/conferences/{id}", "1"))
//                .andDo(print())
//                .andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andExpect(jsonPath("name").value("Mix-IT"))
//                .andExpect(status().isOk());
    }



    @Test
    public void shouldNotFindConferenceWhenIdNull() throws Exception {
        //on veut que le service ne renvoie pas d'entite
        when(conferenceService.getConference(any(Conference.class))).thenReturn(null);

        //wrapper rest assured
        given()
                .standaloneSetup(controller)
        .when()
                .get("/conferences/{id}", (String) "1")
        .then()
                .statusCode(404);

        //A la place de la syntaxe mockvc
        //mockMvc.perform(get("/conferences/{id}", "1"))
        //        .andDo(print())
        //        .andExpect(status().isNotFound());
    }

}
