package com.ninjamind.conference.controller;

import com.google.common.collect.Lists;
import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.service.conference.ConferenceService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 *  Test du controller {@link com.ninjamind.conference.controller.ConferenceController}
 * @author ehret_g
 */
public class ConferenceControllerTest {
    MockMvc mockMvc;

    @InjectMocks
    ConferenceController controller;

    @Mock
    ConferenceService conferenceService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller).build();
    }

    /**
     * Genere un flux Json contenant les donnees li�es � une conference
     * @param id
     * @param name
     * @return
     */
    private String generateConferenceJson(String id, String name){
        return String.format("{\"id\":%s,\"name\":\"%s\",\"streetAdress\":null,\"city\":null,\"postalCode\":null," +
                "\"codeCountry\":null,\"dateStart\":\"2014-05-01 12:05:00\",\"dateEnd\":\"2014-07-01 12:05:00\"}", id, name);
    }

    /**
     * Test de la creation d'une conference via l'API REST : <code>/conferences</code>. On teste le cas passant
     * @throws Exception
     */
    @Test
    public void shouldCreateEntity() throws Exception {
        //Le service renvoie une entite
        when(conferenceService.createConference(any(Conference.class))).thenReturn(new Conference().setName("Mix-IT").setDateStart(new Date(0)).setDateEnd(new Date(0)));

        //L'appel de l'URL doit retourner un status 201
        mockMvc.perform(
                post("/conference")
                        .content(generateConferenceJson(null, "Mix-IT"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    /**
     * Test de la creation d'une conference via l'API REST : <code>/conference</code>. On teste le cas ou on a une erreur sur
     * les donnees
     * @throws Exception
     */
    @Test
    public void shouldNotCreateEntityIfValidationError() throws Exception {
        //Le service renvoie une entite
        when(conferenceService.createConference(any(Conference.class))).thenThrow(NullPointerException.class);

        //L'appel de l'URL doit retourner un status 406 si donn�es inavlide
        mockMvc.perform(
                post("/conference")
                        .content(generateConferenceJson(null, "Mix-IT"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotAcceptable());
    }

    /**
     * Test de la mise a jour d'une conference via l'API REST : <code>/conference</code>. On teste le cas passant
     * @throws Exception
     */
    @Test
    public void shouldUpdateEntity() throws Exception {
        //Le service renvoie une entite
        when(conferenceService.updateConference(any(Conference.class))).thenReturn(new Conference().setName("Mix-IT").setDateStart(new Date(0)).setDateEnd(new Date(0)));

        //L'appel de l'URL doit retourner un status 201
        mockMvc.perform(
                put("/conference")
                        .content(generateConferenceJson("1", "Mix-IT"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * Test de la mise a jour d'une conference via l'API REST : <code>/conference</code>. On teste le cas ou la donn�e n'existe pas
     * @throws Exception
     */
    @Test
    public void shouldNotUpdateEntityIfEntityNotFound() throws Exception {
        //Le service renvoie une entite
        when(conferenceService.updateConference(any(Conference.class))).thenReturn(null);

        //L'appel de l'URL doit retourner un status 404 si donn�es non trouvee
        mockMvc.perform(
                put("/conference")
                        .content(generateConferenceJson("1", "Mix-IT"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * Test de la mise a jour d'une conference via l'API REST : <code>/conference</code>. On teste le cas ou on a une erreur sur
     * les donnees
     * @throws Exception
     */
    @Test
    public void shouldNotUpdateEntityIfValidationError() throws Exception {
        //Le service renvoie une entite
        when(conferenceService.updateConference(any(Conference.class))).thenThrow(NullPointerException.class);

        //L'appel de l'URL doit retourner un status 406 si donn�es invalide
        mockMvc.perform(
                put("/conference")
                        .content(generateConferenceJson("1", "Mix-IT"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotAcceptable());
    }

    /**
     * Test de la suppression d'une conference via l'API REST : <code>/conference</code>. On teste le cas passant
     * @throws Exception
     */
    @Test
    public void shouldDeleteEntity() throws Exception {
        //Le service renvoie une entite
        when(conferenceService.deleteConference(any(Conference.class))).thenReturn(true);

        //L'appel de l'URL doit retourner un status 201
        mockMvc.perform(
                delete("/conference/{id}", "1")
                        .content(generateConferenceJson("1", "Mix-IT"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * Test de la suppression d'une conference via l'API REST : <code>/conference</code>. On teste le cas ou la donn�e n'existe pas
     * @throws Exception
     */
    @Test
    public void shouldNotDeleteEntityIfEntityNotFound() throws Exception {
        //Le service renvoie une entite
        when(conferenceService.deleteConference(any(Conference.class))).thenReturn(false);

        //L'appel de l'URL doit retourner un status 404 si donn�es non trouvee
        mockMvc.perform(
                delete("/conference/{id}", "1")
                        .content(generateConferenceJson("1", "Mix-IT"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * Test de la recuperation d'une conference via l'API REST : <code>/conference/{id}</code>. On teste le cas passant
     * @throws Exception
     */
    @Test
    public void shouldReturnEntityWhenSearchByIdValid() throws Exception {
        String idCherche = "1";

        //Le service renvoie une entite
        when(conferenceService.getConference(any(Conference.class))).thenReturn(
                new Conference().setName("Mix-IT").setDateStart(new Date(0)).setDateEnd(new Date(0)));

        //L'appel de l'URL doit retourner un status 200
        mockMvc.perform(get("/conference/{id}", idCherche))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("name").value("Mix-IT"))
                .andExpect(status().isOk());
    }

    /**
     * Test de la recuperation d'une conference via l'API REST : <code>/conference/{id}</code>. On teste le cas oe l'enregistrement
     * n'existe pas
     * @throws Exception
     */
    @Test
    public void shouldReturnNotFoundStatusWhenSearchByIdInvalid() throws Exception {
        //Le service renvoie une entite
        when(conferenceService.getConference(any(Conference.class))).thenReturn(
                null);

        //L'appel de l'URL doit retourner un status 200
        mockMvc.perform(get("/conference/{id}", "1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * Test de la recuperation de toutes les conferences via l'API REST : <code>/conference</code>. On teste le cas passant
     *
     * @throws Exception
     */
    @Test
    public void shouldReturnListEntityWhenSearchAllConference() throws Exception {
        List<Conference> listExpected = Lists.newArrayList(
                new Conference().setName("Mix-IT").setDateStart(new Date(0)).setDateEnd(new Date(0)),
                new Conference().setName("Devoxx").setDateStart(new Date(0)).setDateEnd(new Date(0)));


        //Le service renvoie une entite
        when(conferenceService.getAllConference()).thenReturn(listExpected);

        //L'appel de l'URL doit retourner un status 200
        mockMvc.perform(get("/conference"))
                .andDo(print())
                .andExpect(jsonPath("$[0].name").value("Mix-IT"))
                .andExpect(jsonPath("$[1].name").value("Devoxx"))
                .andExpect(status().isOk());
    }
}
