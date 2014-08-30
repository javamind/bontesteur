package com.ninjamind.conference.controller;

import com.google.common.collect.Lists;
import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.events.CreatedEvent;
import com.ninjamind.conference.events.DeletedEvent;
import com.ninjamind.conference.events.UpdatedEvent;
import com.ninjamind.conference.service.talk.TalkService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 *  Test du controller {@link com.ninjamind.conference.controller.TalkController}
 * @author ehret_g
 */
public class TalkCommandsControllerTest {
    MockMvc mockMvc;

    @InjectMocks
    TalkController controller;

    @Mock
    TalkService talkService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller).build();
    }

    /**
     * Genere un flux Json contenant les donnees li�es � une talk
     * @param id
     * @param name
     * @return
     */
    private String generateTalkJson(String id, String name){
        return String.format("{\"id\":%s,\"name\":\"%s\",\"description\":null,\"place\":null,\"nbpeoplemax\":null,\"level\":null}", id, name);
    }

    /**
     * Test de la creation d'une talk via l'API REST : <code>/talks</code>. On teste le cas passant
     * @throws Exception
     */
    @Test
    public void shouldCreateEntity() throws Exception {
        //Le service renvoie une entite
        when(talkService.createTalk(any(Talk.class))).thenReturn(
                new CreatedEvent<>(true, new Talk().setDescription("Le bon testeur il teste..."))
        );

        //L'appel de l'URL doit retourner un status 201
        mockMvc.perform(
                post("/talks")
                        .content(generateTalkJson(null, "Le bon testeur il teste..."))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    /**
     * Test de la creation d'une talk via l'API REST : <code>/talks</code>. On teste le cas ou on a une erreur sur
     * les donnees
     * @throws Exception
     */
    @Test
    public void shouldNotCreateEntityIfValidationError() throws Exception {
        //Le service renvoie une entite
        when(talkService.createTalk(any(Talk.class))).thenReturn(
                new CreatedEvent<>(false, null));

        //L'appel de l'URL doit retourner un status 406 si donn�es inavlide
        mockMvc.perform(
                post("/talks")
                        .content(generateTalkJson(null, "Le bon testeur il teste..."))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotAcceptable());
    }

    /**
     * Test de la mise a jour d'une talk via l'API REST : <code>/talks</code>. On teste le cas passant
     * @throws Exception
     */
    @Test
    public void shouldUpdateEntity() throws Exception {
        //Le service renvoie une entite
        when(talkService.updateTalk(any(Talk.class))).thenReturn(
                new UpdatedEvent<>(true, new Talk().setDescription("Le bon testeur il teste..."))
        );

        //L'appel de l'URL doit retourner un status 201
        mockMvc.perform(
                put("/talks")
                        .content(generateTalkJson("1", "Le bon testeur il teste..."))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * Test de la mise a jour d'une talk via l'API REST : <code>/talks</code>. On teste le cas ou la donn�e n'existe pas
     * @throws Exception
     */
    @Test
    public void shouldNotUpdateEntityIfEntityNotFound() throws Exception {
        //Le service renvoie une entite
        when(talkService.updateTalk(any(Talk.class))).thenReturn(
                new UpdatedEvent<>(false, null));

        //L'appel de l'URL doit retourner un status 404 si donn�es non trouvee
        mockMvc.perform(
                put("/talks")
                        .content(generateTalkJson("1", "Le bon testeur il teste..."))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * Test de la mise a jour d'une talk via l'API REST : <code>/talks</code>. On teste le cas ou on a une erreur sur
     * les donnees
     * @throws Exception
     */
    @Test
    public void shouldNotUpdateEntityIfValidationError() throws Exception {
        //Le service renvoie une entite
        when(talkService.updateTalk(any(Talk.class))).thenReturn(
                new UpdatedEvent<>(false, true, null));

        //L'appel de l'URL doit retourner un status 406 si donn�es invalide
        mockMvc.perform(
                put("/talks")
                        .content(generateTalkJson("1", "Le bon testeur il teste..."))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotAcceptable());
    }

    /**
     * Test de la suppression d'une talk via l'API REST : <code>/talks</code>. On teste le cas passant
     * @throws Exception
     */
    @Test
    public void shouldDeleteEntity() throws Exception {
        //Le service renvoie une entite
        when(talkService.deleteTalk(any(Talk.class))).thenReturn(
                new DeletedEvent(true, new Talk(1L))
        );

        //L'appel de l'URL doit retourner un status 201
        mockMvc.perform(
                delete("/talks/{id}", "1")
                        .content(generateTalkJson("1", "Le bon testeur il teste..."))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * Test de la suppression d'une talk via l'API REST : <code>/talks</code>. On teste le cas ou la donn�e n'existe pas
     * @throws Exception
     */
    @Test
    public void shouldNotDeleteEntityIfEntityNotFound() throws Exception {
        //Le service renvoie une entite
        when(talkService.deleteTalk(any(Talk.class))).thenReturn(
                new DeletedEvent(false, null));

        //L'appel de l'URL doit retourner un status 404 si donn�es non trouvee
        mockMvc.perform(
                delete("/talks/{id}", "1")
                        .content(generateTalkJson("1", "Le bon testeur il teste..."))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
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
                new Talk().setName("Le bon testeur il teste..."));

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
        List<Talk> listExpected = Lists.newArrayList(
                new Talk().setName("Le bon testeur il teste..."),
                new Talk().setName("Le mauvais testeur il teste...")
        );

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
