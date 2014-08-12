package com.ninjamind.conference.controller;

import com.google.common.collect.Lists;
import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.service.talk.TalkService;
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
 * Test du controller {@link com.ninjamind.conference.controller.TalkQueriesController}
 * @author ehret_g
 */
public class TalkQueriesControllerTest {
    MockMvc mockMvc;

    @InjectMocks
    TalkQueriesController controller;

    @Mock
    TalkService talkService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller).build();
    }

    /**
     * Test de la recuperation d'une talk via l'API REST : <code>/talks/{id}</code>. On teste le cas passant
     * @throws Exception
     */
    @Test
    public void shouldReturnEntityWhenSearchByIdValid() throws Exception {
        String idCherche = "1";

        //Le service renvoie une entite
        when(talkService.getTalk(any(Talk.class))).thenReturn(
                new Talk("Le bon testeur il teste..."));

        //L'appel de l'URL doit retourner un status 200
        mockMvc.perform(get("/talks/{id}", idCherche))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("name").value("Le bon testeur il teste..."))
                .andExpect(status().isOk());
    }

    /**
     * Test de la recuperation d'une talk via l'API REST : <code>/talks/{id}</code>. On teste le cas oe l'enregistrement
     * n'existe pas
     * @throws Exception
     */
    @Test
    public void shouldReturnNotFoundStatusWhenSearchByIdInvalid() throws Exception {
        //Le service renvoie une entite
        when(talkService.getTalk(any(Talk.class))).thenReturn(
                null);

        //L'appel de l'URL doit retourner un status 200
        mockMvc.perform(get("/talks/{id}", "1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * Test de la recuperation de toutes les talks via l'API REST : <code>/talks</code>. On teste le cas passant
     *
     * @throws Exception
     */
    @Test
    public void shouldReturnListEntityWhenSearchAllTalk() throws Exception {
        List<Talk> listExpected = Lists.newArrayList (
                new Talk(
                        "Le bon testeur il teste..."),
                new Talk(
                       "Le mauvais testeur il teste..."));

        //Le service renvoie une entite
        when(talkService.getAllTalk()).thenReturn(listExpected);

        //L'appel de l'URL doit retourner un status 200
        mockMvc.perform(get("/talks"))
                .andDo(print())
                .andExpect(jsonPath("$[0].name").value("Le bon testeur il teste..."))
                .andExpect(jsonPath("$[1].name").value("Le mauvais testeur il teste..."))
                .andExpect(status().isOk());
    }
}
