package com.ninjamind.conference.service.conference;

import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.repository.ConferenceRepository;
import com.ninjamind.conference.repository.CountryRepository;
import org.assertj.core.api.Assertions;
import org.joda.time.DateTime;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Classe de test de {@link ConferenceService}
 *
 * @author EHRET_G
 */
public class ConferenceServiceImplTestNgTest {
    @Mock
    ConferenceRepository conferenceRepository;
    @Mock
    CountryRepository countryRepository;

    @InjectMocks
    ConferenceServiceImpl service;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Parametres utilises dans {@link #shouldFindTheCoolestConference(String, Long, Long, Long, Long, Long, String, Long, Long, Long, Long, Long, String)}
     *
     * @return
     */
    @DataProvider(name = "conferenceValues")
    public Object[][] conferenceValues() {
        return new Object[][]{
                {"Devoxx", 1704L, 1500L, 154L, 658L, 2800L, "Mix-IT", 43L, 500L, 30L, 200L, 850L, "Mix-IT"},
                {"Devoxx", 1704L, 1500L, 154L, 658L, 2800L, "Mix-IT", 43L, null, 30L, 200L, 850L, "Devoxx"},
                {"Devoxx", 2L, 1500L, 154L, 658L, 2800L, "Mix-IT", 43L, null, 30L, 200L, 850L, "Devoxx"}
        };
    }

    /**
     * Test de la methode {@link com.ninjamind.conference.service.conference.ConferenceService#getCoolestConference()}
     * cas ou une valeur est retournee
     *
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
    @Test(dataProvider = "conferenceValues")
    public void shouldFindTheCoolestConference(
            String nameConf1, Long nbHourToSellTicketConf1, Long nbAttendeesConf1,
            Long nbConferenceSlotConf1, Long nbConferenceProposalsConf1, Long nbTwitterFollowersConf1,
            String nameConf2, Long nbHourToSellTicketConf2, Long nbAttendeesConf2,
            Long nbConferenceSlotConf2, Long nbConferenceProposalsConf2, Long nbTwitterFollowersConf2,
            String confExpected
    ) {
        List<Conference> conferences = new ArrayList<>();
        addConference(conferences, nameConf1, nbHourToSellTicketConf1, nbAttendeesConf1, nbConferenceSlotConf1, nbConferenceProposalsConf1, nbTwitterFollowersConf1);
        addConference(conferences, nameConf2, nbHourToSellTicketConf2, nbAttendeesConf2, nbConferenceSlotConf2, nbConferenceProposalsConf2, nbTwitterFollowersConf2);
        when(conferenceRepository.findAll()).thenReturn(conferences);

        Assertions.assertThat(service.getCoolestConference().getName()).isEqualTo(confExpected);
    }

    /**
     * Permet d'ajouter une conference a la notre liste
     * @param conferences
     * @param name
     * @param nbHourToSellTicket
     * @param nbAttendees
     * @param nbConferenceSlot
     * @param nbConferenceProposals
     * @param nbTwitterFollowers
     */
    private void addConference(List<Conference> conferences, String name, Long nbHourToSellTicket, Long nbAttendees, Long nbConferenceSlot, Long nbConferenceProposals, Long nbTwitterFollowers) {
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

    /**
     * Test de la methode {@link com.ninjamind.conference.service.conference.ConferenceService#getCoolestConference()}
     * cas ou aucune valeur en base de donnees
     */
    @Test
    public void shouldNotFindTheCoolestConferenceWhenNoData() {
        when(conferenceRepository.findAll()).thenReturn(new ArrayList<Conference>());
        Assertions.assertThat(service.getCoolestConference()).isNull();
    }

    /**
     * Test de la methode {@link com.ninjamind.conference.service.conference.ConferenceService#getCoolestConference()}
     * cas ou une exception est remontee lors de la recuperation des donnees
     */
    @Test(expectedExceptions = PersistenceException.class)
    public void shouldNotFindTheCoolestConferenceWhenPersistenceProblem() {
        when(conferenceRepository.findAll()).thenThrow(new PersistenceException());
        service.getCoolestConference();
    }



}