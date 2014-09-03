package com.ninjamind.conference.repository.interetdbsetup.sav;

import com.ninjamind.conference.config.ApplicationConfig;
import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.repository.TalkArchiverRepository;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test de a classe {@link com.ninjamind.conference.repository.TalkArchiverRepository}
 *
 * @author EHRET_G
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
@Transactional
public class TalkArchiverRepositoryImplAvecRuleTest {

    @Rule
    public DbUnitTestRule dbUnitTestRule = new DbUnitTestRule(readDataSet());

    /**
     * Repository a tester
     */
    @Autowired
    private TalkArchiverRepository talkArchiverRepository;

    @Autowired
    protected PlatformTransactionManager transactionManager;


    /**
     * Fichier de donn�es
     *
     * @return
     */
    protected IDataSet readDataSet() {
        try {
            return new FlatXmlDataSetBuilder().build(new File("src/test/resources/datasets/talk_init.xml"));
        } catch (MalformedURLException | DataSetException e) {
           throw new RuntimeException(e);
        }
    }

    /**
     * Test de {@link com.ninjamind.conference.repository.TalkArchiverRepository#findTalkToArchive(Integer)} quand arg invalide
     */
    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenArgIsNull() {
        talkArchiverRepository.findTalkToArchive(null);
    }

    /**
     * Test de {@link com.ninjamind.conference.repository.TalkArchiverRepository#findTalkToArchive(Integer)} quand tout est OK
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
     * Test de {@link com.ninjamind.conference.repository.TalkArchiverRepository#findTalkToArchive(Integer)} quand rien n'est trouve car la date
     * passee est trop vieille
     */
    @Test
    public void shouldNotFindOneConfToArchiveWhenYearIsTooOld() {
        assertThat(talkArchiverRepository.findTalkToArchive(2000)).isEmpty();

    }


    /**
     * Test de la fonction d'archivage {@link com.ninjamind.conference.repository.TalkArchiverRepository#archiveTalks(Integer)}  quand rien n'est trouve car la date
     * passee est trop vieille
     */
    @Test
    public void shouldNotArchiveTalkWhenNoEntityFound() {
        assertThat(talkArchiverRepository.archiveTalks(2000)).isEqualTo(0);
    }

    /**
     * Test de la fonction d'archivage {@link com.ninjamind.conference.repository.TalkArchiverRepository#archiveTalks(Integer)} quand rien n'est trouve car la date
     * passee est trop vieille
     */
    @Test
    public void shouldArchiveTalkWhenOneIsFound() throws Exception {

        TransactionTemplate tp = new TransactionTemplate(transactionManager);
        tp.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        tp.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                assertThat(talkArchiverRepository.archiveTalks(2014)).isEqualTo(1);
            }
        });

        dbUnitTestRule.assertTableInDatabaseIsEqualToXmlDataset("TALK", "src/test/resources/datasets/talk_archived.xml", "id", "name", "dateStart", "dateEnd", "status");

    }
}
