package com.ninjamind.conference.service.mock.sav;

import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.events.CreatedEvent;
import com.ninjamind.conference.repository.TalkRepository;
import com.ninjamind.conference.service.talk.TalkServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Classe de test de {@link com.ninjamind.conference.service.talk.TalkService}  exemple de strict mock ce qu'il
 * ne faut pas faire.
 * Il ne faudrait pas de verify et ne faire que des stubs
 * @author EHRET_G
 */
public class TalkServiceImplProblemMockAvantModifTest {

    public static final String CONF_NAME = "Le bon testeur il teste... le mauvais testeur il teste...";

    private TalkRepository talkRepository;

    private TalkServiceImpl service;

    @Before
    public void setup() {
        talkRepository = Mockito.mock(TalkRepository.class);
        service = new TalkServiceImpl().setTalkRepository(talkRepository);
    }


    @Test
    public void shouldCreateTalk(){
        //La sauvegarde du talk retournera une instance avec un id
        Talk talkCreated = new Talk().setName(CONF_NAME);
        talkCreated.setId(2345L);
        when(talkRepository.save(any(Talk.class))).thenReturn(talkCreated);

        //On appelle notre service de creation
        Talk createdTalkEvent = service.createTalk(new Talk().setName(CONF_NAME));
        assertThat(createdTalkEvent.getId()).isEqualTo(2345L);
        assertThat(createdTalkEvent.getName()).isEqualTo(CONF_NAME);

        //Le but est de verifier que la sauvegarde est appelee mais pas la recherche d'entite
        verify(talkRepository, only()).save(any(Talk.class));
        verifyNoMoreInteractions(talkRepository);
    }


}