package com.ninjamind.conference.service.conference;

import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.domain.Country;
import com.ninjamind.conference.events.CreatedEvent;
import com.ninjamind.conference.events.DeletedEvent;
import com.ninjamind.conference.events.UpdatedEvent;
import com.ninjamind.conference.repository.ConferenceRepository;
import com.ninjamind.conference.repository.CountryRepository;
import com.ninjamind.conference.utils.Utils;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.assertj.core.api.Assertions;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

import static junitparams.JUnitParamsRunner.$;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Classe de test de {@link com.ninjamind.conference.service.conference.ConferenceService}
 *
 * @author EHRET_G
 */
@RunWith(JUnitParamsRunner.class)
public class ConferenceServiceImplTest {
    @Mock
    ConferenceRepository conferenceRepository;
    @Mock
    CountryRepository countryRepository;

    @InjectMocks
    ConferenceServiceImpl service;

    private List<Conference> conferences = new ArrayList<>();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        conferences.clear();
    }

    /**
     * Test de {@link com.ninjamind.conference.service.conference.ConferenceService#createConference(com.ninjamind.conference.domain.Conference)}
     * cas ou argument null
     */
    @Test(expected = NullPointerException.class)
    public void createConferenceShouldThrownNullPointerExceptionIfArgIsNull(){
        service.createConference(null);
    }

    /**
     * Test de {@link com.ninjamind.conference.service.conference.ConferenceService#createConference(com.ninjamind.conference.domain.Conference)}
     * cas ou pas de conference passe null
     */
    @Test(expected = NullPointerException.class)
    public void shouldNotcreateConferenceWhenArgNull(){
        service.createConference(null);
    }

    /**
     * Test de {@link com.ninjamind.conference.service.conference.ConferenceService#createConference(com.ninjamind.conference.domain.Conference)}
     * cas ou on cree sans avoir passé de pays
     */
    @Test
    public void shouldCreateConferenceEvenIfNoCountryAdded(){
        //La recherche de pays sans code ne donnera rien
        when(countryRepository.findCountryByCode(null)).thenReturn(null);
        //La sauvegarde de la conference retournera une instance avec un id
        Conference conferenceCreated = new Conference().setName("Mix-IT").setDateStart(new DateTime(2014,4,29,9,0).toDate()).setDateEnd(new DateTime(2014,4,30,19,0).toDate());
        conferenceCreated.setId(1L);
        when(conferenceRepository.save(any(Conference.class))).thenReturn(conferenceCreated);

        //On appelle notre service de creation
        CreatedEvent<Conference> createdConferenceEvent =
                service.createConference(new Conference().setName("Mix-IT").setDateStart(new DateTime(2014,4,29,9,0).toDate()).setDateEnd(new DateTime(2014,4,30,19,0).toDate()));

        assertThat(((Conference)createdConferenceEvent.getValue()).getId()).isEqualTo(1L);
        assertThat(Utils.dateJavaToJson(((Conference) createdConferenceEvent.getValue()).getDateStart())).isEqualTo("2014-04-29 09:00:00");
    }

    /**
     * Test de {@link com.ninjamind.conference.service.conference.ConferenceService#createConference(com.ninjamind.conference.domain.Conference)}
     * cas nominal
     */
    @Test
    public void shouldCreateConferenceWithCountry(){
        //La recherche de pays renvoie une occurence
        when(countryRepository.findCountryByCode("FR")).thenReturn(new Country().setCode("FR").setName("France"));
        //La sauvegarde de la conference retournera une instance avec un id
        Conference conferenceCreated = new Conference().setName("Mix-IT").setDateStart(new DateTime(2014,4,29,9,0).toDate()).setDateEnd(new DateTime(2014,4,30,19,0).toDate());
        conferenceCreated.setId(1L);
        when(conferenceRepository.save(any(Conference.class))).thenReturn(conferenceCreated);

        //On appelle notre service de creation
        Conference param = new Conference().setName("Mix-IT").setDateStart(new DateTime(2014,4,29,9,0).toDate()).setDateEnd(new DateTime(2014,4,30,19,0).toDate());;
        param.setCountry(new Country().setCode("FR").setName("France"));
        CreatedEvent<Conference> createdConferenceEvent = service.createConference(param);
        assertThat(((Conference)createdConferenceEvent.getValue()).getId()).isEqualTo(1L);
        assertThat(Utils.dateJavaToJson(((Conference) createdConferenceEvent.getValue()).getDateStart())).isEqualTo("2014-04-29 09:00:00");

    }


    /**
     * Test de {@link com.ninjamind.conference.service.conference.ConferenceService#updateConference(com.ninjamind.conference.domain.Conference)}
     * cas ou argument null
     */
    @Test(expected = NullPointerException.class)
    public void shouldNotUupdateConferenceWhenArgNull(){
        service.updateConference(null);
    }

    /**
     * Test de {@link com.ninjamind.conference.service.conference.ConferenceService#updateConference(com.ninjamind.conference.domain.Conference)}
     * cas ou pas de conference passe null
     */
    @Test(expected = NullPointerException.class)
    public void shouldNotUupdateConferenceWhenIdConferenceNull(){
        service.updateConference(new Conference());
    }

    /**
     * Test de {@link com.ninjamind.conference.service.conference.ConferenceService#updateConference(com.ninjamind.conference.domain.Conference)}
     * cas ou on cree sans avoir passé de pays
     */
    @Test
    public void shouldUpdateConferenceEvenIfNoCountryAdded(){
        //La sauvegarde de la conference retournera une instance avec un id
        Conference conferenceCreated = new Conference().setName("Mix-IT").setDateStart(new DateTime(2014,4,29,9,0).toDate()).setDateEnd(new DateTime(2014,4,30,19,0).toDate());
        conferenceCreated.setId(1L);

        //La recherche de pays sans code ne donnera rien
        when(countryRepository.findCountryByCode(null)).thenReturn(null);
        //La recherche de l'entite passee renvoie un resultat
        when(conferenceRepository.findOne(1L)).thenReturn(conferenceCreated);
        //Sauvegarde
        when(conferenceRepository.save(any(Conference.class))).thenReturn(conferenceCreated);

        //On appelle notre service de creation
        Conference param = new Conference().setName("Mix-IT").setDateStart(new DateTime(2014,4,29,9,0).toDate()).setDateEnd(new DateTime(2014,4,30,19,0).toDate());
        param.setId(1L);
        UpdatedEvent<Conference> updatedConferenceEvent = service.updateConference(param);

        assertThat(((Conference)updatedConferenceEvent.getValue()).getId()).isEqualTo(1L);
        assertThat(Utils.dateJavaToJson(((Conference) updatedConferenceEvent.getValue()).getDateStart())).isEqualTo("2014-04-29 09:00:00");

    }

    /**
     * Test de {@link com.ninjamind.conference.service.conference.ConferenceService#updateConference(com.ninjamind.conference.domain.Conference)}
     * cas ou on ne modifie pas car la donnée n'a pas été trouvée
     */
    @Test
    public void shouldNotUpdateConferenceWhenConfIsNotFound(){
        //La recherche de pays renvoie une occurence
        when(countryRepository.findCountryByCode("FR")).thenReturn(new Country().setCode("FR").setName("France"));
        //La recherche de l'entite passee renvoie pas de resultat
        when(conferenceRepository.findOne(1L)).thenReturn(null);

        //On appelle notre service de creation
        Conference param = new Conference().setName("Mix-IT").setDateStart(new DateTime(2014,4,29,9,0).toDate()).setDateEnd(new DateTime(2014,4,30,19,0).toDate());
        param.setId(1L);
        param.setCountry(new Country().setCode("FR").setName("France"));
        UpdatedEvent<Conference> updatedConferenceEvent = service.updateConference(param);

        assertThat(updatedConferenceEvent.getValue()).isNull();
        assertThat(updatedConferenceEvent.isEntityFound()).isEqualTo(false);

    }

    /**
     * Test de {@link com.ninjamind.conference.service.conference.ConferenceService#deleteConference(com.ninjamind.conference.domain.Conference)}
     * cas nominal
     */
    @Test
    public void shouldDeleteConference(){
        //La recherche de l'entite passee renvoie un resultat
        when(conferenceRepository.findOne(1L)).thenReturn(new Conference().setName("Mix-IT").setDateStart(new DateTime(2014,4,29,9,0).toDate()).setDateEnd(new DateTime(2014,4,30,19,0).toDate()));

        //On appelle notre service de creation
        DeletedEvent<Conference> deletedConferenceEvent = service.deleteConference(new Conference().setId(1L));

        assertThat(deletedConferenceEvent.getValue()).isNotNull();

    }

    /**
     * Test de {@link com.ninjamind.conference.service.conference.ConferenceService#deleteConference(com.ninjamind.conference.domain.Conference)}
     * cas ou id passé ne correspond a aucun enregsitrement
     */
    @Test
    public void shouldNotDeleteConferenceWhenEntityIsNotFound(){
        //La recherche de l'entite passee renvoie pas de resultat
        when(conferenceRepository.findOne(1L)).thenReturn(null);

        //On appelle notre service de creation
        DeletedEvent<Conference> deletedConferenceEvent = service.deleteConference(new Conference().setId(1L));

        assertThat(deletedConferenceEvent.getValue()).isNull();
        assertThat(deletedConferenceEvent.isEntityFound()).isEqualTo(false);

    }


    /**
     * Parametres utilises dans {@link #shouldFindTheCoolestConference(String, Long, Long, Long, Long, Long, String, Long, Long, Long, Long, Long, String)}
     * @return
     */
    protected Object[] conferenceValues(){
        return $(
                $("Devoxx", 1704L, 1500L, 154L, 658L, 2800L, "Mix-IT", 43L, 500L, 30L, 200L, 850L, "Mix-IT"),
                $("Devoxx", 1704L, 1500L, 154L, 658L, 2800L, "Mix-IT", 43L, null, 30L, 200L, 850L, "Devoxx"),
                $("Devoxx", 2L, 1500L, 154L, 658L, 2800L, "Mix-IT", 43L, null, 30L, 200L, 850L, "Devoxx")
        );
    }

    /**
     * Test de la methode {@link ConferenceService#getCoolestConference()}
     * cas ou une valeur est retournee
     * @param nameConf1
     * @param nbHourToSellTicketConf1
     * @param nbAttendeesConf1
     * @param nbConferenceSlotConf1
     * @param nbConferenceProposalsConf1
     * @param nbTwitterFollowersConf1
     * @param nameConf2
     * @param nbHourToSellTicketConf2
     * @param nbAttendeesConf2
     * @param nbConferenceSlotConf2
     * @param nbConferenceProposalsConf2
     * @param nbTwitterFollowersConf2
     * @param confExpected
     */
    @Test
    @Parameters(method = "conferenceValues")
    public void shouldFindTheCoolestConference(
            String nameConf1,  Long nbHourToSellTicketConf1, Long nbAttendeesConf1,
            Long nbConferenceSlotConf1, Long nbConferenceProposalsConf1, Long nbTwitterFollowersConf1,
            String nameConf2,  Long nbHourToSellTicketConf2, Long nbAttendeesConf2,
            Long nbConferenceSlotConf2, Long nbConferenceProposalsConf2, Long nbTwitterFollowersConf2,
            String confExpected
    ) {
        addConference(nameConf1, nbHourToSellTicketConf1, nbAttendeesConf1, nbConferenceSlotConf1, nbConferenceProposalsConf1, nbTwitterFollowersConf1);
        addConference(nameConf2, nbHourToSellTicketConf2, nbAttendeesConf2, nbConferenceSlotConf2, nbConferenceProposalsConf2, nbTwitterFollowersConf2);
        when(conferenceRepository.findAll()).thenReturn(conferences);

        Assertions.assertThat(service.getCoolestConference().getName()).isEqualTo(confExpected);
    }

    /**
     * Test de la methode {@link ConferenceService#getCoolestConference()}
     * cas ou aucune valeur en base de donnees
     */
    @Test
    public void shouldNotFindTheCoolestConferenceWhenNoData() {
        when(conferenceRepository.findAll()).thenReturn(conferences);
        Conference event = service.getCoolestConference();
        Assertions.assertThat(event).isNull();
    }

    /**
     * Test de la methode {@link ConferenceService#getCoolestConference()}
     * cas ou une exception est remontee lors de la recuperation des donnees
     */
    @Test(expected = PersistenceException.class)
    public void shouldNotFindTheCoolestConferenceWhenPersistenceProblem() {
        when(conferenceRepository.findAll()).thenThrow(new PersistenceException());
        service.getCoolestConference();
    }


    /**
     * Permet d'ajouter une conference a la notre liste
     * @param name
     * @param nbHourToSellTicket
     * @param nbAttendees
     * @param nbConferenceSlot
     * @param nbConferenceProposals
     * @param nbTwitterFollowers
     */
    private void addConference(String name, Long nbHourToSellTicket, Long nbAttendees, Long nbConferenceSlot, Long nbConferenceProposals, Long nbTwitterFollowers) {
        conferences.add(
                new Conference()
                        .setName(name)
                        .setDateStart(new DateTime(2014, 4, 29, 9, 0).toDate())
                        .setDateEnd(new DateTime(2014, 4, 30, 19, 0).toDate())
                        .setNbHoursToSellTicket(nbHourToSellTicket)
                        .setNbAttendees(nbAttendees)
                        .setNbConferenceSlots(nbConferenceSlot)
                        .setNbConferenceProposals(nbConferenceProposals)
                        .setNbTwitterFollowers(nbTwitterFollowers)
        );
    }
}