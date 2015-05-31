package com.ninjamind.conference.service.lisibilite.sav;

import com.google.common.collect.Lists;
import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.exception.ConferenceNotFoundException;
import com.ninjamind.conference.repository.ConferenceRepository;
import com.ninjamind.conference.service.DefaultFavoriteService;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

import static junitparams.JUnitParamsRunner.$;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Classe de test de la classe {@link com.ninjamind.conference.service.DefaultFavoriteService}.
 * <br/>
 * Le but est de montrer
 * <ul>
 * <li>Localisation des tests : classe de test dans le meme package que la classe testee</li>
 * <li>Nommage de la classe de test : nom de la classe testee suffixee par Test</li>
 * <li>Nommage des methodes de tests qui doivent repondre aux questions quoi et pourquoi</li>
 * <li>Granularite un test ne doit tester qu'une seule chose a la fois</li>
 * <li>S'aider des frameworks de test : exemple de JUnitParamsRunner souvent meconnu</li>
 * <li>Des assertions simples</li>
 * <li>try/catch exception</li>
 * </ul>
 *
 * @author EHRET_G
 * @author Agnès
 */
@RunWith(JUnitParamsRunner.class)
public class DefaultFavoriteServiceCibleTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    private ConferenceRepository conferenceRepository;
    @InjectMocks
    private DefaultFavoriteService defaultFavoriteService;

    private List<Conference> conferences = new ArrayList<>();


    protected Object[] conferenceValues() {
        Conference devoxx2014 = new Conference().setName("Devoxx2014").setNbConferenceSlots(154L).setNbConferenceProposals(658L);
        Conference mixit2014 = new Conference().setName("Mix-IT2014").setNbConferenceSlots(30L).setNbConferenceProposals(200L);
        Conference jugsummercamp2014 = new Conference().setName("JugSummerCamp2014").setNbConferenceSlots(10L).setNbConferenceProposals(130L);
        Conference mixit2014WithoutParam = new Conference().setName("Mix-IT2014").setNbConferenceSlots(null).setNbConferenceProposals(200L);

        return $(
                //Avec les vraies valeurs : mixit2014 est la plus hype
                $(devoxx2014, mixit2014, Lists.newArrayList("Mix-IT2014", "Devoxx2014")),
                //Avec les vraies valeurs jugsummercamp2014 est la plus hype
                $(mixit2014, jugsummercamp2014, Lists.newArrayList("JugSummerCamp2014", "Mix-IT2014")),
                //Une conf avec des donnees incomplètes ne compte pas
                $(devoxx2014, mixit2014WithoutParam, Lists.newArrayList("Devoxx2014"))

        );
    }

    /**
     * Test de la methode {@link com.ninjamind.conference.service.DefaultFavoriteService#getTheHypestConfs()}
     * cas ou une valeur est retournee
     * @param conf1
     * @param conf2
     * @param expected
     */
    @Parameters(method = "conferenceValues")
    @Test
    public void should_return_hypest_confs(Conference conf1, Conference conf2, List<String> expected) throws Exception {

        ///////////////  premier cas de test : Devoxx2014 + Mix-IT2014
        conferences.add(conf1);
        conferences.add(conf2);

        when(conferenceRepository.findAll()).thenReturn(conferences);
        List<Conference> theHypestConfs = defaultFavoriteService.getTheHypestConfs();
        List<String> confNames = new ArrayList<String>();
        for (Conference conf : theHypestConfs) {
            confNames.add(conf.getName());
        }
        assertEquals(expected, confNames);

    }



    /**
     * Test de la methode {@link com.ninjamind.conference.service.DefaultFavoriteService#getTheHypestConfs}
     * cas ou aucune conference n'existe
     */
    @Test
    public void should_throw_ConferenceNotFoundException_when_conference_is_empty() {
        when(conferenceRepository.findAll()).thenReturn(conferences);

        try {
            defaultFavoriteService.getTheHypestConfs();
            Assert.fail();
        } catch (ConferenceNotFoundException e) {
            assertEquals("Aucune conference evaluee", e.getMessage());
        }
    }

}
