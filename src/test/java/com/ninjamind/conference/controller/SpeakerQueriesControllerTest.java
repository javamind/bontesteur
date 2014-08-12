package com.ninjamind.conference.controller;

import com.google.common.collect.Lists;
import com.ninjamind.conference.domain.Speaker;
import com.ninjamind.conference.service.speaker.SpeakerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Test du controller {@link SpeakerQueriesController}
 * @author ehret_g
 */
public class SpeakerQueriesControllerTest {
    MockMvc mockMvc;

    @InjectMocks
    SpeakerQueriesController controller;

    @Mock
    SpeakerService speakerService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller).build();
    }

    /**
     * Test de la recuperation d'une speaker via l'API REST : <code>/speakers/{id}</code>. On teste le cas passant
     * @throws Exception
     */
    @Test
    public void shouldReturnEntityWhenSearchByIdValid() throws Exception {
        String idCherche = "1";

        //Le service renvoie une entite
        when(speakerService.getSpeaker(any(Speaker.class))).thenReturn(
                new Speaker(    "Martin",
                                "Fowler"));

        //L'appel de l'URL doit retourner un status 200
        mockMvc.perform(get("/speakers/{id}", idCherche))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("firstname").value("Martin"))
                .andExpect(status().isOk());
    }

    /**
     * Test de la recuperation d'une speaker via l'API REST : <code>/speakers/{id}</code>. On teste le cas oe l'enregistrement
     * n'existe pas
     * @throws Exception
     */
    @Test
    public void shouldReturnNotFoundStatusWhenSearchByIdInvalid() throws Exception {
        //Le service renvoie une entite
        when(speakerService.getSpeaker(any(Speaker.class))).thenReturn(null);

        //L'appel de l'URL doit retourner un status 200
        mockMvc.perform(get("/speakers/{id}", "1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * Test de la recuperation de toutes les speakers via l'API REST : <code>/speakers</code>. On teste le cas passant
     *
     * @throws Exception
     */
    @Test
    public void shouldReturnListEntityWhenSearchAllSpeaker() throws Exception {
        List<Speaker> listExpected = Lists.newArrayList (
                new Speaker(
                        "Agnes",
                        "Crepet"),
                new Speaker(
                        "Guillaume",
                        "Ehret"));

        //Le service renvoie une entite
        when(speakerService.getAllSpeaker()).thenReturn(
                listExpected);

        //L'appel de l'URL doit retourner un status 200
        mockMvc.perform(get("/speakers"))
                .andDo(print())
                .andExpect(jsonPath("$[0].firstname").value("Agnes"))
                .andExpect(jsonPath("$[1].firstname").value("Guillaume"))
                .andExpect(status().isOk());
    }
}
