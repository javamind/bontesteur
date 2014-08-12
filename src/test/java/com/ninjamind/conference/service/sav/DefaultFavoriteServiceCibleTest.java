package com.ninjamind.conference.service.sav;

import com.google.common.collect.Lists;
import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.repository.ConferenceRepository;
import com.ninjamind.conference.service.DefaultFavoriteService;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
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
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.assertj.core.data.Index.atIndex;
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
    @Mock
    private ConferenceRepository conferenceRepository;

    @InjectMocks
    private DefaultFavoriteService defaultFavoriteService;

    private List<Conference> conferences = new ArrayList<>();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        conferences.clear();
    }

    /**
     * Methode JUnitParams permettant d'injecter les valeurs de tests dans une méthode de tests
     *
     * @return les paramètres des tests
     */
    protected Object[] conferenceValues() {
        Conference devoxx2014 = new Conference("Devoxx2014", 154L, 658L);
        Conference mixit2014 = new Conference("Mix-IT2014", 30L, 200L, 845L);
        Conference jugsummercamp2014 = new Conference("JugSummerCamp2014", 12L, 97L);
        Conference mixit2014WithoutParam = new Conference("Mix-IT2014", null, 200L);

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
     * Test de la recuperation de la conference dans des cas OK
     * Test paramétré : les données de la methode conferenceValues lui sont injectés.
     * A l'execution : autant d'execution que
     */
    @Test
    @Parameters(method = "conferenceValues")
    public void shouldFindTheHypestConference(Conference conf1, Conference conf2, List<Conference> confsExpected) throws Exception {
        conferences.add(conf1);
        conferences.add(conf2);
        when(conferenceRepository.findAll()).thenReturn(conferences);
        assertThat(defaultFavoriteService.getTheHypestConfs()).extracting("name").containsExactlyElementsOf(confsExpected);
    }


    /**
     * Test de la recuperation de la conference la plus selective dans le cas où on a une pb avec le repository:
     * le repository lève une exception unchecked de type PersistenceException
     * Dans ce cas là pas de message d'exception à vérifier : autant utiliser simplement 'expected' dans l'annotaiton
     */
    @Test(expected = PersistenceException.class)
    public void shouldThrowExceptionWhenProblemOnDatabase() throws Exception {
        when(conferenceRepository.findAll()).thenThrow(new PersistenceException());
        defaultFavoriteService.getTheHypestConfs();
        failBecauseExceptionWasNotThrown(PersistenceException.class);

    }

    /**
     * Test de la recuperation de la conference la plus selective dans le cas où aucune conference n'est à evaluer
     * Doit retourner une exception avec un message "Aucune conference evaluée"
     * Dans ce cas là si le message de l'exception est à vérifier : mieux faire un try-catch et un assert sur le message
     */
    @Test()
    public void shouldThrowExceptionWhenNoConf() {
        when(conferenceRepository.findAll()).thenReturn(conferences);
        try {
            defaultFavoriteService.getTheHypestConfs();
            failBecauseExceptionWasNotThrown(Exception.class);
        } catch (Exception e) {
            assertThat(e).hasMessage("Aucune conference evaluee").hasNoCause();
        }
    }

}
