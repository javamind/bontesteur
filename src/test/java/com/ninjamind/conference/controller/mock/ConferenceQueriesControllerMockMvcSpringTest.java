package com.ninjamind.conference.controller.mock;

import com.ninjamind.conference.controller.ConferenceController;
import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.service.conference.ConferenceService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Test du controller {@link com.ninjamind.conference.controller.ConferenceController}
 * exemple utilisation de {@link org.springframework.test.web.servlet.MockMvc}
 * @author ehret_g
 */
public class ConferenceQueriesControllerMockMvcSpringTest {
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


    @Test
    public void shouldFindMixit() throws Exception {
        //on veut que le service renvoie une entite
        when(conferenceService.getConference(any(Conference.class))).thenReturn(new Conference().setName("Mix-IT").setDateStart(new Date(0)).setDateEnd(new Date(0)));

        //L'appel de l'URL doit retourner un status 200
        mockMvc.perform(get("/conference/{id}", "1"))
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("name").value("Mix-IT"))
                .andExpect(status().isOk());
    }



    @Test
    public void shouldNotFindConferenceWhenIdNull() throws Exception {
        //on veut que le service ne renvoie pas d'entite
        when(conferenceService.getConference(any(Conference.class))).thenReturn(null);

        //L'appel de l'URL doit retourner un status 404
        mockMvc.perform(get("/conference/{id}", "1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
