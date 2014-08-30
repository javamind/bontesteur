package com.ninjamind.conference.service.speaker;

import com.ninjamind.conference.domain.Country;
import com.ninjamind.conference.domain.Speaker;
import com.ninjamind.conference.events.CreatedEvent;
import com.ninjamind.conference.events.DeletedEvent;
import com.ninjamind.conference.events.UpdatedEvent;
import com.ninjamind.conference.repository.CountryRepository;
import com.ninjamind.conference.repository.SpeakerRepository;
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
 * Classe de test de {@link com.ninjamind.conference.service.speaker.SpeakerService}
 *
 * @author EHRET_G
 */
@RunWith(JUnitParamsRunner.class)
public class SpeakerServiceImplTest {
    @Mock
    SpeakerRepository speakerRepository;
    @Mock
    CountryRepository countryRepository;

    @InjectMocks
    SpeakerServiceImpl service;

    private List<Speaker> speakers = new ArrayList<>();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test de {@link com.ninjamind.conference.service.speaker.SpeakerService#createSpeaker(com.ninjamind.conference.domain.Speaker)}
     * cas ou argument null
     */
    @Test(expected = NullPointerException.class)
    public void createSpeakerShouldThrownNullPointerExceptionIfArgIsNull(){
        service.createSpeaker(null);
    }

    /**
     * Test de {@link com.ninjamind.conference.service.speaker.SpeakerService#createSpeaker(com.ninjamind.conference.domain.Speaker)}
     * cas ou pas de speaker passe null
     */
    @Test(expected = NullPointerException.class)
    public void shouldNotcreateSpeakerWhenArgNull(){
        service.createSpeaker(null);
    }

    /**
     * Test de {@link com.ninjamind.conference.service.speaker.SpeakerService#createSpeaker(com.ninjamind.conference.domain.Speaker)}
     * cas ou on cree sans avoir passé de pays
     */
    @Test
    public void shouldCreateSpeakerEvenIfNoCountryAdded(){
        //La recherche de pays sans code ne donnera rien
        when(countryRepository.findCountryByCode(null)).thenReturn(null);
        //La sauvegarde du speaker retournera une instance avec un id
        Speaker speakerCreated = new Speaker().setFirstname("Martin").setLastname("Fowler");
        speakerCreated.setId(1L);
        when(speakerRepository.save(any(Speaker.class))).thenReturn(speakerCreated);

        //On appelle notre service de creation
        CreatedEvent<Speaker> createdSpeakerEvent =
                service.createSpeaker(new Speaker().setFirstname("Martin").setLastname("Fowler"));

        assertThat(((Speaker)createdSpeakerEvent.getValue()).getId()).isEqualTo(1L);
        assertThat(((Speaker)createdSpeakerEvent.getValue()).getLastname()).isEqualTo("Fowler");
    }

    /**
     * Test de {@link com.ninjamind.conference.service.speaker.SpeakerService#createSpeaker(com.ninjamind.conference.domain.Speaker)}
     * cas nominal
     */
    @Test
    public void shouldCreateSpeakerWithCountry(){
        //La recherche de pays renvoie une occurence
        when(countryRepository.findCountryByCode("FR")).thenReturn(new Country().setCode("FR").setName("France"));
        //La sauvegarde du speaker retournera une instance avec un id
        Speaker speakerCreated = new Speaker().setFirstname("Martin").setLastname("Fowler");
        speakerCreated.setId(1L);
        when(speakerRepository.save(any(Speaker.class))).thenReturn(speakerCreated);

        //On appelle notre service de creation
        Speaker param = new Speaker().setFirstname("Martin").setLastname("Fowler");
        param.setCountry(new Country().setCode("FR").setName("France"));
        CreatedEvent<Speaker> createdSpeakerEvent = service.createSpeaker(param);
        assertThat(((Speaker)createdSpeakerEvent.getValue()).getId()).isEqualTo(1L);
        assertThat(((Speaker)createdSpeakerEvent.getValue()).getFirstname()).isEqualTo("Martin");

    }


    /**
     * Test de {@link com.ninjamind.conference.service.speaker.SpeakerService#updateSpeaker(com.ninjamind.conference.domain.Speaker)}
     * cas ou argument null
     */
    @Test(expected = NullPointerException.class)
    public void shouldNotUupdateSpeakerWhenArgNull(){
        service.updateSpeaker(null);
    }

    /**
     * Test de {@link com.ninjamind.conference.service.speaker.SpeakerService#updateSpeaker(com.ninjamind.conference.domain.Speaker)}
     * cas ou pas de speaker passe null
     */
    @Test(expected = NullPointerException.class)
    public void shouldNotUupdateSpeakerWhenIdSpeakerNull(){
        service.updateSpeaker(new Speaker());
    }

    /**
     * Test de {@link com.ninjamind.conference.service.speaker.SpeakerService#updateSpeaker(com.ninjamind.conference.domain.Speaker)}
     * cas ou on cree sans avoir passé de pays
     */
    @Test
    public void shouldUpdateSpeakerEvenIfNoCountryAdded(){
        //La sauvegarde du speaker retournera une instance avec un id
        Speaker speakerCreated = new Speaker().setFirstname("Martin").setLastname("Fowler");
        speakerCreated.setId(1L);

        //La recherche de pays sans code ne donnera rien
        when(countryRepository.findCountryByCode(null)).thenReturn(null);
        //La recherche de l'entite passee renvoie un resultat
        when(speakerRepository.findOne(1L)).thenReturn(speakerCreated);
        //Sauvegarde
        when(speakerRepository.save(any(Speaker.class))).thenReturn(speakerCreated);

        //On appelle notre service de creation
        Speaker param = new Speaker().setFirstname("Martin").setLastname("Fowler");
        param.setId(1L);
        UpdatedEvent<Speaker> updatedSpeakerEvent = service.updateSpeaker(param);

        assertThat(((Speaker)updatedSpeakerEvent.getValue()).getId()).isEqualTo(1L);
        assertThat(((Speaker)updatedSpeakerEvent.getValue()).getFirstname()).isEqualTo("Martin");

    }

    /**
     * Test de {@link com.ninjamind.conference.service.speaker.SpeakerService#updateSpeaker(com.ninjamind.conference.domain.Speaker)}
     * cas ou on ne modifie pas car la donnée n'a pas été trouvée
     */
    @Test
    public void shouldNotUpdateSpeakerWhenConfIsNotFound(){
        //La recherche de pays renvoie une occurence
        when(countryRepository.findCountryByCode("FR")).thenReturn(new Country().setCode("FR").setName("France"));
        //La recherche de l'entite passee renvoie pas de resultat
        when(speakerRepository.findOne(1L)).thenReturn(null);

        //On appelle notre service de creation
        Speaker param = new Speaker().setFirstname("Martin").setLastname("Fowler");
        param.setId(1L);
        param.setCountry(new Country().setCode("FR").setName("France"));
        UpdatedEvent<Speaker> updatedSpeakerEvent = service.updateSpeaker(param);

        assertThat(updatedSpeakerEvent.getValue()).isNull();
        assertThat(updatedSpeakerEvent.isEntityFound()).isEqualTo(false);

    }

    /**
     * Test de {@link com.ninjamind.conference.service.speaker.SpeakerService#deleteSpeaker(com.ninjamind.conference.domain.Speaker)}
     * cas nominal
     */
    @Test
    public void shouldDeleteSpeaker(){
        //La recherche de l'entite passee renvoie un resultat
        when(speakerRepository.findOne(1L)).thenReturn(new Speaker().setFirstname("Martin").setLastname("Fowler"));

        //On appelle notre service de creation
        DeletedEvent<Speaker> deletedSpeakerEvent = service.deleteSpeaker(new Speaker().setId(1L));

        assertThat(deletedSpeakerEvent.getValue()).isNotNull();

    }

    /**
     * Test de {@link com.ninjamind.conference.service.speaker.SpeakerService#deleteSpeaker(com.ninjamind.conference.domain.Speaker)}
     * cas ou id passé ne correspond a aucun enregsitrement
     */
    @Test
    public void shouldNotDeleteSpeakerWhenEntityIsNotFound(){
        //La recherche de l'entite passee renvoie pas de resultat
        when(speakerRepository.findOne(1L)).thenReturn(null);

        //On appelle notre service de creation
        DeletedEvent<Speaker> deletedSpeakerEvent = service.deleteSpeaker(new Speaker().setId(1L));

        assertThat(deletedSpeakerEvent.getValue()).isNull();
        assertThat(deletedSpeakerEvent.isEntityFound()).isEqualTo(false);

    }

}