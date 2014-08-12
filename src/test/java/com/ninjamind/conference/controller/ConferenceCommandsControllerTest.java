package com.ninjamind.conference.controller;

import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.events.CreatedEvent;
import com.ninjamind.conference.events.DeletedEvent;
import com.ninjamind.conference.events.UpdatedEvent;
import com.ninjamind.conference.service.conference.ConferenceService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 *  Test du controller {@link com.ninjamind.conference.controller.ConferenceCommandsController}
 * @author ehret_g
 */
public class ConferenceCommandsControllerTest {
    MockMvc mockMvc;

    @InjectMocks
    ConferenceCommandsController controller;

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
        when(conferenceService.createConference(any(Conference.class))).thenReturn(
                new CreatedEvent<Conference>(true, new Conference(
                        "Mix-IT",
                        new Date(0),
                        new Date(0)))
        );

        //L'appel de l'URL doit retourner un status 201
        mockMvc.perform(
                post("/conferences")
                        .content(generateConferenceJson(null, "Mix-IT"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    /**
     * Test de la creation d'une conference via l'API REST : <code>/conferences</code>. On teste le cas ou on a une erreur sur
     * les donnees
     * @throws Exception
     */
    @Test
    public void shouldNotCreateEntityIfValidationError() throws Exception {
        //Le service renvoie une entite
        when(conferenceService.createConference(any(Conference.class))).thenReturn(
                new CreatedEvent<Conference>(false, null));

        //L'appel de l'URL doit retourner un status 406 si donn�es inavlide
        mockMvc.perform(
                post("/conferences")
                        .content(generateConferenceJson(null, "Mix-IT"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotAcceptable());
    }

    /**
     * Test de la mise a jour d'une conference via l'API REST : <code>/conferences</code>. On teste le cas passant
     * @throws Exception
     */
    @Test
    public void shouldUpdateEntity() throws Exception {
        //Le service renvoie une entite
        when(conferenceService.updateConference(any(Conference.class))).thenReturn(
                new UpdatedEvent<Conference>(true, new Conference(
                        "Mix-IT",
                       new Date(0),
                       new Date(0)))
        );

        //L'appel de l'URL doit retourner un status 201
        mockMvc.perform(
                put("/conferences")
                        .content(generateConferenceJson("1", "Mix-IT"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * Test de la mise a jour d'une conference via l'API REST : <code>/conferences</code>. On teste le cas ou la donn�e n'existe pas
     * @throws Exception
     */
    @Test
    public void shouldNotUpdateEntityIfEntityNotFound() throws Exception {
        //Le service renvoie une entite
        when(conferenceService.updateConference(any(Conference.class))).thenReturn(
                new UpdatedEvent<Conference>(false, null));

        //L'appel de l'URL doit retourner un status 404 si donn�es non trouvee
        mockMvc.perform(
                put("/conferences")
                        .content(generateConferenceJson("1", "Mix-IT"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * Test de la mise a jour d'une conference via l'API REST : <code>/conferences</code>. On teste le cas ou on a une erreur sur
     * les donnees
     * @throws Exception
     */
    @Test
    public void shouldNotUpdateEntityIfValidationError() throws Exception {
        //Le service renvoie une entite
        when(conferenceService.updateConference(any(Conference.class))).thenReturn(
                new UpdatedEvent<Conference>(false, true, null));

        //L'appel de l'URL doit retourner un status 406 si donn�es invalide
        mockMvc.perform(
                put("/conferences")
                        .content(generateConferenceJson("1", "Mix-IT"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotAcceptable());
    }

    /**
     * Test de la suppression d'une conference via l'API REST : <code>/conferences</code>. On teste le cas passant
     * @throws Exception
     */
    @Test
    public void shouldDeleteEntity() throws Exception {
        //Le service renvoie une entite
        when(conferenceService.deleteConference(any(Conference.class))).thenReturn(
                new DeletedEvent<Conference>(true, new Conference(
                        "Mix-IT",
                        new Date(0),
                        new Date(0)))
        );

        //L'appel de l'URL doit retourner un status 201
        mockMvc.perform(
                delete("/conferences/{id}", "1")
                        .content(generateConferenceJson("1", "Mix-IT"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * Test de la suppression d'une conference via l'API REST : <code>/conferences</code>. On teste le cas ou la donn�e n'existe pas
     * @throws Exception
     */
    @Test
    public void shouldNotDeleteEntityIfEntityNotFound() throws Exception {
        //Le service renvoie une entite
        when(conferenceService.deleteConference(any(Conference.class))).thenReturn(
                new DeletedEvent<Conference>(false, null));

        //L'appel de l'URL doit retourner un status 404 si donn�es non trouvee
        mockMvc.perform(
                delete("/conferences/{id}", "1")
                        .content(generateConferenceJson("1", "Mix-IT"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
