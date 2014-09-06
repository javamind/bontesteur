package com.ninjamind.conference.repository.interetdbsetup;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.generator.ValueGenerators;
import com.ninja_squad.dbsetup.operation.Operation;
import com.ninjamind.conference.config.ApplicationConfig;
import com.ninjamind.conference.domain.Status;
import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.repository.TalkArchiverRepository;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
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

import javax.sql.DataSource;
import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.assertj.core.api.Assertions.assertThat;


@ContextConfiguration(classes = {ApplicationConfig.class})
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class TalkArchiverRepositoryImpAbtsractDbunitTest {

    @Autowired
    protected PlatformTransactionManager transactionManager;
    @Autowired
    private TalkArchiverRepository talkArchiverRepository;
    @Autowired
    private DataSource dataSource;

    private static DbSetupTracker dbSetupTracker = new DbSetupTracker();

    @Before
    public void setUp() throws Exception {
        Operation initData = sequenceOf(
                deleteAllFrom("talk"),
                insertInto("talk")
                        .withGeneratedValue("id", ValueGenerators.sequence().startingAt(1).incrementingBy(1))
                        .columns("name", "status", "dateStart", "dateEnd")
                        .values("Le bon testeur il teste", Status.ACTIVE, new DateTime(2014, 4, 18, 13, 30).toDate(), new DateTime(2014, 4, 18, 14, 20).toDate())
                        .values("La conf passee", Status.ACTIVE, new DateTime(2010, 4, 18, 13, 30).toDate(), new DateTime(2010, 4, 18, 14, 20).toDate())
                        .build()
        );
        dbSetupTracker.launchIfNecessary(new DbSetup(DataSourceDestination.with(dataSource), initData));
    }

    @Test
    public void shouldFindOneConfToArchiveWhenYearIs2014() {
        dbSetupTracker.skipNextLaunch();
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
