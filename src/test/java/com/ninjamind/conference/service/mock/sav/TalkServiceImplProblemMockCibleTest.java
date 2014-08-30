package com.ninjamind.conference.service.mock.sav;

import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.events.CreatedEvent;
import com.ninjamind.conference.repository.TalkRepository;
import com.ninjamind.conference.service.talk.TalkServiceImpl;
import junitparams.JUnitParamsRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Classe de test de {@link com.ninjamind.conference.service.talk.TalkService}
 * Cible a atteindre
 * @author EHRET_G
 */
@RunWith(MockitoJUnitRunner.class)
public class TalkServiceImplProblemMockCibleTest {

    public static final String CONF_NAME = "Le bon testeur il teste... le mauvais testeur il teste...";

    @Mock
    TalkRepository talkRepository;

    @InjectMocks
    TalkServiceImpl service;

    @Test
    public void shouldCreateTalk(){
        //La sauvegarde du talk retournera une instance avec un id
        Talk talkCreated = new Talk().setName(CONF_NAME);
        talkCreated.setId(2345L);
        when(talkRepository.save(any(Talk.class))).thenReturn(talkCreated);

        //On appelle notre service de creation
        CreatedEvent<Talk> createdTalkEvent = service.createTalk(new Talk().setName(CONF_NAME));

        //On ne verifie pas le contenu de l'objet car on sait ce que l'on recoit vu qu'on le
        //d�fini juste avant
        assertThat(createdTalkEvent.getValue()).isNotNull();
    }


}