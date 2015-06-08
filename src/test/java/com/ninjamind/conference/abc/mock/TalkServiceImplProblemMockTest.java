package com.ninjamind.conference.abc.mock;

import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.repository.TalkRepository;
import com.ninjamind.conference.service.talk.TalkService;
import com.ninjamind.conference.service.talk.TalkServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Classe de test de {@link com.ninjamind.conference.service.talk.TalkService}  ou on veut tester le save
 * @author EHRET_G
 */
public class TalkServiceImplProblemMockTest {

    public static final String CONF_NAME = "Le bon testeur il teste... le mauvais testeur il teste...";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    private TalkRepository talkRepository;
    @InjectMocks
    private TalkService service = new TalkServiceImpl();

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