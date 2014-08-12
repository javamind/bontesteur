package com.ninjamind.conference.service.sav;

import com.ninjamind.conference.domain.Conference;
import com.ninjamind.conference.repository.ConferenceRepository;
import com.ninjamind.conference.service.DefaultFavoriteService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Test de la class {@link com.ninjamind.conference.service.DefaultFavoriteService}
 */
public class DefaultFavoriteServiceTest {
    @Mock
    private ConferenceRepository conferenceRepository;

    @InjectMocks
    private DefaultFavoriteService defaultFavoriteService;

    private List<Conference> conferences = new ArrayList<>();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    /**
     * Test de la methode {@link com.ninjamind.conference.service.DefaultFavoriteService#getTheHypestConfs()}
     * cas ou une valeur est retournee
     */
    @Test
    public void testTheHypestConfOK() throws Exception {

        ///////////////  premier cas de test : Devoxx2014 + Mix-IT2014
        Conference devoxx2014 = new Conference("Devoxx2014", 154L, 658L, 2820L);
        Conference mixit2014 = new Conference("Mix-IT2014", 30L, 200L, 845L);
        conferences.add(devoxx2014);
        conferences.add(mixit2014);

        when(conferenceRepository.findAll()).thenReturn(conferences);
        List<Conference> theHypestConfs = defaultFavoriteService.getTheHypestConfs();
        List<String> confNames = new ArrayList<String>();
        for (Conference conf : theHypestConfs) {
            confNames.add(conf.getName());
        }
        List<String> expected = Arrays.asList("Mix-IT2014", "Devoxx2014");
        assertEquals(expected, confNames);

        /////////////// deuxième cas de test : Devoxx2014 + Mix-IT2014 + JugSummerCamp2014
        conferences.clear();
        conferences.add(devoxx2014);
        conferences.add(mixit2014);
        Conference jugsummercamp2014 = new Conference("JugSummerCamp2014", 12L, 97L);
        conferences.add(jugsummercamp2014);

        when(conferenceRepository.findAll()).thenReturn(conferences);
        List<Conference> theHypestConfs2 = defaultFavoriteService.getTheHypestConfs();
        List<String> confNames2 = new ArrayList<String>();
        for (Conference conf : theHypestConfs2) {
            confNames2.add(conf.getName());
        }
        List<String> expected2 = Arrays.asList("JugSummerCamp2014","Mix-IT2014","Devoxx2014");
        assertEquals(expected2, confNames2);

        /////////////// troisième cas de test : Devoxx2014 + Mix-IT2014 sans un parametre
        conferences.clear();
        conferences.add(devoxx2014);
        Conference mixit2014WithoutParam = new Conference("Mix-IT2014", null, 200L, null);
        conferences.add(mixit2014WithoutParam);

        when(conferenceRepository.findAll()).thenReturn(conferences);
        List<Conference> theHypestConfs3 = defaultFavoriteService.getTheHypestConfs();
        List<String> confNames3 = new ArrayList<String>();
        for (Conference conf : theHypestConfs3) {
            confNames3.add(conf.getName());
        }
        List<String> expected3 = Arrays.asList("Devoxx2014");
        assertEquals(expected3, confNames3);
    }



    /**
     * Test de la methode {@link com.ninjamind.conference.service.DefaultFavoriteService#getTheHypestConfs}
     * cas ou aucune conference n'existe
     */
    @Test
    public void testTheHypestConfKO() {
        when(conferenceRepository.findAll()).thenReturn(conferences);

        try {
            defaultFavoriteService.getTheHypestConfs();
            Assert.fail();
        } catch (Exception e) {
            assertEquals("Aucune conference evaluee", e.getMessage());
        }
    }

}
