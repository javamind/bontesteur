package com.ninjamind.conference.abc.performance;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.generator.ValueGenerators;
import com.ninja_squad.dbsetup.operation.Operation;
import com.ninjamind.conference.config.ApplicationConfig;
import com.ninjamind.conference.domain.Status;
import com.ninjamind.conference.domain.Talk;
import com.ninjamind.conference.repository.TalkArchiverRepository;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.sql.DataSource;
import java.util.List;

import static com.ninja_squad.dbsetup.Operations.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test de a classe {@link com.ninjamind.conference.repository.TalkArchiverRepository}
 * en utilisant testNg et DbSetup. Le but est de voir que Dbsetup est encore plus rapide si on
 * ne charge la donnee qu'au besoin
 *
 * @see TalkArchiverRepositoryImplDbUnitTest
 * @author EHRET_G
 */
@ContextConfiguration(classes = {ApplicationConfig.class})
@Test(groups = {"perf"})
public class TalkArchiverRepositoryDbSetupSpeedTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    protected DataSource dataSource;

    @Autowired
    private TalkArchiverRepository talkArchiverRepository;

    private static DbSetupTracker dbSetupTracker = new DbSetupTracker();

    @BeforeMethod
    public void prepare(){
        Operation init_data = sequenceOf(
                deleteAllFrom("talk"),
                insertInto("talk")
                        .withGeneratedValue("id", ValueGenerators.sequence().startingAt(1))
                        .columns("name", "status", "dateStart", "dateEnd")
                        .values("Le bon testeur il teste", Status.ACTIVE, new DateTime(2014, 4, 18, 13, 30).toDate(), new DateTime(2014, 4, 18, 14, 20).toDate())
                        .values("La conf passee", Status.ACTIVE, new DateTime(2010, 4, 18, 13, 30).toDate(), new DateTime(2010, 4, 18, 14, 20).toDate())
                        .build()
        );
        DbSetup dbSetup = new DbSetup(DataSourceDestination.with(dataSource), init_data);
        dbSetupTracker.launchIfNecessary(dbSetup);
    }




    @Test(invocationCount = 1000)
    public void shouldFindOneConfToArchiveWhenYearIs2014() {
        dbSetupTracker.skipNextLaunch();
        List<Talk> talks = talkArchiverRepository.findTalkToArchive(2014);
        assertThat(talks).hasSize(1);
        assertThat(talks.get(0)).isEqualToComparingOnlyGivenFields(
                new Talk().setId(2L).setName("La conf passee"), "id", "name");

    }

}
