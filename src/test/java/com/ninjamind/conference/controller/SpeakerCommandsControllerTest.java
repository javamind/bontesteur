package com.ninjamind.conference.controller;

import com.ninjamind.conference.domain.Speaker;
import com.ninjamind.conference.events.CreatedEvent;
import com.ninjamind.conference.events.DeletedEvent;
import com.ninjamind.conference.events.UpdatedEvent;
import com.ninjamind.conference.service.speaker.SpeakerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 *  Test du controller {@link SpeakerCommandsController}
 * @author ehret_g
 */
public class SpeakerCommandsControllerTest {
    MockMvc mockMvc;

    @InjectMocks
    SpeakerCommandsController controller;

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
     * Test de la creation d'une speaker via l'API REST : <code>/speakers</code>. On teste le cas passant
     * @throws Exception
     */
    @Test
    public void shouldCreateEntity() throws Exception {
        //Le service renvoie une entite
        when(speakerService.createSpeaker(any(Speaker.class))).thenReturn(
                new CreatedEvent<Speaker>(true, new Speaker(
                        "Martin",
                        "Fowler"))
        );

        //L'appel de l'URL doit retourner un status 201
        mockMvc.perform(
                post("/speakers")
                        .content(generateSpeakerJson(null, "Martin", "Fowler"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    /**
     * Test de la creation d'une speaker via l'API REST : <code>/speakers</code>. On teste le cas ou on a une erreur sur
     * les donnees
     * @throws Exception
     */
    @Test
    public void shouldNotCreateEntityIfValidationError() throws Exception {
        //Le service renvoie une entite
        when(speakerService.createSpeaker(any(Speaker.class))).thenReturn(
                new CreatedEvent(false, null));

        //L'appel de l'URL doit retourner un status 406 si donn�es inavlide
        mockMvc.perform(
                post("/speakers")
                        .content(generateSpeakerJson(null, "Martin", "Fowler"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotAcceptable());
    }

    /**
     * Test de la mise a jour d'une speaker via l'API REST : <code>/speakers</code>. On teste le cas passant
     * @throws Exception
     */
    @Test
    public void shouldUpdateEntity() throws Exception {
        //Le service renvoie une entite
        when(speakerService.updateSpeaker(any(Speaker.class))).thenReturn(
                new UpdatedEvent<Speaker>(true, new Speaker(
                        "Martin",
                        "Fowler"))
        );

        //L'appel de l'URL doit retourner un status 201
        mockMvc.perform(
                put("/speakers")
                        .content(generateSpeakerJson("1", "Martin", "Fowler"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * Test de la mise a jour d'une speaker via l'API REST : <code>/speakers</code>. On teste le cas ou la donn�e n'existe pas
     * @throws Exception
     */
    @Test
    public void shouldNotUpdateEntityIfEntityNotFound() throws Exception {
        //Le service renvoie une entite
        when(speakerService.updateSpeaker(any(Speaker.class))).thenReturn(
                new UpdatedEvent(false, null));

        //L'appel de l'URL doit retourner un status 404 si donn�es non trouvee
        mockMvc.perform(
                put("/speakers")
                        .content(generateSpeakerJson("1", "Martin", "Fowler"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * Test de la mise a jour d'une speaker via l'API REST : <code>/speakers</code>. On teste le cas ou on a une erreur sur
     * les donnees
     * @throws Exception
     */
    @Test
    public void shouldNotUpdateEntityIfValidationError() throws Exception {
        //Le service renvoie une entite
        when(speakerService.updateSpeaker(any(Speaker.class))).thenReturn(
                new UpdatedEvent(false, true, null));

        //L'appel de l'URL doit retourner un status 406 si donn�es invalide
        mockMvc.perform(
                put("/speakers")
                        .content(generateSpeakerJson("1", "Martin", "Fowler"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotAcceptable());
    }

    /**
     * Test de la suppression d'une speaker via l'API REST : <code>/speakers</code>. On teste le cas passant
     * @throws Exception
     */
    @Test
    public void shouldDeleteEntity() throws Exception {
        //Le service renvoie une entite
        when(speakerService.deleteSpeaker(any(Speaker.class))).thenReturn(
                new DeletedEvent<Speaker>(true, new Speaker("Martin", "Fowler"))
        );

        //L'appel de l'URL doit retourner un status 201
        mockMvc.perform(
                delete("/speakers/{id}", "1")
                        .content(generateSpeakerJson("1", "Martin", "Fowler"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * Test de la suppression d'une speaker via l'API REST : <code>/speakers</code>. On teste le cas ou la donn�e n'existe pas
     * @throws Exception
     */
    @Test
    public void shouldNotDeleteEntityIfEntityNotFound() throws Exception {
        //Le service renvoie une entite
        when(speakerService.deleteSpeaker(any(Speaker.class))).thenReturn(
                new DeletedEvent<Speaker>(false, null));

        //L'appel de l'URL doit retourner un status 404 si donn�es non trouvee
        mockMvc.perform(
                delete("/speakers/{id}", "1")
                        .content(generateSpeakerJson("1", "Martin", "Fowler"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
