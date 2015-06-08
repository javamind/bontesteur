package com.ninjamind.conference.abc.database;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

import com.ninjamind.conference.category.IntegrationTest;
import com.ninjamind.conference.config.ApplicationConfig;
import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.repository.TalkArchiverRepository;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;


@ContextConfiguration(classes = {ApplicationConfig.class})
@Transactional
@Category(IntegrationTest.class)
public class TalkArchiverRepositoryImplTest extends AbstractDbunitRepositoryTest {

    @Autowired
    private TalkArchiverRepository talkArchiverRepository;

    @Autowired
    protected PlatformTransactionManager transactionManager;



    protected IDataSet readDataSet() {
        try {
            return new FlatXmlDataSetBuilder().build(new File("src/test/resources/datasets/talk_init.xml"));
        } catch (MalformedURLException | DataSetException e) {
            throw new RuntimeException(e);
        }
    }



    @Test
    public void should_find_one_conf_to_archive_in_2014() {
        List<Talk> talks = talkArchiverRepository.findTalkToArchive(2014);
        assertThat(talks).hasSize(1);
        assertThat(talks.get(0)).isEqualToComparingOnlyGivenFields(
                new Talk().setId(2L).setName("La conf passee"), "id", "name");

    }




    @Test
    public void should_archive_conf() throws Exception {

        TransactionTemplate tp = new TransactionTemplate(transactionManager);
        tp.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        tp.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                assertThat(talkArchiverRepository.archiveTalks(2014)).isEqualTo(1);
            }
        });

        assertTableInDatabaseIsEqualToXmlDataset("TALK", "src/test/resources/datasets/talk_archived.xml", "id", "name", "dateStart", "dateEnd", "status");

    }

}
