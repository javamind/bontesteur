package com.ninjamind.conference.repository.interetdbsetup.sav;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.generator.ValueGenerators;
import com.ninja_squad.dbsetup.operation.Operation;
import com.ninjamind.conference.category.IntegrationTest;
import com.ninjamind.conference.config.ApplicationConfig;
import com.ninjamind.conference.domain.Status;
import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.repository.TalkArchiverRepository;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test de a classe {@link com.ninjamind.conference.repository.TalkArchiverRepository}
 *
 * @author EHRET_G
 */
@ContextConfiguration(classes = {ApplicationConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@Category(IntegrationTest.class)
@Transactional
public class TalkArchiverRepositoryImplCibleTest {

    @Autowired
    protected DataSource dataSource;

    /**
     * Repository a tester
     */
    @Autowired
    private TalkArchiverRepository talkArchiverRepository;

    @Before
    public void prepare(){
        Operation init_data = sequenceOf(
                deleteAllFrom("talk"),
                insertInto("talk")
                        .withGeneratedValue("id", ValueGenerators.sequence().startingAt(1).incrementingBy(1))
                        .columns("name", "status", "dateStart", "dateEnd")
                        .values("Le bon testeur il teste", Status.ACTIVE, new DateTime(2014, 4, 18, 13, 30).toDate(), new DateTime(2014, 4, 18, 14, 20).toDate())
                        .values("La conf passee", Status.ACTIVE, new DateTime(2010, 4, 18, 13, 30).toDate(), new DateTime(2010, 4, 18, 14, 20).toDate())
                        .build()
        );
        DbSetup dbSetup = new DbSetup(DataSourceDestination.with(dataSource), init_data);
        dbSetup.launch();
    }

    /**
     * Test de {@link TalkArchiverRepository#findTalkToArchive(Integer)} quand arg invalide
     */
    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenArgIsNull() {
        talkArchiverRepository.findTalkToArchive(null);
    }

    /**
     * Test de {@link TalkArchiverRepository#findTalkToArchive(Integer)} quand tout est OK
     */
    @Test
    public void shouldFindOneConfToArchiveWhenYearIs2014() {
        List<Talk> talks = talkArchiverRepository.findTalkToArchive(2014);
        assertThat(talks).hasSize(1);
        assertThat(talks.get(0)).isEqualToComparingOnlyGivenFields(
                new Talk().setId(2L).setName("La conf passee"), "id", "name");

    }

    @Test
    public void shouldArchiveOneConfWhenYearIs2013() {
        int nb = talkArchiverRepository.archiveTalks(2013);
        assertThat(nb).isEqualTo(1);
    }

    /**
     * Test de {@link TalkArchiverRepository#findTalkToArchive(Integer)} quand rien n'est trouve car la date
     * passee est trop vieille
     */
    @Test
    public void shouldNotFindOneConfToArchiveWhenYearIsTooOld() {
        assertThat(talkArchiverRepository.findTalkToArchive(2000)).isEmpty();

    }


    /**
     * Test de la fonction d'archivage {@link TalkArchiverRepository#archiveTalks(Integer)}  quand rien n'est trouve car la date
     * passee est trop vieille
     */
    @Test
    public void shouldNotArchiveTalkWhenNoEntityFound() {
        assertThat(talkArchiverRepository.archiveTalks(2000)).isEqualTo(0);
    }

    /**
     * Test de la fonction d'archivage {@link TalkArchiverRepository#archiveTalks(Integer)} quand rien n'est trouve car la date
     * passee est trop vieille
     */
    @Test
    public void shouldArchiveTalkWhenOneIsFound() throws Exception {
        assertThat(talkArchiverRepository.archiveTalks(2014)).isEqualTo(1);
    }
}
