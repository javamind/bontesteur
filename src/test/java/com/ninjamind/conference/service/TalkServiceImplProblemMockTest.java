package com.ninjamind.conference.service;

import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.events.CreatedEvent;
import com.ninjamind.conference.events.DeletedEvent;
import com.ninjamind.conference.events.UpdatedEvent;
import com.ninjamind.conference.repository.TalkRepository;
import com.ninjamind.conference.service.talk.TalkServiceImpl;
import junitparams.JUnitParamsRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Classe de test de {@link com.ninjamind.conference.service.talk.TalkService}  exemple de strict mock ce qu'il
 * ne faut pas faire.
 * Il ne faudrait pas de verify et ne faire que des stubs
 * @author EHRET_G
 */
@RunWith(JUnitParamsRunner.class)
public class TalkServiceImplProblemMockTest {
    public static final String CONF_NAME = "Le bon testeur il teste... le mauvais testeur il teste...";
    @Mock
    TalkRepository talkRepository;

    @InjectMocks
    TalkServiceImpl service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void shouldCreateTalk(){
        //La sauvegarde du talk retournera une instance avec un id
        Talk talkCreated = new Talk(CONF_NAME);
        talkCreated.setId(2345L);
        when(talkRepository.save(any(Talk.class))).thenReturn(talkCreated);

        //On appelle notre service de creation
        CreatedEvent<Talk> createdTalkEvent = service.createTalk(new Talk(CONF_NAME));
        assertThat(((Talk)createdTalkEvent.getValue()).getId()).isEqualTo(2345L);
        assertThat(((Talk)createdTalkEvent.getValue()).getName()).isEqualTo(CONF_NAME);

        //Le but est de verifier que la sauvegarde est appelee mais pas la recherche d'entite
        verify(talkRepository, only()).save(any(Talk.class));
        verifyNoMoreInteractions(talkRepository);
    }


}