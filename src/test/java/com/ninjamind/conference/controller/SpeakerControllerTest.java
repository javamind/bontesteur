package com.ninjamind.conference.controller;

import com.google.common.collect.Lists;
import com.ninjamind.conference.category.IntegrationTest;
import com.ninjamind.conference.domain.Speaker;
import com.ninjamind.conference.service.speaker.SpeakerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 *  Test du controller {@link SpeakerController}
 * @author ehret_g
 */
public class SpeakerControllerTest {
    MockMvc mockMvc;

    @InjectMocks
    SpeakerController controller;

    @Mock
    SpeakerService speakerService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller).build();
    }

    /**
     * Genere un flux Json contenant les donnees li�es � une speaker
     * @param id
     * @param firstname
     * @param lastname
     * @return
     */
    private String generateSpeakerJson(String id, String firstname, String lastname){
        return String.format("{\"id\":%s,\"firstname\":\"%s\",\"lastname\":\"%s\",\"city\":null,\"postalCode\":null,\"streetAdress\":null," +
                "\"codeCountry\":null,\"company\":null}", id, firstname, lastname);

    }

    /**
     * Test de la creation d'une speaker via l'API REST : <code>/speaker</code>. On teste le cas passant
     * @throws Exception
     */
    @Test
    public void shouldCreateEntity() throws Exception {
        //Le service renvoie une entite
        when(speakerService.createSpeaker(any(Speaker.class))).thenReturn(new Speaker().setFirstname("Martin").setLastname("Fowler"));

        //L'appel de l'URL doit retourner un status 201
        mockMvc.perform(
                post("/speaker")
                        .content(generateSpeakerJson(null, "Martin", "Fowler"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    /**
     * Test de la creation d'une speaker via l'API REST : <code>/speaker</code>. On teste le cas ou on a une erreur sur
     * les donnees
     * @throws Exception
     */
    @Test
    public void shouldNotCreateEntityIfValidationError() throws Exception {
        //Le service renvoie une entite
        when(speakerService.createSpeaker(any(Speaker.class))).thenThrow(NullPointerException.class);

        //L'appel de l'URL doit retourner un status 406 si donn�es inavlide
        mockMvc.perform(
                post("/speaker")
                        .content(generateSpeakerJson(null, "Martin", "Fowler"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotAcceptable());
    }

    /**
     * Test de la mise a jour d'une speaker via l'API REST : <code>/speaker</code>. On teste le cas passant
     * @throws Exception
     */
    @Test
    public void shouldUpdateEntity() throws Exception {
        //Le service renvoie une entite
        when(speakerService.updateSpeaker(any(Speaker.class))).thenReturn(new Speaker().setFirstname("Martin").setLastname("Fowler"));

        //L'appel de l'URL doit retourner un status 201
        mockMvc.perform(
                put("/speaker")
                        .content(generateSpeakerJson("1", "Martin", "Fowler"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * Test de la mise a jour d'une speaker via l'API REST : <code>/speaker</code>. On teste le cas ou la donn�e n'existe pas
     * @throws Exception
     */
    @Test
    public void shouldNotUpdateEntityIfEntityNotFound() throws Exception {
        //Le service renvoie une entite
        when(speakerService.updateSpeaker(any(Speaker.class))).thenReturn(null);

        //L'appel de l'URL doit retourner un status 404 si donn�es non trouvee
        mockMvc.perform(
                put("/speaker")
                        .content(generateSpeakerJson("1", "Martin", "Fowler"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * Test de la mise a jour d'une speaker via l'API REST : <code>/speaker</code>. On teste le cas ou on a une erreur sur
     * les donnees
     * @throws Exception
     */
    @Test
    public void shouldNotUpdateEntityIfValidationError() throws Exception {
        //Le service renvoie une entite
        when(speakerService.updateSpeaker(any(Speaker.class))).thenThrow(NullPointerException.class);

        //L'appel de l'URL doit retourner un status 406 si donn�es invalide
        mockMvc.perform(
                put("/speaker")
                        .content(generateSpeakerJson("1", "Martin", "Fowler"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotAcceptable());
    }

    /**
     * Test de la suppression d'une speaker via l'API REST : <code>/speaker</code>. On teste le cas passant
     * @throws Exception
     */
    @Test
    public void shouldDeleteEntity() throws Exception {
        //Le service renvoie une entite
        when(speakerService.deleteSpeaker(any(Speaker.class))).thenReturn(true);

        //L'appel de l'URL doit retourner un status 201
        mockMvc.perform(
                delete("/speaker/{id}", "1")
                        .content(generateSpeakerJson("1", "Martin", "Fowler"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * Test de la suppression d'une speaker via l'API REST : <code>/speaker</code>. On teste le cas ou la donn�e n'existe pas
     * @throws Exception
     */
    @Test
    public void shouldNotDeleteEntityIfEntityNotFound() throws Exception {
        //Le service renvoie une entite
        when(speakerService.deleteSpeaker(any(Speaker.class))).thenReturn(false);

        //L'appel de l'URL doit retourner un status 404 si donn�es non trouvee
        mockMvc.perform(
                delete("/speaker/{id}", "1")
                        .content(generateSpeakerJson("1", "Martin", "Fowler"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * Test de la recuperation d'une speaker via l'API REST : <code>/speaker/{id}</code>. On teste le cas passant
     *
     * @throws Exception
     */
    @Test
    public void shouldReturnEntityWhenSearchByIdValid() throws Exception {
        String idCherche = "1";

        //Le service renvoie une entite
        when(speakerService.getSpeaker(any(Speaker.class))).thenReturn(
                new Speaker().setFirstname("Martin").setLastname("Fowler"));

        //L'appel de l'URL doit retourner un status 200
        mockMvc.perform(get("/speaker/{id}", idCherche))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("firstname").value("Martin"))
                .andExpect(status().isOk());
    }

    /**
     * Test de la recuperation d'une speaker via l'API REST : <code>/speaker/{id}</code>. On teste le cas oe l'enregistrement
     * n'existe pas
     *
     * @throws Exception
     */
    @Test
    public void shouldReturnNotFoundStatusWhenSearchByIdInvalid() throws Exception {
        //Le service renvoie une entite
        when(speakerService.getSpeaker(any(Speaker.class))).thenReturn(null);

        //L'appel de l'URL doit retourner un status 200
        mockMvc.perform(get("/speaker/{id}", "1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * Test de la recuperation de toutes les speakers via l'API REST : <code>/speaker</code>. On teste le cas passant
     *
     * @throws Exception
     */
    @Test
    public void shouldReturnListEntityWhenSearchAllSpeaker() throws Exception {
        List<Speaker> listExpected = Lists.newArrayList(
                new Speaker().setFirstname("Agnes").setLastname("Crepet"),
                new Speaker().setFirstname("Guillaume").setLastname("Ehret")
        );

        //Le service renvoie une entite
        when(speakerService.getAllSpeaker()).thenReturn(
                listExpected);

        //L'appel de l'URL doit retourner un status 200
        mockMvc.perform(get("/speaker"))
                .andDo(print())
                .andExpect(jsonPath("$[0].firstname").value("Agnes"))
                .andExpect(jsonPath("$[1].firstname").value("Guillaume"))
                .andExpect(status().isOk());
    }
}
