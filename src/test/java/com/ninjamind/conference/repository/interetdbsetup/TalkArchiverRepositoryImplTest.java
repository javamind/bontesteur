package com.ninjamind.conference.repository.interetdbsetup;

import com.ninjamind.conference.category.IntegrationTest;
import com.ninjamind.conference.config.ApplicationConfig;
import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.repository.TalkArchiverRepository;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
@Category(IntegrationTest.class)
@Transactional
public class TalkArchiverRepositoryImplTest {

    @Rule
    public DbUnitTestRule dbUnitTestRule = new DbUnitTestRule(readDataSet());

    @Autowired
    private TalkArchiverRepository talkArchiverRepository;


    protected IDataSet readDataSet() {
        try {
            return new FlatXmlDataSetBuilder().build(new File("src/test/resources/datasets/talk_init.xml"));
        } catch (MalformedURLException | DataSetException e) {
           throw new RuntimeException(e);
        }
    }


    @Test
    public void shouldFindOneConfToArchiveWhenYearIs2014() {
        List<Talk> talks = talkArchiverRepository.findTalkToArchive(2014);
        assertThat(talks).hasSize(1);
        assertThat(talks.get(0)).isEqualToComparingOnlyGivenFields(
                new Talk().setId(2L).setName("La conf passee"), "id", "name");

    }


    @Test
    public void shouldArchiveTalkWhenOneIsFound() throws Exception {
        assertThat(talkArchiverRepository.archiveTalks(2014)).isEqualTo(1);
    }
}
