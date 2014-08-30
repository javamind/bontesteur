package com.ninjamind.conference.service.talk;

import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.events.CreatedEvent;
import com.ninjamind.conference.events.DeletedEvent;
import com.ninjamind.conference.events.UpdatedEvent;
import com.ninjamind.conference.repository.TalkRepository;
import junitparams.JUnitParamsRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Classe de test de {@link com.ninjamind.conference.service.talk.TalkService}
 *
 * @author EHRET_G
 */
@RunWith(JUnitParamsRunner.class)
public class TalkServiceImplTest {
    @Mock
    TalkRepository talkRepository;

    @InjectMocks
    TalkServiceImpl service;

    private List<Talk> talks = new ArrayList<>();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test(expected = NullPointerException.class)
    public void createTalkShouldThrownNullPointerExceptionIfArgIsNull(){
        service.createTalk(null);
    }


    @Test(expected = NullPointerException.class)
    public void shouldNotcreateTalkWhenArgNull(){
        service.createTalk(null);
    }


    @Test
    public void shouldCreateTalk(){
        //La sauvegarde du talk retournera une instance avec un id
        Talk talkCreated = new Talk().setName("Le bon testeur il teste... le mauvais testeur il teste...");
        talkCreated.setId(1L);
        when(talkRepository.save(any(Talk.class))).thenReturn(talkCreated);

        //On appelle notre service de creation
        CreatedEvent<Talk> createdTalkEvent = service.createTalk(new Talk().setName("Le bon testeur il teste... le mauvais testeur il teste..."));
        assertThat(((Talk)createdTalkEvent.getValue()).getId()).isEqualTo(1L);
        assertThat(((Talk)createdTalkEvent.getValue()).getName()).isEqualTo("Le bon testeur il teste... le mauvais testeur il teste...");
    }


    /**
     * Test de {@link com.ninjamind.conference.service.talk.TalkService#updateTalk(com.ninjamind.conference.domain.Talk)}
     * cas ou argument null
     */
    @Test(expected = NullPointerException.class)
    public void shouldNotUupdateTalkWhenArgNull(){
        service.updateTalk(null);
    }

    /**
     * Test de {@link com.ninjamind.conference.service.talk.TalkService#updateTalk(com.ninjamind.conference.domain.Talk)}
     * cas ou pas de talk passe null
     */
    @Test(expected = NullPointerException.class)
    public void shouldNotUupdateTalkWhenIdTalkNull(){
        service.updateTalk(new Talk());
    }

    /**
     * Test de {@link com.ninjamind.conference.service.talk.TalkService#updateTalk(com.ninjamind.conference.domain.Talk)}
     * cas ou on cree sans avoir passé de pays
     */
    @Test
    public void shouldUpdateTalk(){
        //La sauvegarde du talk retournera une instance avec un id
        Talk talkCreated = new Talk().setName("Le bon testeur il teste... le mauvais testeur il teste...");
        talkCreated.setId(1L);

        //La recherche de l'entite passee renvoie un resultat
        when(talkRepository.findOne(1L)).thenReturn(talkCreated);
        //Sauvegarde
        when(talkRepository.save(any(Talk.class))).thenReturn(talkCreated);

        //On appelle notre service de creation
        Talk param = new Talk().setName("Le bon testeur il teste... le mauvais testeur il teste...");
        param.setId(1L);
        UpdatedEvent<Talk> updatedTalkEvent = service.updateTalk(param);

        assertThat(((Talk)updatedTalkEvent.getValue()).getId()).isEqualTo(1L);
        assertThat(((Talk)updatedTalkEvent.getValue()).getName()).isEqualTo("Le bon testeur il teste... le mauvais testeur il teste...");

    }

    /**
     * Test de {@link com.ninjamind.conference.service.talk.TalkService#updateTalk(com.ninjamind.conference.domain.Talk)}
     * cas ou on ne modifie pas car la donnée n'a pas été trouvée
     */
    @Test
    public void shouldNotUpdateTalkWhenConfIsNotFound(){
        //La recherche de l'entite passee renvoie pas de resultat
        when(talkRepository.findOne(1L)).thenReturn(null);

        //On appelle notre service de creation
        Talk param = new Talk().setDescription("Le bon testeur il teste... le mauvais testeur il teste...");
        param.setId(1L);
        UpdatedEvent<Talk> updatedTalkEvent = service.updateTalk(param);

        assertThat(updatedTalkEvent.getValue()).isNull();
        assertThat(updatedTalkEvent.isEntityFound()).isEqualTo(false);

    }

    /**
     * Test de {@link com.ninjamind.conference.service.talk.TalkService#deleteTalk(com.ninjamind.conference.domain.Talk)}
     * cas nominal
     */
    @Test
    public void shouldDeleteTalk(){
        //La recherche de l'entite passee renvoie un resultat
        when(talkRepository.findOne(1L)).thenReturn(new Talk().setDescription("Le bon testeur il teste... le mauvais testeur il teste..."));

        //On appelle notre service de creation
        DeletedEvent<Talk> deletedTalkEvent = service.deleteTalk(new Talk(1L));

        assertThat(deletedTalkEvent.getValue()).isNotNull();
    }

    /**
     * Test de {@link com.ninjamind.conference.service.talk.TalkService#deleteTalk(com.ninjamind.conference.domain.Talk)}
     * cas ou id passé ne correspond a aucun enregsitrement
     */
    @Test
    public void shouldNotDeleteTalkWhenEntityIsNotFound(){
        //La recherche de l'entite passee renvoie pas de resultat
        when(talkRepository.findOne(1L)).thenReturn(null);

        //On appelle notre service de creation
        DeletedEvent<Talk> deletedTalkEvent = service.deleteTalk(new Talk(1L));

        assertThat(deletedTalkEvent.getValue()).isNull();
        assertThat(deletedTalkEvent.isEntityFound()).isEqualTo(false);

    }

}